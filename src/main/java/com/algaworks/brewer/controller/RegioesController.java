package com.algaworks.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
import com.algaworks.brewer.model.Regiao;
import com.algaworks.brewer.repository.Regioes;
import com.algaworks.brewer.repository.filter.RegiaoFilter;
import com.algaworks.brewer.service.CadastroRegiaoService;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.NomeRegiaoJaCadastradoException;

@Controller
@RequestMapping("/regioes")
public class RegioesController {

	@Autowired
	private Regioes regioes;

	@Autowired
	private CadastroRegiaoService cadastroRegiaoService;

	@RequestMapping("/nova")
	public ModelAndView nova(Regiao regiao) {
		ModelAndView mv = new ModelAndView("regiao/CadastroRegiao");
		return mv;
	}

	@RequestMapping(value = { "/nova", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Regiao regiao, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(regiao);
		}

		try {
			cadastroRegiaoService.salvar(regiao);
		} catch (NomeRegiaoJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(regiao);
		}

		attributes.addFlashAttribute("mensagem", "Região salva com sucesso");
		return new ModelAndView("redirect:/regioes/nova");
	}

	@GetMapping
	public ModelAndView pesquisar(RegiaoFilter regiaoFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("regiao/PesquisaRegioes");

		PageWrapper<Regiao> paginaWrapper = new PageWrapper<>(regioes.filtrar(regiaoFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Regiao> pesquisar(String nome) {
		validarTamanhoNome(nome);
		return regioes.findByNomeStartingWithIgnoreCase(nome);
	}

	private void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Regiao regiao) {
		ModelAndView mv = nova(regiao);
		mv.addObject(regiao);
		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Regiao regiao) {
		try {
			cadastroRegiaoService.excluir(regiao);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
