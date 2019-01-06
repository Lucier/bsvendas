package com.algaworks.brewer.repository.helper.fornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Fornecedor;
import com.algaworks.brewer.repository.filter.FornecedorFilter;

public interface FornecedoresQueries {
	
	public Page<Fornecedor> filtrar(FornecedorFilter filtro, Pageable pageable);

}
