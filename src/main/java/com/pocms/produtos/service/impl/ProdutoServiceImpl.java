package com.pocms.produtos.service.impl;

import com.pocms.produtos.entity.Produto;
import com.pocms.produtos.event.ProdutoPersistEvent;
import com.pocms.produtos.exception.ProdutoNotFoundException;
import com.pocms.produtos.repository.ProdutoRepository;
import com.pocms.produtos.service.ProdutoService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProdutoServiceImpl(ProdutoRepository repository, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Page<Produto> all(@NonNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        var produtos = repository.findAll(pageable);

        if (produtos.isEmpty()) {
            throw new ProdutoNotFoundException(pageable.getPageSize(), pageable.getPageNumber());
        }

        return produtos;
    }

    @Override
    public Produto one(@NonNull Long id) {
        Objects.requireNonNull(id);
        return repository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    @Override
    public Produto save(@NonNull Produto produto) {
        Objects.requireNonNull(produto);

        repository.save(produto);
        if (produto.getId() % 2 == 0) {
            throw new RuntimeException("Erro!");
        }
        applicationEventPublisher.publishEvent(new ProdutoPersistEvent(this, produto));

        return produto;
    }

    @Override
    public Produto update(@NonNull Produto produto) {
        Objects.requireNonNull(produto);
        Objects.requireNonNull(produto.getId());

        if (!repository.existsById(produto.getId())) {
            throw new ProdutoNotFoundException(produto.getId());
        }

        repository.save(produto);
        applicationEventPublisher.publishEvent(new ProdutoPersistEvent(this, produto));

        return produto;
    }

    @Override
    public void delete(@NonNull Long id) {
        Objects.requireNonNull(id);

        if (!repository.existsById(id)) {
            throw new ProdutoNotFoundException(id);
        }

        repository.deleteById(id);
    }

}
