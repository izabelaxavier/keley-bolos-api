package com.keleybolos.api.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.keleybolos.api.domain.ItemPedido;
import com.keleybolos.api.domain.Pedido;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

@Service
public class PdfService {

    private static final BaseColor ROSA_KELEY = new BaseColor(232, 99, 153);

    // Imagem do Cupcake
    private static final String CUPCAKE_BASE64 = "iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAABmJLR0QA/wD/AP+gvaeTAAAA90lEQVRoge2Z0Q3CIBCG/0g30I10A91IN9CNdAPdQDfQDbSD3UAvXiFJogRaA6F3yYdLrtF+/L8HHAhACCFEf4gAOAA4d+81AAbA1q07W0T60CjJbXWp0UgF4AtgDmAEYN/b/xLAzB38J5CIHAFs3LpXAA8A64j7nSBZ3X3bNNoH0Lh1767R5gQSmTMAHwD7H6XW5j47H0FkIl0Ea5Je1aU1Eal+kF7VpSMR+Qbw3b3f3U8iMgEwj7gXq7sXNNoMIPuXpfL6E0gmnQG8u3XvAN7d795A+u5e7d7Gq0Zq31+lSkt3t7o7WpW+P0pCiJ9mG1C3+gWjH7sEAAAAAElFTkSuQmCC";

    public ByteArrayInputStream gerarComprovante(Pedido pedido) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // --- FONTES ---
            Font fonteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 26, ROSA_KELEY);
            Font fonteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.DARK_GRAY);
            Font fonteNormal = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
            Font fonteNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            Font fonteRodape = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12, BaseColor.DARK_GRAY);

            // --- CABEÇALHO ---
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            try { headerTable.setWidths(new int[]{1, 4}); } catch (Exception e) {}

            PdfPCell cellImage = new PdfPCell();
            cellImage.setBorder(Rectangle.NO_BORDER);
            cellImage.setVerticalAlignment(Element.ALIGN_MIDDLE);
            try {
                byte[] imgBytes = Base64.getDecoder().decode(CUPCAKE_BASE64);
                Image image = Image.getInstance(imgBytes);
                image.scaleToFit(70, 70);
                image.setAlignment(Element.ALIGN_CENTER);
                cellImage.addElement(image);
            } catch (Exception e) {
                cellImage.addElement(new Phrase("🧁"));
            }
            headerTable.addCell(cellImage);

            PdfPCell cellText = new PdfPCell();
            cellText.setBorder(Rectangle.NO_BORDER);
            cellText.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellText.setPaddingLeft(15);

            Paragraph pTitulo = new Paragraph("KELEY BOLOS & CIA", fonteTitulo);
            pTitulo.setSpacingAfter(8);
            cellText.addElement(pTitulo);

            Paragraph pSub = new Paragraph("Comprovante de Pedido", fonteSubtitulo);
            cellText.addElement(pSub);

            headerTable.addCell(cellText);
            document.add(headerTable);

            document.add(new Paragraph(" "));

            DottedLineSeparator separator = new DottedLineSeparator();
            separator.setLineColor(BaseColor.LIGHT_GRAY);
            document.add(new Chunk(separator));

            document.add(new Paragraph(" "));

            // --- DADOS DO CLIENTE ---
            document.add(new Paragraph("Pedido N°: " + pedido.getId(), fonteNegrito));
            document.add(new Paragraph("Cliente: " + pedido.getCliente().getNome(), fonteNormal));
            document.add(new Paragraph("Telefone: " + pedido.getCliente().getTelefone(), fonteNormal));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
            String dataFormatada = pedido.getDataHora() != null ? pedido.getDataHora().format(formatter) : "---";
            document.add(new Paragraph("Data: " + dataFormatada, fonteNormal));

            // --- FORMA DE PAGAMENTO (NOVO) ---
            String pag = pedido.getFormaPagamento() != null ? pedido.getFormaPagamento() : "Não informado";
            document.add(new Paragraph("Forma de Pagamento: " + pag, fonteNormal));

            document.add(new Paragraph(" "));

            // --- TABELA ---
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);
            try { table.setWidths(new int[]{1, 4, 2}); } catch(Exception e){}

            addHeader(table, "Qtd", 14);
            addHeader(table, "Produto", 14);
            addHeader(table, "Valor", 14);

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            // Lista de Itens
            for (ItemPedido item : pedido.getItens()) {
                table.addCell(createCell(item.getQuantidade() + "x", Element.ALIGN_CENTER, 12));

                String desc = item.getProduto().getNome();
                if(item.getObservacao() != null && !item.getObservacao().isEmpty()) {
                    desc += "\n(" + item.getObservacao() + ")";
                }
                table.addCell(createCell(desc, Element.ALIGN_LEFT, 12));

                table.addCell(createCell(nf.format(item.getPrecoVenda()), Element.ALIGN_RIGHT, 12));
            }

            // --- LINHA DA TAXA (SE TIVER) ---
            if (pedido.getTaxaMaquininha() != null && pedido.getTaxaMaquininha().compareTo(BigDecimal.ZERO) > 0) {
                table.addCell(createCell("", Element.ALIGN_CENTER, 12));
                table.addCell(createCell("Taxa da Maquininha (Cartão)", Element.ALIGN_LEFT, 12));
                table.addCell(createCell(nf.format(pedido.getTaxaMaquininha()), Element.ALIGN_RIGHT, 12));
            }

            document.add(table);

            // --- TOTAL ---
            Font fonteTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, ROSA_KELEY);
            Paragraph total = new Paragraph("TOTAL: " + nf.format(pedido.getValorTotal()), fonteTotal);
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(15);
            total.setSpacingAfter(15);
            document.add(total);

            document.add(new Chunk(separator));
            document.add(new Paragraph(" "));

            // --- RODAPÉ ---
            Paragraph msg = new Paragraph("Obrigado pela preferência!", fonteNegrito);
            msg.setAlignment(Element.ALIGN_CENTER);
            msg.setSpacingAfter(5);
            document.add(msg);

            Font fonteInsta = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, ROSA_KELEY);
            Paragraph insta = new Paragraph("Siga: @keley_bolos", fonteInsta);
            insta.setAlignment(Element.ALIGN_CENTER);
            document.add(insta);

            Paragraph contato = new Paragraph("Encomendas: (31) 99691-6994", fonteRodape);
            contato.setAlignment(Element.ALIGN_CENTER);
            contato.setSpacingBefore(5);
            document.add(contato);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addHeader(PdfPTable table, String text, int size) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, size, BaseColor.WHITE)));
        cell.setBackgroundColor(ROSA_KELEY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(10);
        cell.setBorderColor(ROSA_KELEY);
        table.addCell(cell);
    }

    private PdfPCell createCell(String text, int align, int size) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, size, BaseColor.BLACK)));
        cell.setHorizontalAlignment(align);
        cell.setPadding(10);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }
}