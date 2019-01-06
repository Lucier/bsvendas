package com.algaworks.brewer.repository.helper.ordemservico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.OrdemServico;
import com.algaworks.brewer.repository.filter.OrdemServicoFilter;

public interface OrdensServicosQueries {
	
	public Page<OrdemServico> filtrar(OrdemServicoFilter filtro, Pageable pageable);

}
