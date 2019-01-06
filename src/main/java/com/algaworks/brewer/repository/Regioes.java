package com.algaworks.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Regiao;
import com.algaworks.brewer.repository.helper.regiao.RegioesQueries;

@Repository
public interface Regioes extends JpaRepository<Regiao, Long>, RegioesQueries {
	
	public Optional<Regiao> findByNomeIgnoreCase(String nome);

	public List<Regiao> findByNomeStartingWithIgnoreCase(String nome);

}
