package com.keleybolos.api.service;

import com.keleybolos.api.domain.ItemPedido;
import com.keleybolos.api.domain.Pedido;
import com.keleybolos.api.domain.StatusPedido;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class PdfService {

    public byte[] gerarComprovante(Pedido pedido) {

        if (pedido.getStatus() == StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new RuntimeException("Pedido ainda não possui pagamento mínimo para gerar comprovante");
        }


        if (pedido.getStatus() != StatusPedido.PAGO) {
            throw new RuntimeException("Pedido ainda nao foi pago");
        }

        if (pedido.getStatus() == StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new RuntimeException(
                    "Pedido ainda não possui pagamento confirmado para gerar comprovante."
            );
        }

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();


            Font fonteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("KELEY BOLOS - Comprovante", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" "));


            document.add(new Paragraph("Cliente: " + pedido.getCliente().getNome()));
            document.add(new Paragraph("Telefone: " + pedido.getCliente().getTelefone()));
            document.add(new Paragraph("Pedido #: " + pedido.getId()));
            document.add(new Paragraph("Status: " + pedido.getStatus()));
            document.add(new Paragraph("------------------------------------------------"));


            document.add(new Paragraph("ITENS DO PEDIDO:"));
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            for (ItemPedido item : pedido.getItens()) {
                String linha = String.format(
                        "%dx %s - %s",
                        item.getQuantidade(),
                        item.getProduto().getNome(),
                        nf.format(item.getPrecoVenda())
                );
                document.add(new Paragraph(linha));

                if (item.getObservacao() != null && !item.getObservacao().isEmpty()) {
                    document.add(new Paragraph("   Obs: " + item.getObservacao()));
                }
            }

            document.add(new Paragraph("------------------------------------------------"));


            document.add(new Paragraph("Forma de Pagamento: " + pedido.getFormaPagamento()));

            if (pedido.getTaxaMaquininha() != null && pedido.getTaxaMaquininha().doubleValue() > 0) {
                document.add(new Paragraph(
                        "Taxa Maquininha: " + nf.format(pedido.getTaxaMaquininha())
                ));
            }

            Font fonteTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph total = new Paragraph(
                    "VALOR TOTAL: " + nf.format(pedido.getValorTotal()),
                    fonteTotal
            );
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
