package br.unibh.produtos.controller;

import br.unibh.produtos.dto.*;
import br.unibh.produtos.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutosResponseDTO>> getAllProdutos() {
        List<ProdutosResponseDTO> produtos = produtoService.getAllProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<ProdutosResponseDTO>> getProdutosByStatus(@PathVariable String status) {
        List<ProdutosResponseDTO> produtos = produtoService.getProdutosByStatus(status);
        return ResponseEntity.ok(produtos);
    }



    @PostMapping("/add")
    public ResponseEntity<ProdutoCreateResponseDTO> createProduto (@Valid @RequestBody ProdutoCreateDTO request){
        ProdutoCreateResponseDTO response = produtoService.createProduto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/vender/{id}")
    public ResponseEntity<Void> venderProduto(@PathVariable Long id, @RequestBody ProdutoVenderRequestDTO request) {
        produtoService.venderProduto(id, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ProdutoCreateResponseDTO> editarProduto(@PathVariable Long id, @RequestBody ProdutoEditDTO request) {
        ProdutoCreateResponseDTO response = produtoService.editarProduto(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
}
