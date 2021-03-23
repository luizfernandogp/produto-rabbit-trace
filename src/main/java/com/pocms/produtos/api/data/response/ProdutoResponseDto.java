package com.pocms.produtos.api.data.response;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

public class ProdutoResponseDto {

    private final Long id;
    private final String descricao;
    private final BigDecimal valor;

    public ProdutoResponseDto(@NonNull Long id, @NonNull String descricao, @NonNull BigDecimal valor) {
        this.id = Objects.requireNonNull(id);
        this.descricao = Objects.requireNonNull(descricao);
        this.valor = Objects.requireNonNull(valor);
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
