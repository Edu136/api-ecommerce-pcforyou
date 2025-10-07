package br.unibh.produtos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class ImagemProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] imagem;

    private String nomeArquivo;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
