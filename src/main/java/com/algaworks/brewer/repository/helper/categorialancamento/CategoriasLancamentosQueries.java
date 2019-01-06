package com.algaworks.brewer.repository.helper.categorialancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.CategoriaLancamento;
import com.algaworks.brewer.repository.filter.CategoriaLancamentoFilter;

public interface CategoriasLancamentosQueries {
	
	public Page<CategoriaLancamento> filtrar(CategoriaLancamentoFilter filtro, Pageable pageable);

}
