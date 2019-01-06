package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Fornecedor;
import com.algaworks.brewer.repository.Fornecedores;
import com.algaworks.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroFornecedorService {
	
	@Autowired
	private Fornecedores fornecedores;
	
	@Transactional
	public Fornecedor salvar(Fornecedor fornecedor) {
		Optional<Fornecedor> fornecedorExistente = fornecedores.findByCpfOuCnpj(fornecedor.getCpfOuCnpjSemFormatacao());
		if (fornecedorExistente.isPresent() && !fornecedorExistente.get().equals(fornecedor)) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}
		
		return fornecedores.save(fornecedor);
	}
	
	@Transactional
	public void excluir(Fornecedor fornecedor) {
		try {
			fornecedores.delete(fornecedor);
			fornecedores.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException(
					"Impossível excluir fornecedor.");
		}
	}

}
