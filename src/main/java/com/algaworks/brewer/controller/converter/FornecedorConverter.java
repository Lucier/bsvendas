package com.algaworks.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Fornecedor;

public class FornecedorConverter implements Converter<String, Fornecedor> {
	
	@Override
	public Fornecedor convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Fornecedor fornecedor = new Fornecedor();
			fornecedor.setCodigo(Long.valueOf(codigo));
			return fornecedor;
		}
		
		return null;
	}

}
