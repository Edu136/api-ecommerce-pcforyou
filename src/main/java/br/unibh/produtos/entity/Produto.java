package br.unibh.produtos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidade;
    @Enumerated(EnumType.STRING)
    private ProdutoStatus status;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagemProduto> imagens = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(
            mappedBy = "produto",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<ItemPedido> itens = new ArrayList<>();
}
