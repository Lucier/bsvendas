package com.algaworks.brewer.repository.helper.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Lancamento;
import com.algaworks.brewer.repository.filter.LancamentoFilter;

public interface LancamentosQueries {
	
	public Page<Lancamento> filtrar(LancamentoFilter filtro, Pageable pageable);

}
