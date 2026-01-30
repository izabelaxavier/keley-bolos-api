package com.keleybolos.api.controller;

import com.keleybolos.api.domain.Pedido;
import com.keleybolos.api.repository.PedidoRepository;
import com.keleybolos.api.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/pedidos")
public class PdfController {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PdfService pdfService;

    @GetMapping("/{id}/pdf")
    public ResponseEntity<InputStreamResource> gerarPdf(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        ByteArrayInputStream bis = pdfService.gerarComprovante(pedido);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=pedido-" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}