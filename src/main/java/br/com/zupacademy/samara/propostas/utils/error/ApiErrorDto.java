package br.com.zupacademy.samara.propostas.utils.error;

public class ApiErrorDto {

    private String campo;
    private String erro;

    public ApiErrorDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
