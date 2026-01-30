package com.keleybolos.api.controller;

import com.keleybolos.api.domain.Pedido;
import com.keleybolos.api.domain.StatusPedido;
import com.keleybolos.api.dto.PedidoDTO;
import com.keleybolos.api.repository.PedidoRepository;
import com.keleybolos.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired private PedidoService service;
    @Autowired private PedidoRepository repository;

    @GetMapping
    public List<Pedido> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Pedido criar(@RequestBody PedidoDTO dto) {
        return service.criarPedido(dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluirPedido(id);
    }

    @PutMapping("/{id}/status")
    public Pedido atualizarStatus(@PathVariable Long id, @RequestBody StatusPedido status) {
        return service.atualizarStatus(id, status);
    }
}