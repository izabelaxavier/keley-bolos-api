package com.keleybolos.api.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDateTime dataHora = LocalDateTime.now();
    private BigDecimal valorTotal = BigDecimal.ZERO;

    private String formaPagamento;
    private BigDecimal taxaMaquininha;

    private BigDecimal valorPago = BigDecimal.ZERO;
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING) // Garante que salve "PAGO" em vez de "1" no banco
    private StatusPedido status = StatusPedido.AGUARDANDO_PAGAMENTO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;
}