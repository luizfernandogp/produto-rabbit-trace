package com.pocms.produtos.database;

import com.pocms.produtos.entity.Produto;
import com.pocms.produtos.service.ProdutoService;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Import implements CommandLineRunner {

    private final ProdutoService produtoService;

    @Autowired
    public Import(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (produtoService.count() == 0) {
            inserirNovoProduto("cadeira", 1280D);
            inserirNovoProduto("fogao", 675.90);
            inserirNovoProduto("geladeira", 4500D);
            inserirNovoProduto("mesa", 1450.50);
            inserirNovoProduto("sofa", 4500D);
        }
    }

    private void inserirNovoProduto(String descricao, Double valor) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("/fotos/" + descricao.toLowerCase() + ".png");
        var image = classPathResource.getInputStream().readAllBytes();
        Produto cadeira = new Produto(WordUtils.capitalize(descricao), BigDecimal.valueOf(valor), image);
        produtoService.save(cadeira);
    }

}
