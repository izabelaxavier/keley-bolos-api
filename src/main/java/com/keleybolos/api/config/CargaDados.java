package com.keleybolos.api.config;

import com.keleybolos.api.domain.Produto;
import com.keleybolos.api.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class CargaDados {

    @Bean
    public CommandLineRunner carregarBanco(ProdutoRepository produtoRepository) {
        return args -> {
            // VERIFICAÇÃO DE SEGURANÇA:
            if (produtoRepository.count() > 0) {
                System.out.println("✅ O BANCO JÁ TEM DADOS! Mantendo histórico seguro.");
                System.out.println("⏹️ Carga de dados cancelada para não apagar pedidos.");
                return; // Para o código aqui e não faz mais nada
            }

            System.out.println("🍰 BANCO VAZIO DETECTADO! Iniciando carga inicial do cardápio...");

            produtoRepository.saveAll(Arrays.asList(
                    // --- TABELA 1: MASSA BRANCA (4 LEITES) ---
                    criar("Bolo 4 Leites (1,5kg - 15 pessoas)", 125.00),
                    criar("Bolo 4 Leites (2,5kg - 30 pessoas)", 200.00),
                    criar("Bolo 4 Leites (4kg - 50 pessoas)", 290.00),

                    // --- TABELA 2: MASSA BRANCA (ABACAXI) ---
                    criar("Bolo Creme com Abacaxi (1,5kg - 15 pessoas)", 125.00),
                    criar("Bolo Creme com Abacaxi (2,5kg - 30 pessoas)", 200.00),
                    criar("Bolo Creme com Abacaxi (4kg - 50 pessoas)", 290.00),

                    // --- TABELA 3: MASSA BRANCA (MORANGO) ---
                    criar("Bolo 4 Leites com Geleia de Morango (1,5kg - 15 pessoas)", 135.00),
                    criar("Bolo 4 Leites com Geleia de Morango (2,5kg - 30 pessoas)", 210.00),
                    criar("Bolo 4 Leites com Geleia de Morango (4kg - 50 pessoas)", 300.00),

                    // --- TABELA 4: MASSA CHOCOLATE (PRESTÍGIO) ---
                    criar("Bolo Prestígio (1,5kg - 15 pessoas)", 125.00),
                    criar("Bolo Prestígio (2,5kg - 30 pessoas)", 200.00),
                    criar("Bolo Prestígio (4kg - 50 pessoas)", 290.00),

                    // --- TABELA 5: MASSA CHOCOLATE (NINHO C/ BOMBOM) ---
                    criar("Bolo Ninho com Bombom (1,5kg - 15 pessoas)", 130.00),
                    criar("Bolo Ninho com Bombom (2,5kg - 30 pessoas)", 210.00),
                    criar("Bolo Ninho com Bombom (4kg - 50 pessoas)", 330.00),

                    // --- TABELA 6: MASSA CHOCOLATE (4 LEITES C/ MORANGO) ---
                    criar("Bolo 4 Leites com Morango e Brigadeiro (1,5kg - 15 pessoas)", 120.00),
                    criar("Bolo 4 Leites com Morango e Brigadeiro (2,5kg - 30 pessoas)", 200.00),
                    criar("Bolo 4 Leites com Morango e Brigadeiro (4kg - 50 pessoas)", 320.00),

                    // --- TABELA 7: MASSA CHOCOLATE (4 LEITES C/ BRIGADEIRO) ---
                    criar("Bolo 4 Leites com Brigadeiro (1,5kg - 15 pessoas)", 130.00),
                    criar("Bolo 4 Leites com Brigadeiro (2,5kg - 30 pessoas)", 210.00),
                    criar("Bolo 4 Leites com Brigadeiro (4kg - 50 pessoas)", 330.00),

                    // --- CHOCOLATUDO ---
                    criar("Bolo Chocolatudo (1,5kg - 15 pessoas)", 145.00),
                    criar("Bolo Chocolatudo (2,5kg - 30 pessoas)", 230.00),
                    criar("Bolo Chocolatudo (4kg - 50 pessoas)", 360.00),

                    // --- BOLO DE 1KG ---
                    criar("Bolo de 1kg (Sabor a escolher)", 90.00),

                    // --- BENTÔ CAKES ---
                    criar("Bentô Cake (Ninho com Bombom)", 60.00),
                    criar("Bentô Cake (Ninho com Geleia de Morango)", 60.00),
                    criar("Bentô Cake (Brigadeiro)", 60.00),

                    // --- BOLOS CASEIROS ---
                    criar("Bolo Caseiro - Romeu e Julieta", 28.00),
                    criar("Bolo Caseiro - Prestígio", 25.00),
                    criar("Bolo Caseiro - Fubá com Queijo", 15.00),

                    // --- DOCINHOS E BOMBONS ---
                    criar("Docinhos Tradicionais (25 und)", 30.00),
                    criar("Docinhos Tradicionais (50 und)", 60.00),
                    criar("Docinhos Tradicionais (75 und)", 90.00),
                    criar("Docinhos Tradicionais (100 und)", 110.00),

                    criar("Docinhos Especiais (25 und)", 35.00),
                    criar("Docinhos Especiais (50 und)", 70.00),
                    criar("Docinhos Especiais (75 und)", 105.00),
                    criar("Docinhos Especiais (100 und)", 140.00),

                    criar("Bombons (1/2 Cento)", 100.00),
                    criar("Bombons (Cento)", 190.00),

                    // --- ITEM EXTRA ---
                    criar("ADICIONAL / PERSONALIZADO", 0.00)
            ));

            System.out.println("✅ BANCO INICIADO COM SUCESSO!");
        };
    }

    private Produto criar(String nome, double preco) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setPreco(new BigDecimal(preco));
        return p;
    }
}