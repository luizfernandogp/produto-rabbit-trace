package com.pocms.produtos.exception;

import javax.persistence.NoResultException;

public class ProdutoNotFoundException extends NoResultException {

    public ProdutoNotFoundException(Long id) {
        super("Produto não pode ser encontrado: ID[" + id + "]");
    }

    public ProdutoNotFoundException(Integer size, Integer page) {
        super("Nenhum produto foi encontrado: SIZE[" + size + "], PAGE[" + page + "]");
    }

}
