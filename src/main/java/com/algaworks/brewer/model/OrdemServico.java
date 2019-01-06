package com.algaworks.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "ordemservico")
public class OrdemServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotEmpty(message = "Descrição é obrigatório")
	@Column(name = "descricao_equipamento")
	private String descricaoEquipamento;

	@NotNull(message = "Número de série é obrigatório")
	@Column(name = "numero_serie_equipamento")
	private String numeroSerieEquipamento;

	@Column(name = "marca_equipamento")
	private String marcaEquipamento;

	@Column(name = "modelo_equipamento")
	private String modeloEquipamento;

	@NotEmpty(message = "Defeito equipamento é obrigatório")
	@Column(name = "defeito_equipamento")
	private String defeitoEquipamento;

	@Column(name = "data_emissao")
	private String dataEmissao;

	@Column(name = "data_encerramento")
	private String dataEncerramento;

	@Column(name = "descricao_servico")
	private String descricaoServico;

	@NotNull(message = "O valor é obrigatório")
	private BigDecimal valor;

	@NotNull(message = "Cliente é obrigatório")
	@ManyToOne
	@JoinColumn(name = "codigo_cliente")
	private Cliente cliente;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusOS statusOS;

	public boolean isNova() {
		return codigo == null;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricaoEquipamento() {
		return descricaoEquipamento;
	}

	public void setDescricaoEquipamento(String descricaoEquipamento) {
		this.descricaoEquipamento = descricaoEquipamento;
	}

	public String getNumeroSerieEquipamento() {
		return numeroSerieEquipamento;
	}

	public void setNumeroSerieEquipamento(String numeroSerieEquipamento) {
		this.numeroSerieEquipamento = numeroSerieEquipamento;
	}

	public String getMarcaEquipamento() {
		return marcaEquipamento;
	}

	public void setMarcaEquipamento(String marcaEquipamento) {
		this.marcaEquipamento = marcaEquipamento;
	}

	public String getModeloEquipamento() {
		return modeloEquipamento;
	}

	public void setModeloEquipamento(String modeloEquipamento) {
		this.modeloEquipamento = modeloEquipamento;
	}

	public String getDefeitoEquipamento() {
		return defeitoEquipamento;
	}

	public void setDefeitoEquipamento(String defeitoEquipamento) {
		this.defeitoEquipamento = defeitoEquipamento;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(String dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public StatusOS getStatusOS() {
		return statusOS;
	}

	public void setStatusOS(StatusOS statusOS) {
		this.statusOS = statusOS;
	}

	public String getDescricaoServico() {
		return descricaoServico;
	}

	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
