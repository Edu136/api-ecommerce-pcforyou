package br.unibh.produtos.controller;

import br.unibh.produtos.dto.CategoriaCreateDTO;
import br.unibh.produtos.dto.CategoriaResponseDTO;
import br.unibh.produtos.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoriaResponseDTO>> getAllCategorias() {
        List<CategoriaResponseDTO> categorias = categoriaService.getAllCategorias();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoriaResponseDTO> createCategoria(@RequestBody @Valid CategoriaCreateDTO req) {
        CategoriaResponseDTO categoria = categoriaService.createNewCategoria(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteCategoriaById(id);
        return ResponseEntity.noContent().build();
    }
}
