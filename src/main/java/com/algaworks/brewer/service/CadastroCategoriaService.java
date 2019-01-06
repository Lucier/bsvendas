package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Categoria;
import com.algaworks.brewer.repository.Categorias;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.NomeCategoriaJaCadastradoException;

@Service
public class CadastroCategoriaService {

	@Autowired
	private Categorias categorias;

	@Transactional
	public Categoria salvar(Categoria categoria) {
		Optional<Categoria> categoriaOptional = categorias.findByDescricaoIgnoreCase(categoria.getDescricao());
		if (categoriaOptional.isPresent() && !categoriaOptional.get().equals(categoria)) {
			throw new NomeCategoriaJaCadastradoException("Descrição categoria já cadastrada!");

		}

		return categorias.save(categoria);
	}

	@Transactional
	public void excluir(Categoria categoria) {
		try {
			categorias.delete(categoria);
			categorias.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException(
					"Impossível excluir categoria, já está ligada a algum lnaçamento.");
		}
	}

}
