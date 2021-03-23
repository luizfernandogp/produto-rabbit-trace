package com.pocms.produtos.service;

import com.pocms.produtos.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface ProdutoService {

    long count();

    Page<Produto> all(@NonNull Pageable pageable);

    Produto one(@NonNull Long id);

    Produto save(@NonNull Produto produto);

    Produto update(@NonNull Produto produto);

    void delete(@NonNull Long id);

}
