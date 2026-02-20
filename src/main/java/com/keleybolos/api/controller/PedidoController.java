package com.keleybolos.api.controller;

import com.keleybolos.api.domain.Pedido;
import com.keleybolos.api.domain.StatusPedido;
import com.keleybolos.api.dto.PedidoDTO;
import com.keleybolos.api.repository.PedidoRepository;
import com.keleybolos.api.service.PdfService;
import com.keleybolos.api.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @PostMapping
    public Pedido criar(@RequestBody PedidoDTO dto) {
        return pedidoService.criarPedido(dto);
    }

    @PutMapping("/{id}/status")
    public Pedido mudarStatus(@PathVariable Long id, @RequestBody StatusPedido status) {
        return pedidoService.atualizarStatus(id, status);
    }

    @PostMapping("/{id}/pagamento")
    public Pedido registrarPagamento(
            @PathVariable Long id,
            @RequestParam BigDecimal valorPago
    ) {
        return pedidoService.registrarPagamento(id, valorPago);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        pedidoService.excluirPedido(id);
    }
}
