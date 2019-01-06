package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Fornecedor;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.repository.Fornecedores;
import com.algaworks.brewer.repository.filter.FornecedorFilter;
import com.algaworks.brewer.service.CadastroFornecedorService;
import com.algaworks.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/fornecedores")
public class FornecedoresController {

	@Autowired
	private CadastroFornecedorService cadastroFornecedorService;

	@Autowired
	private Fornecedores fornecedores;

	@RequestMapping("/novo")
	public ModelAndView novo(Fornecedor fornecedor) {
		ModelAndView mv = new ModelAndView("fornecedor/CadastroFornecedor");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		return mv;
	}

	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Fornecedor fornecedor, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(fornecedor);
		}

		try {
			cadastroFornecedorService.salvar(fornecedor);
		} catch (CpfCnpjClienteJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(fornecedor);
		}

		attributes.addFlashAttribute("mensagem", "Fornecedor salvo com sucesso!");
		return new ModelAndView("redirect:/fornecedores/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(FornecedorFilter fornecedorFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("fornecedor/PesquisaFornecedores");
		mv.addObject("tiposPessoa", TipoPessoa.values());

		PageWrapper<Fornecedor> paginaWrapper = new PageWrapper<>(fornecedores.filtrar(fornecedorFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Fornecedor fornecedor = fornecedores.findOne(codigo);
		ModelAndView mv = novo(fornecedor);
		mv.addObject(fornecedor);
		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Fornecedor fornecedor) {
		try {
			cadastroFornecedorService.excluir(fornecedor);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
