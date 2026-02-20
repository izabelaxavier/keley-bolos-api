package com.keleybolos.api.controller;

import com.keleybolos.api.domain.Pedido;
import com.keleybolos.api.repository.PedidoRepository;
import com.keleybolos.api.service.PdfService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/pedidos")
public class PdfController {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PdfService pdfService;

    @PersistenceContext
    private EntityManager entityManager; // Isso aqui é o "zerador" de cache

    @GetMapping("/{id}/pdf")
    @Transactional(readOnly = true)
    public ResponseEntity<InputStreamResource> gerarPdf(@PathVariable Long id) {

        // 1. FORÇA O JAVA A ESQUECER O QUE SABE (Limpa o cache)
        entityManager.clear();

        // 2. BUSCA DO BANCO DE VERDADE
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // 3. REATUALIZA O OBJETO
        entityManager.refresh(pedido);

        byte[] pdfBytes = pdfService.gerarComprovante(pedido);
        ByteArrayInputStream bis = new ByteArrayInputStream(pdfBytes);

        // Adicionando um número aleatório no nome do arquivo para o navegador não viciar
        String filename = "comprovante_" + id + "_" + System.currentTimeMillis() + ".pdf";

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=" + filename)
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}