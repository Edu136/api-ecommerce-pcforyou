package br.unibh.produtos.controller;

import br.unibh.produtos.dto.ImagemProdutoCreateDTO;
import br.unibh.produtos.entity.ImagemProduto;
import br.unibh.produtos.repository.ImagemRepository;
import br.unibh.produtos.repository.ProdutoRepository;
import br.unibh.produtos.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ImageController {


    private final ProdutoRepository produtoRepository;
    private final ImagemRepository imagemRepository;
    private final ImageService imageService;

    public ImageController(ProdutoRepository produtoRepository, ImagemRepository imagemRepository, ImageService imageService){
        this.produtoRepository = produtoRepository;
        this.imagemRepository = imagemRepository;
        this.imageService = imageService;
    }

    @GetMapping("/first/{idProduto}")
    public ResponseEntity<byte[]> getFirstImageByProdutoId(@PathVariable Long idProduto){
        byte[] imageData = imageService.getFirstImageByProdutoId(idProduto);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
            @Valid @ModelAttribute ImagemProdutoCreateDTO request,
            @RequestParam("file") MultipartFile file) {
        try {
            imageService.salvarImagem(
                    request.idProduto(),
                    request.nomeDoArquivo(),
                    file
            );

            return ResponseEntity.ok("Imagem salva com sucesso!");

        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping(value = "/add/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadMultiple(
            @RequestParam("idProduto") Long idProduto,
            @RequestParam("files") List<MultipartFile> files) throws IOException {

        var produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + idProduto));

        if (files == null || files.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhuma imagem foi enviada.");
        }

        List<ImagemProduto> imagensParaSalvar = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                var imagem = new ImagemProduto();
                imagem.setProduto(produto);
                imagem.setNomeArquivo(file.getOriginalFilename());
                imagem.setImagem(file.getBytes());
                imagensParaSalvar.add(imagem);
            }
        }

        imagemRepository.saveAll(imagensParaSalvar);

        return ResponseEntity.ok(imagensParaSalvar.size() + " imagens salvas com sucesso!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        imageService.deleteImageById(id);
        return ResponseEntity.noContent().build();
    }
}
