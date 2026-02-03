package com.keleybolos.api.controller;

import com.keleybolos.api.domain.Pedido;
import com.keleybolos.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/{id}/pagamento")
    public Pedido registrarPagamento(
            @PathVariable Long id,
            @RequestParam BigDecimal valorPago
    ) {
        return pedidoService.registrarPagamento(id, valorPago);
    }
}
