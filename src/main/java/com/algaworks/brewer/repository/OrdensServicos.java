package com.algaworks.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.OrdemServico;
import com.algaworks.brewer.repository.helper.ordemservico.OrdensServicosQueries;

@Repository
public interface OrdensServicos extends JpaRepository<OrdemServico, Long>, OrdensServicosQueries {
	
	public Optional<OrdemServico> findByCodigo(Long codigo);

}
