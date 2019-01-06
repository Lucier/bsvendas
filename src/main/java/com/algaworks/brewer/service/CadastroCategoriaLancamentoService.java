package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.CategoriaLancamento;
import com.algaworks.brewer.repository.CategoriasLancamentos;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.NomeCategoriaLancamentoJaCadastradoException;

@Service
public class CadastroCategoriaLancamentoService {

	@Autowired
	private CategoriasLancamentos categoriasLancamentos;

	@Transactional
	public CategoriaLancamento salvar(CategoriaLancamento categoriaLancamento) {
		Optional<CategoriaLancamento> categoriaLancamentoOptional = categoriasLancamentos
				.findByNomeIgnoreCase(categoriaLancamento.getNome());
		if (categoriaLancamentoOptional.isPresent() && !categoriaLancamentoOptional.get().equals(categoriaLancamento)) {
			throw new NomeCategoriaLancamentoJaCadastradoException("Nome categoria já cadastrada!");

		}

		return categoriasLancamentos.save(categoriaLancamento);
	}

	@Transactional
	public void excluir(CategoriaLancamento categoriaLancamento) {
		try {
			categoriasLancamentos.delete(categoriaLancamento);
			categoriasLancamentos.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException(
					"Impossível excluir categoria, já está ligada a algum lançamento.");
		}
	}

}
