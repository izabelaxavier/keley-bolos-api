package com.keleybolos.api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoDTO {
    private ClienteDTO cliente;
    private List<ItemPedidoDTO> itens;

    // --- NOVOS CAMPOS ---
    private String formaPagamento;
    private BigDecimal taxaMaquininha;

    @Data
    public static class ClienteDTO {
        private String nome;
        private String telefone;
    }

    @Data
    public static class ItemPedidoDTO {
        private Long produtoId;
        private Integer quantidade;
        private String observacao;
        private BigDecimal precoPersonalizado;
    }
}