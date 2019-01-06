package com.algaworks.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.CategoriaLancamento;
import com.algaworks.brewer.repository.helper.categorialancamento.CategoriasLancamentosQueries;

@Repository
public interface CategoriasLancamentos extends JpaRepository<CategoriaLancamento, Long>, CategoriasLancamentosQueries {
	
	public Optional<CategoriaLancamento> findByNomeIgnoreCase(String nome);

}
