package br.unibh.produtos.service;

import br.unibh.produtos.dto.CategoriaCreateDTO;
import br.unibh.produtos.dto.CategoriaResponseDTO;
import br.unibh.produtos.entity.Categoria;
import br.unibh.produtos.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CategoriaResponseDTO createNewCategoria(CategoriaCreateDTO req) {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(req.nomeCategoria());
        categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNomeCategoria());
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(cat -> new CategoriaResponseDTO(cat.getId(), cat.getNomeCategoria()))
                .toList();
    }

    @Transactional
    public void deleteCategoriaById(Long id) {
        categoriaRepository.deleteById(id);
    }
}
