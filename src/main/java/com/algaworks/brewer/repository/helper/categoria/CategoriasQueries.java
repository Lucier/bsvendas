package com.algaworks.brewer.repository.helper.categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Categoria;
import com.algaworks.brewer.repository.filter.CategoriaFilter;

public interface CategoriasQueries {
	
	public Page<Categoria> filtrar(CategoriaFilter filtro, Pageable pageable);

}
