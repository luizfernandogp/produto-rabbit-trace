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
public class ProdutoResponse implements RepresentationModelAssembler<Produto, EntityModel<Produto>> {

    @Override
    public EntityModel<Produto> toModel(@NonNull Produto produto) {
        return EntityModel.of(produto,
                linkTo(methodOn(ProdutoApiImpl.class).one(produto.getId())).withSelfRel(),
                linkTo(methodOn(ProdutoApiImpl.class).all(null, null)).withRel("produtos"));
    }

}
