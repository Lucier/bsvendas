package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.algaworks.brewer.model.Lancamento;
import com.algaworks.brewer.model.TipoLancamento;
import com.algaworks.brewer.repository.CategoriasLancamentos;
import com.algaworks.brewer.repository.Lancamentos;
import com.algaworks.brewer.repository.filter.LancamentoFilter;
import com.algaworks.brewer.service.CadastroLancamentoService;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.LancamentoJaCadastradoException;

@Controller
@RequestMapping("/lancamentos")
public class LancamentosController {

	@Autowired
	private CategoriasLancamentos categoriasLancamentos;

	@Autowired
	private CadastroLancamentoService cadastroLancamentoService;

	@Autowired
	private Lancamentos lancamentos;

	@RequestMapping("/novo")
	public ModelAndView novo(Lancamento lancamento) {
		ModelAndView mv = new ModelAndView("lancamento/CadastroLancamento");
		mv.addObject("tipoLancamentos", TipoLancamento.values());
		mv.addObject("categoriasLancamentos", categoriasLancamentos.findAll());
		return mv;
	}

	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Lancamento lancamento, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(lancamento);
		}

		try {
			cadastroLancamentoService.salvar(lancamento);
		} catch (LancamentoJaCadastradoException e) {
			result.rejectValue("codigo", e.getMessage(), e.getMessage());
			return novo(lancamento);

		}
		attributes.addFlashAttribute("mensagem", "Lancamento salvo com sucesso");
		return new ModelAndView("redirect:/lancamentos/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(LancamentoFilter lancamentoFilter, BindingResult result
			, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("lancamento/PesquisaLancamentos");
		mv.addObject("tipoLancamentos", TipoLancamento.values());
		mv.addObject("categoriasLancamentos", categoriasLancamentos.findAll());
		
		PageWrapper<Lancamento> paginaWrapper = new PageWrapper<>(lancamentos.filtrar(lancamentoFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Lancamento lancamento) {
		ModelAndView mv = novo(lancamento);
		mv.addObject(lancamento);
		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Lancamento lancamento) {
		try {
			cadastroLancamentoService.excluir(lancamento);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
