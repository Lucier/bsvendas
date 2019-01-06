package com.algaworks.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Categoria;
import com.algaworks.brewer.repository.helper.categoria.CategoriasQueries;

@Repository
public interface Categorias extends JpaRepository<Categoria, Long>, CategoriasQueries {
	
	public Optional<Categoria> findByDescricaoIgnoreCase(String descricao);

}
