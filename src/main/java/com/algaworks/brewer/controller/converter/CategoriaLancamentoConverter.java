package com.algaworks.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.CategoriaLancamento;

public class CategoriaLancamentoConverter implements Converter<String, CategoriaLancamento> {

	@Override
	public CategoriaLancamento convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			CategoriaLancamento categoriaLancamento = new CategoriaLancamento();
			categoriaLancamento.setCodigo(Long.valueOf(codigo));
			return categoriaLancamento;
		}

		return null;
	}

}
