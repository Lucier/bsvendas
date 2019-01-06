package com.algaworks.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Fornecedor;
import com.algaworks.brewer.repository.helper.fornecedor.FornecedoresQueries;

@Repository
public interface Fornecedores extends JpaRepository<Fornecedor, Long>, FornecedoresQueries {
	
	public Optional<Fornecedor> findByCpfOuCnpj(String cpfOuCnpj);

	public List<Fornecedor> findByNomeStartingWithIgnoreCase(String nome);

}
