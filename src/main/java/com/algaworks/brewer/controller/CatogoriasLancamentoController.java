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
import com.algaworks.brewer.model.CategoriaLancamento;
import com.algaworks.brewer.repository.CategoriasLancamentos;
import com.algaworks.brewer.repository.filter.CategoriaLancamentoFilter;
import com.algaworks.brewer.service.CadastroCategoriaLancamentoService;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.NomeCategoriaLancamentoJaCadastradoException;

@Controller
@RequestMapping("/categoriaslancamentos")
public class CatogoriasLancamentoController {

	@Autowired
	private CadastroCategoriaLancamentoService cadastroCategoriaLancamentoService;

	@Autowired
	private CategoriasLancamentos categoriaLancamentos;

	@RequestMapping("/nova")
	public ModelAndView nova(CategoriaLancamento categoriaLancamento) {
		ModelAndView mv = new ModelAndView("categorialancamento/CadastroCategoriaLancamento");
		return mv;

	}

	@RequestMapping(value = { "/nova", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid CategoriaLancamento categoriaLancamento, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(categoriaLancamento);
		}

		try {
			cadastroCategoriaLancamentoService.salvar(categoriaLancamento);
		} catch (NomeCategoriaLancamentoJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(categoriaLancamento);

		}
		attributes.addFlashAttribute("mensagem", "Categoria salva com sucesso");
		return new ModelAndView("redirect:/categoriaslancamentos/nova");
	}

	@GetMapping
	public ModelAndView pesquisar(CategoriaLancamentoFilter categoriaLancamentoFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("categorialancamento/PesquisaCategoriasLancamentos");

		PageWrapper<CategoriaLancamento> paginaWrapper = new PageWrapper<>(
				categoriaLancamentos.filtrar(categoriaLancamentoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") CategoriaLancamento categoriaLancamento) {
		ModelAndView mv = nova(categoriaLancamento);
		mv.addObject(categoriaLancamento);
		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") CategoriaLancamento categoriaLancamento) {
		try {
			cadastroCategoriaLancamentoService.excluir(categoriaLancamento);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
