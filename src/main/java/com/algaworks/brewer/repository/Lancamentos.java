package com.algaworks.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Lancamento;
import com.algaworks.brewer.repository.helper.lancamento.LancamentosQueries;

@Repository
public interface Lancamentos extends JpaRepository<Lancamento, Long>, LancamentosQueries {
	
	public Optional<Lancamento> findByCodigo(Long codigo);

}
