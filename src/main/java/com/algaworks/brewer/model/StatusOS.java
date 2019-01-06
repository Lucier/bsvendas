package com.algaworks.brewer.model;

public enum StatusOS {

	ANALISE("ANÁLISE"), ORCAMENTO("ORÇAMENTO"), REPARO("REPARO"), FINALIZADO("FINALIZADO"), CANCELADO("CANCELADO"),
	AGUARDANDO("AGUARDANDO");

	private String descricao;

	StatusOS(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
