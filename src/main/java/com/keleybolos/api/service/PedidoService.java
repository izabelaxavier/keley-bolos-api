package com.keleybolos.api.service;

import com.keleybolos.api.domain.*;
import com.keleybolos.api.dto.PedidoDTO;
import com.keleybolos.api.repository.ClienteRepository;
import com.keleybolos.api.repository.PedidoRepository;
import com.keleybolos.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class PedidoService {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProdutoRepository produtoRepository;

    public Pedido criarPedido(PedidoDTO dto) {
        Cliente cliente = clienteRepository.findByTelefone(dto.getCliente().getTelefone());
        if (cliente == null) {
            cliente = new Cliente();
            cliente.setNome(dto.getCliente().getNome());
            cliente.setTelefone(dto.getCliente().getTelefone());
            cliente = clienteRepository.save(cliente);
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setItens(new ArrayList<>());
        pedido.setFormaPagamento(dto.getFormaPagamento());
        pedido.setTaxaMaquininha(dto.getTaxaMaquininha() != null ? dto.getTaxaMaquininha() : BigDecimal.ZERO);


        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (PedidoDTO.ItemPedidoDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setObservacao(itemDTO.getObservacao());

            if (itemDTO.getPrecoPersonalizado() != null) {
                item.setPrecoVenda(itemDTO.getPrecoPersonalizado());
            } else {
                item.setPrecoVenda(produto.getPreco());
            }

            pedido.getItens().add(item);
            BigDecimal subtotal = item.getPrecoVenda().multiply(new BigDecimal(item.getQuantidade()));
            totalPedido = totalPedido.add(subtotal);
        }

        totalPedido = totalPedido.add(pedido.getTaxaMaquininha());
        pedido.setValorTotal(totalPedido);

        return pedidoRepository.save(pedido);
    }

    public Pedido registrarPagamento(Long pedidoId, BigDecimal valorPago) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        BigDecimal minimo = pedido.getValorTotal().multiply(new BigDecimal("0.5"));

        if (valorPago.compareTo(minimo) < 0) {
            throw new RuntimeException("Pagamento minimo de 50% necessario");
        }

        pedido.setValorPago(valorPago);
        pedido.setDataPagamento(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PAGO);

        return pedidoRepository.save(pedido);
    }


    public void excluirPedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pedido não encontrado");
        }
    }

      public Pedido atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
}

