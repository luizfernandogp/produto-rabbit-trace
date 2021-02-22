package com.pocms.produtos.api;

import com.pocms.produtos.api.data.request.ProdutoPersistDto;
import com.pocms.produtos.api.data.request.ProdutoUpdateDto;
import com.pocms.produtos.entity.Produto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Produtos",
                description = "Essa API provê endpoints para manipular os dados de produtos no banco de dados",
                version = "1.0",
                contact = @Contact(
                        name = "Banco PAN digital"
                )
        )
)
public interface ProdutoApi {

    @Operation(summary = "Lista os produtos de forma páginada")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de produtos encontrados",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                       "_embedded": {
                                                         "produtoList": [
                                                           {
                                                             "id": 0,
                                                             "descricao": "string",
                                                             "valor": 0,
                                                             "_links": {
                                                               "self": {
                                                                 "href": "http://localhost:8081/produtos/0"
                                                               },
                                                               "produtos": {
                                                                 "href": "http://localhost:8081/produtos{?size,page}",
                                                                 "templated": true
                                                               }
                                                             }
                                                           }
                                                         ]
                                                       },
                                                       "_links": {
                                                         "self": {
                                                           "href": "http://localhost:8081?size=10&page=0"
                                                         }
                                                       }
                                                     }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "timestamp": "2021-02-19T18:44:03.081+00:00",
                                                         "status": "XXX_200",
                                                         "userMessage": "For input string: \\"xxx\\"",
                                                         "developerMessage": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'"
                                                    }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum produto foi encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-19T18:45:12.927+00:00",
                                                         "status": "XXX_100",
                                                         "userMessage": "Nenhum produto foi encontrado: SIZE[10], PAGE[0]",
                                                         "developerMessage": "Nenhum produto foi encontrado: SIZE[10], PAGE[0]"
                                                    }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-19T18:43:29.585+00:00",
                                                         "status": "XXX_300",
                                                         "userMessage": "Mensagem de erro inesperado",
                                                         "developerMessage": "Mensagem de erro inesperado"
                                                     }"""
                                    )
                            }
                    )
            )
    })
    CollectionModel<EntityModel<Produto>> all(
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
    );

    @Operation(summary = "Retorna o produto filtrando pelo seu código de identificação")
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                        {
                                            "id": 0,
                                            "descricao": "string",
                                            "valor": 0,
                                            "_links": {
                                              "self": {
                                                "href": "http://localhost:8081/produtos/0"
                                              },
                                              "produtos": {
                                                "href": "http://localhost:8081/produtos{?size,page}",
                                                "templated": true
                                              }
                                            }
                                        }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetro inválido",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "timestamp": "2021-02-19T18:44:03.081+00:00",
                                                         "status": "XXX_200",
                                                         "userMessage": "For input string: \\"xxx\\"",
                                                         "developerMessage": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'"
                                                    }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-19T18:45:12.927+00:00",
                                                         "status": "XXX_100",
                                                         "userMessage": "Produto não pode ser encontrado: ID[1]",
                                                         "developerMessage": "Produto não pode ser encontrado: ID[1]"
                                                    }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-19T18:43:29.585+00:00",
                                                         "status": "XXX_300",
                                                         "userMessage": "Mensagem de erro inesperado",
                                                         "developerMessage": "Mensagem de erro inesperado"
                                                     }"""
                                    )
                            }
                    )
            )
    })
    EntityModel<Produto> one(@NonNull @PathVariable("id") Long id);

    @Operation(summary = "Persiste o produto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto inserido",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                       "id": 0,
                                                       "descricao": "string",
                                                       "valor": 0,
                                                       "_links": {
                                                         "self": {
                                                           "href": "http://localhost:8081/produtos/0"
                                                         },
                                                         "produtos": {
                                                           "href": "http://localhost:8081/produtos{?size,page}",
                                                           "templated": true
                                                         }
                                                       }
                                                     }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-19T18:43:29.585+00:00",
                                                         "status": "XXX_300",
                                                         "userMessage": "Mensagem de erro inesperado",
                                                         "developerMessage": "Mensagem de erro inesperado"
                                                     }"""
                                    )
                            }
                    )
            )
    })
    EntityModel<Produto> newProduto(@NonNull @Valid @RequestBody ProdutoPersistDto produtoPersist);

    @Operation(summary = "Altera as informações do produto já persistido")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto alterado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "id": 0,
                                                        "descricao": "string",
                                                        "valor": 0,
                                                        "_links": {
                                                          "self": {
                                                            "href": "http://localhost:8081/produtos/0"
                                                          },
                                                          "produtos": {
                                                            "href": "http://localhost:8081/produtos{?size,page}",
                                                            "templated": true
                                                          }
                                                        }
                                                      }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetro inválido",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-22T12:23:14.006+00:00",
                                                         "status": "XXX_200",
                                                         "userMessage": "For input string: \\"xxxx\\"",
                                                         "developerMessage": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \\"xxxx\\""
                                                     }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum produto foi encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-22T12:23:52.847+00:00",
                                                         "status": "XXX_100",
                                                         "userMessage": "Produto não pode ser encontrado: ID[0]",
                                                         "developerMessage": "Produto não pode ser encontrado: ID[0]"
                                                     }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-19T18:43:29.585+00:00",
                                                         "status": "XXX_300",
                                                         "userMessage": "Mensagem de erro inesperado",
                                                         "developerMessage": "Mensagem de erro inesperado"
                                                     }"""
                                    )
                            }
                    )
            )
    })
    EntityModel<Produto> update(
            @NonNull @Valid @RequestBody ProdutoPersistDto produtoPersist,
            @NonNull @PathVariable Long id
    );

    @Operation(summary = "Remove o produto pelo seu código de identificação")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Produto removido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetro inválido",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-22T12:23:14.006+00:00",
                                                         "status": "XXX_200",
                                                         "userMessage": "For input string: \\"xxxx\\"",
                                                         "developerMessage": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \\"xxxx\\""
                                                     }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum produto foi encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-22T12:23:52.847+00:00",
                                                         "status": "XXX_100",
                                                         "userMessage": "Produto não pode ser encontrado: ID[0]",
                                                         "developerMessage": "Produto não pode ser encontrado: ID[0]"
                                                     }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-19T18:43:29.585+00:00",
                                                         "status": "XXX_300",
                                                         "userMessage": "Mensagem de erro inesperado",
                                                         "developerMessage": "Mensagem de erro inesperado"
                                                     }"""
                                    )
                            }
                    )
            )
    })
    void delete(@NonNull @PathVariable Long id);

    @Operation(summary = "Altera apenas as informações dos produtos recebidas no corpo da requisição")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto alterado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "id": 0,
                                                        "descricao": "string",
                                                        "valor": 0,
                                                        "_links": {
                                                          "self": {
                                                            "href": "http://localhost:8081/produtos/0"
                                                          },
                                                          "produtos": {
                                                            "href": "http://localhost:8081/produtos{?size,page}",
                                                            "templated": true
                                                          }
                                                        }
                                                      }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetro inválido",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-22T12:23:14.006+00:00",
                                                         "status": "XXX_200",
                                                         "userMessage": "For input string: \\"xxxx\\"",
                                                         "developerMessage": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \\"xxxx\\""
                                                     }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum produto foi encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-22T12:23:52.847+00:00",
                                                         "status": "XXX_100",
                                                         "userMessage": "Produto não pode ser encontrado: ID[0]",
                                                         "developerMessage": "Produto não pode ser encontrado: ID[0]"
                                                     }"""
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro inesperado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                         "timestamp": "2021-02-19T18:43:29.585+00:00",
                                                         "status": "XXX_300",
                                                         "userMessage": "Mensagem de erro inesperado",
                                                         "developerMessage": "Mensagem de erro inesperado"
                                                     }"""
                                    )
                            }
                    )
            )
    })
    EntityModel<Produto> updateProduto(
            @NonNull @PathVariable("id") Long id,
            @Valid @RequestBody ProdutoUpdateDto produtoUpdateDto
    );

}
