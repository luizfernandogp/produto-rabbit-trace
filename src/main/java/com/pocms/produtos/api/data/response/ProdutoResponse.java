package com.pocms.produtos.api.data.response;

import com.pocms.produtos.api.impl.ProdutoApiImpl;
import com.pocms.produtos.entity.Produto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProdutoResponse implements RepresentationModelAssembler<Produto, EntityModel<ProdutoResponseDto>> {

    @Override
    public EntityModel<ProdutoResponseDto> toModel(@NonNull Produto produto) {
        var produtoResponseDto = new ProdutoResponseDto(produto.getId(), produto.getDescricao(), produto.getValor());
        return EntityModel.of(produtoResponseDto,
                linkTo(methodOn(ProdutoApiImpl.class).one(produto.getId())).withSelfRel(),
                linkTo(methodOn(ProdutoApiImpl.class).all(null, null)).withRel("produtos"));
    }

}
