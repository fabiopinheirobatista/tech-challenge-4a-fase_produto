package br.com.fiap.adapter.exception;

import lombok.Getter;

@Getter
public enum ProblemType {
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),
    MENSAGEM_CAMPO_IGNORADO("/mensagem-campo-ignorado", "Mensagem campo ignorado"),
    MENSAGEM_CAMPO_INEXISTENTE("/mensagem-campo-inexistente", "Mensagem campo inexistente"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    SKU_JACADASTRADO("/sku-ja-cadastrado", "SKU Já cadastrado"),
    CPF_INVALIDOS("/cpf-invalido", "CPF Ivalido"),
    CPF_JA_CADASTRADO("/cpf-ja-cadastrado", "CPF Já Cadastrado"),
    ;

    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "http://www.fiap.com.br"+path;
        this.title = title;
    }

}
