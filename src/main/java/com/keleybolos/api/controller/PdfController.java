package com.keleybolos.api.controller;

import com.keleybolos.api.domain.Pedido;
import com.keleybolos.api.repository.PedidoRepository;
import com.keleybolos.api.service.PdfService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
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
    @PersistenceContext private EntityManager entityManager;

    @GetMapping("/{id}/pdf")
    @Transactional(readOnly = true)
    public ResponseEntity<InputStreamResource> gerarPdf(@PathVariable Long id) {

        // 1. LIMPEZA TOTAL: Esquece tudo que está na memória
        entityManager.clear();

        // 2. BUSCA DO ZERO: Vai lá no banco buscar o status atual
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        byte[] pdfBytes = pdfService.gerarComprovante(pedido);
        ByteArrayInputStream bis = new ByteArrayInputStream(pdfBytes);

        // 3. NOME ÚNICO: Adiciona o horário atual no nome do arquivo
        // Isso engana o navegador e força ele a baixar um arquivo novo, não o antigo.
        String nomeArquivo = "comprovante_" + id + "_" + System.currentTimeMillis() + ".pdf";

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=" + nomeArquivo)
                .cacheControl(CacheControl.noCache().mustRevalidate()) // Proíbe o navegador de guardar o PDF
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}