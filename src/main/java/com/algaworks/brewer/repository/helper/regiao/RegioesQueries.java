package com.algaworks.brewer.repository.helper.regiao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Regiao;
import com.algaworks.brewer.repository.filter.RegiaoFilter;

public interface RegioesQueries {
	
	public Page<Regiao> filtrar(RegiaoFilter filtro, Pageable pageable);

}
