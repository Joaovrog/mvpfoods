package com.algajv.jvfoods.api.exceptionhandler;

public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    ERRO_DE_NEGOCIO("Violação da regra de negócio", "/erro-de-negocio"),
    CORPO_INVALIDO("Corpo inválido", "/corpo-invalido"),
    PARAMETRO_URL_INVALIDO("Parâmetro da URL inválido", "/parametro-url-invalido"),
    ERRO_DE_SISTEMA("Erro de sistema", "/erro-de-sistema"),
    DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos");


    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://jvfoods.com.br" + path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
