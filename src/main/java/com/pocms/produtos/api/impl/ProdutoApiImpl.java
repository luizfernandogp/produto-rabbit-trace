package com.pocms.produtos.api.impl;

import com.pocms.produtos.api.ProdutoApi;
import com.pocms.produtos.api.data.request.ProdutoPersistDto;
import com.pocms.produtos.api.data.request.ProdutoUpdateDto;
import com.pocms.produtos.api.data.response.ProdutoResponse;
import com.pocms.produtos.api.data.response.ProdutoResponseDto;
import com.pocms.produtos.entity.Produto;
import com.pocms.produtos.service.ProdutoService;
import com.pocms.produtos.util.JsonUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("produtos")
public class ProdutoApiImpl implements ProdutoApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoApiImpl.class);

    private final ProdutoResponse produtoResponse;
    private final ModelMapper modelMapper;
    private final ProdutoService service;

    public ProdutoApiImpl(
            ProdutoResponse produtoResponse,
            ModelMapper modelMapper,
            ProdutoService service) {
        this.produtoResponse = produtoResponse;
        this.modelMapper = modelMapper;
        this.service = service;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<ProdutoResponseDto>> all(
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        LOGGER.info("Buscando os produtos: size = {}, page = {}", size, page);

        var pageable = PageRequest.of(page, size);

        var produtos = service.all(pageable)
                .stream()
                .map(produtoResponse::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                produtos,
                linkTo(methodOn(ProdutoApi.class).all(size, page))
                .withSelfRel()
        );
    }

    @Override
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<ProdutoResponseDto> one(@NonNull @PathVariable("id") Long id) {
        LOGGER.info("Buscando o produto: id = {}", id);

        var produto = service.one(id);
        return produtoResponse.toModel(produto);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<ProdutoResponseDto> newProduto(@NonNull @Valid @RequestBody ProdutoPersistDto produtoPersist) {
        LOGGER.info("Inserindo o produto: {}", produtoPersist);

        var produto = modelMapper.map(produtoPersist, Produto.class);
        return produtoResponse.toModel(service.save(produto));
    }

    @Override
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<ProdutoResponseDto> update(
            @NonNull @Valid @RequestBody ProdutoPersistDto produtoPersist,
            @NonNull @PathVariable Long id) {
        LOGGER.info("Alterando o produto: id = {}, {}", id, produtoPersist);

        var produto = service.one(id);

        modelMapper.map(produtoPersist, produto);

        return produtoResponse.toModel(service.update(produto));
    }

    @Override
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long id) {
        LOGGER.info("Excluindo o produto: id = {}", id);

        service.delete(id);
    }

    @Override
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<ProdutoResponseDto> updateProduto(
            @NonNull @PathVariable("id") Long id,
            @Valid @RequestBody ProdutoUpdateDto produtoUpdateDto) {
        LOGGER.info("Alerando o produto: id = {}, {}", id, produtoUpdateDto);

        var produto = service.one(id);

        JsonUtil.changeIfPresent(produtoUpdateDto.getDescricao(), produto::setDescricao);
        JsonUtil.changeIfPresent(produtoUpdateDto.getValor(), produto::setValor);

        return produtoResponse.toModel(service.update(produto));
    }

    @Override
    @GetMapping("{id}/imagem")
    @ResponseStatus(HttpStatus.OK)
    public void imagem(@PathVariable("id") Long id, HttpServletResponse response) {
        byte[] imagem = service.one(id).getImagem();
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader("Content-Disposition", "filename=\"" + id + ".png\"");
        response.setContentLength(imagem.length);

        try (OutputStream os = response.getOutputStream()) {
            os.write(imagem, 0, imagem.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @PostMapping("{id}/imagem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upload(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith("png")) {
            throw new RuntimeException("Apenas imagens do tipo PNG são aceitas");
        }

        Produto produto = service.one(id);
        produto.setImagem(file.getBytes());
        service.save(produto);
    }

}
