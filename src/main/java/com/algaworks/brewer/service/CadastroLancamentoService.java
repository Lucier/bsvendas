package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Lancamento;
import com.algaworks.brewer.repository.Lancamentos;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.NomeCategoriaLancamentoJaCadastradoException;

@Service
public class CadastroLancamentoService {
	
	@Autowired
	private Lancamentos lancamentos;
	
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		Optional<Lancamento> lancamentoOptional = lancamentos
				.findByCodigo(lancamento.getCodigo());
		if (lancamentoOptional.isPresent() && !lancamentoOptional.get().equals(lancamento)) {
			throw new NomeCategoriaLancamentoJaCadastradoException("Lançamento já cadastrado!");

		}

		return lancamentos.save(lancamento);
	}

	@Transactional
	public void excluir(Lancamento lancamento) {
		try {
			lancamentos.delete(lancamento);
			lancamentos.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException(
					"Impossível excluir lançamento.");
		}
	}

}
