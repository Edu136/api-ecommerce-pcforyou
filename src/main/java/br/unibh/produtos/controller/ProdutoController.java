package br.unibh.produtos.controller;

import br.unibh.produtos.dto.CreateProdutoRequestDTO;
import br.unibh.produtos.dto.ReponseCreateProdutoDTO;
import br.unibh.produtos.dto.VenderProdutoRequestDTO;
import br.unibh.produtos.entity.Produto;
import br.unibh.produtos.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = produtoService.getAllProdutos();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping("/add")
    public ResponseEntity<ReponseCreateProdutoDTO> createProduto (@Valid @RequestBody CreateProdutoRequestDTO request){
        ReponseCreateProdutoDTO response = produtoService.createProduto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/vender/{id}")
    public ResponseEntity<Void> venderProduto(@PathVariable Long id, @RequestBody VenderProdutoRequestDTO request) {
        produtoService.venderProduto(id, request);
        return ResponseEntity.ok().build();
    }
}
