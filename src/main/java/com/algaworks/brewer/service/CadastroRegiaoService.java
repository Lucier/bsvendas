package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Regiao;
import com.algaworks.brewer.repository.Regioes;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.NomeRegiaoJaCadastradoException;

@Service
public class CadastroRegiaoService {

	@Autowired
	private Regioes regioes;

	@Transactional
	public Regiao salvar(Regiao regiao) {
		Optional<Regiao> regiaoOptional = regioes.findByNomeIgnoreCase(regiao.getNome());
		if (regiaoOptional.isPresent() && !regiaoOptional.get().equals(regiao)) {
			throw new NomeRegiaoJaCadastradoException("Região já cadastrada!");
		}

		return regioes.save(regiao);
	}

	@Transactional
	public void excluir(Regiao regiao) {
		try {
			regioes.delete(regiao);
			regioes.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException(
					"Impossível excluir a região, já está ligada a algum vendedor.");
		}
	}

}
