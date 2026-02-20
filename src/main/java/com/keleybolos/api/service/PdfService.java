package com.keleybolos.api.service;

import com.keleybolos.api.domain.ItemPedido;
import com.keleybolos.api.domain.Pedido;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
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

            // Fontes
            Font fTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new Color(255, 105, 180));
            Font fTexto = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);
            Font fNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.BLACK);

            // Cabeçalho
            Paragraph titulo = new Paragraph("KELEY BOLOS", fTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("--------------------------------------------------", fTexto));
            document.add(new Paragraph("Pedido nº: " + pedido.getId(), fNegrito));
            document.add(new Paragraph("Cliente: " + (pedido.getCliente() != null ? pedido.getCliente().getNome() : "Consumidor"), fTexto));
            document.add(new Paragraph("Pagamento: " + (pedido.getFormaPagamento() != null ? pedido.getFormaPagamento() : "Não informado"), fTexto));
            document.add(new Paragraph(" "));

            // Tabela de Itens
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
}