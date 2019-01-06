package com.algaworks.brewer.model;

public enum TipoLancamento {

	RECEITA("RECEITA"), DESPESA("DESPESA");

	private String descricao;

	TipoLancamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
