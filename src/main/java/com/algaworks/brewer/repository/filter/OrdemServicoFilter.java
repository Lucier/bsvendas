package com.algaworks.brewer.repository.filter;

import com.algaworks.brewer.model.StatusOS;

public class OrdemServicoFilter {

	private StatusOS statusOS;
	private String numeroSerieEquipamento;

	public StatusOS getStatusOS() {
		return statusOS;
	}

	public void setStatusOS(StatusOS statusOS) {
		this.statusOS = statusOS;
	}

	public String getNumeroSerieEquipamento() {
		return numeroSerieEquipamento;
	}

	public void setNumeroSerieEquipamento(String numeroSerieEquipamento) {
		this.numeroSerieEquipamento = numeroSerieEquipamento;
	}

	

}
