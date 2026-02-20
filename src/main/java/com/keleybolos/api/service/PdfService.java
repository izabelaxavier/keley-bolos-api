package com.keleybolos.api.service;

import com.keleybolos.api.domain.ItemPedido;
import com.keleybolos.api.domain.Pedido;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.awt.Color;

@Service
public class PdfService {

    public byte[] gerarComprovante(Pedido pedido) {
        Document document = new Document(PageSize.A6, 20, 20, 20, 20);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // LÓGICA INFALÍVEL:
            // Se o valorPago for maior que zero ou o status for PAGO, ele tira o aviso
            boolean jaRecebeuDinheiro = (pedido.getValorPago() != null && pedido.getValorPago().compareTo(BigDecimal.ZERO) > 0);
            boolean statusTaPago = (pedido.getStatus() != null && pedido.getStatus().toString().equalsIgnoreCase("PAGO"));

            if (!jaRecebeuDinheiro && !statusTaPago) {
                Font fAviso = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.RED);
                Paragraph aviso = new Paragraph("PAGAMENTO PENDENTE", fAviso);
                aviso.setAlignment(Element.ALIGN_CENTER);
                document.add(aviso);
                document.add(new Paragraph("________________________________", fAviso));
            }

            // VOLTANDO O VISUAL BONITO QUE VOCÊ QUERIA
            Font fTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new Color(255, 105, 180));
            Font fTexto = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);
            Font fNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.BLACK);

            Paragraph titulo = new Paragraph("KELEY BOLOS", fTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Pedido nº: " + pedido.getId(), fTexto));
            document.add(new Paragraph("Cliente: " + (pedido.getCliente() != null ? pedido.getCliente().getNome() : "Consumidor"), fTexto));
            document.add(new Paragraph("Data: " + pedido.getDataHora(), fTexto));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(new float[]{1, 4, 2});
            table.setWidthPercentage(100);
            table.addCell(new Phrase("Qtd", fNegrito));
            table.addCell(new Phrase("Produto", fNegrito));
            table.addCell(new Phrase("Total", fNegrito));

            if (pedido.getItens() != null) {
                for (ItemPedido item : pedido.getItens()) {
                    table.addCell(new Phrase(item.getQuantidade().toString(), fTexto));
                    table.addCell(new Phrase(item.getProduto().getNome(), fTexto));
                    table.addCell(new Phrase(nf.format(item.getPrecoVenda()), fTexto));
                }
            }
            document.add(table);

            document.add(new Paragraph(" "));
            Paragraph total = new Paragraph("TOTAL: " + nf.format(pedido.getValorTotal()), fTitulo);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
        } catch (Exception e) { e.printStackTrace(); }
        return out.toByteArray();
    }

    private PdfPCell criarCelula(String texto, Font fonte) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, fonte));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}