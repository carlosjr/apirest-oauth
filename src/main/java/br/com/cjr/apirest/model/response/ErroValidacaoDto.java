package br.com.cjr.apirest.model.response;

public class ErroValidacaoDto {

	private String campo;
	private String erro;
	
	public ErroValidacaoDto(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}
}
