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
import com.algaworks.brewer.model.OrdemServico;
import com.algaworks.brewer.model.StatusOS;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.repository.OrdensServicos;
import com.algaworks.brewer.repository.filter.OrdemServicoFilter;
import com.algaworks.brewer.service.CadastroOrdemServicoService;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.OrdemServicoJaCadastradaException;

@Controller
@RequestMapping("/ordensservicos")
public class OrdemServicoController {
	
	@Autowired
	OrdensServicos ordensServicos;
	
	@Autowired
	private CadastroOrdemServicoService cadastroOrdemServicoService;
	
	@Autowired
	private Clientes clientes;
	
	@RequestMapping("/nova")
	public ModelAndView nova(OrdemServico ordemServico) {
		ModelAndView mv = new ModelAndView("ordemservico/CadastroOrdemServico");
		mv.addObject("statusOSs", StatusOS.values());
		mv.addObject("clientes", clientes.findAll());
		return mv;
	}
	
	@RequestMapping(value = { "/nova", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid OrdemServico ordemServico, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(ordemServico);
		}

		try {
			cadastroOrdemServicoService.salvar(ordemServico);
		} catch (OrdemServicoJaCadastradaException e) {
			result.rejectValue("codigo", e.getMessage(), e.getMessage());
			return nova(ordemServico);

		}
		attributes.addFlashAttribute("mensagem", "OS cadastrada com sucesso");
		return new ModelAndView("redirect:/ordensservicos/nova");
	}

	@GetMapping
	public ModelAndView pesquisar(OrdemServicoFilter ordemServicoFilter, BindingResult result
			, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("ordemservico/PesquisaOrdensServicos");
		mv.addObject("statusOSs", StatusOS.values());
		mv.addObject("clientes", clientes.findAll());
		
		PageWrapper<OrdemServico> paginaWrapper = new PageWrapper<>(ordensServicos.filtrar(ordemServicoFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") OrdemServico ordemServico) {
		ModelAndView mv = nova(ordemServico);
		mv.addObject(ordemServico);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") OrdemServico ordemServico) {
		try {
			cadastroOrdemServicoService.excluir(ordemServico);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
