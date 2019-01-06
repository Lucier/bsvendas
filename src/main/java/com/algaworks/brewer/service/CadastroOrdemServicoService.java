package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.OrdemServico;
import com.algaworks.brewer.repository.OrdensServicos;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.OrdemServicoJaCadastradaException;

@Service
public class CadastroOrdemServicoService {

	@Autowired
	private OrdensServicos oss;

	@Transactional
	public OrdemServico salvar(OrdemServico os) {
		Optional<OrdemServico> osOptional = oss.findByCodigo(os.getCodigo());
		if (osOptional.isPresent() && !osOptional.get().equals(os)) {
			throw new OrdemServicoJaCadastradaException("OS já cadastrada!");

		}

		return oss.save(os);
	}

	@Transactional
	public void excluir(OrdemServico os) {
		try {
			oss.delete(os);
			oss.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir OS.");
		}
	}

}
