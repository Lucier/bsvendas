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
import com.algaworks.brewer.model.Categoria;
import com.algaworks.brewer.repository.Categorias;
import com.algaworks.brewer.repository.filter.CategoriaFilter;
import com.algaworks.brewer.service.CadastroCategoriaService;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.NomeCategoriaJaCadastradoException;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {

	@Autowired
	private CadastroCategoriaService cadastroCategoriaService;

	@Autowired
	private Categorias categorias;

	@RequestMapping("/nova")
	public ModelAndView nova(Categoria categoria) {
		ModelAndView mv = new ModelAndView("categoria/CadastroCategoria");
		return mv;

	}

	@RequestMapping(value = { "/nova", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(categoria);
		}

		try {
			cadastroCategoriaService.salvar(categoria);
		} catch (NomeCategoriaJaCadastradoException e) {
			result.rejectValue("descricao", e.getMessage(), e.getMessage());
			return nova(categoria);

		}
		attributes.addFlashAttribute("mensagem", "Categoria salva com sucesso");
		return new ModelAndView("redirect:/categorias/nova");
	}

	@GetMapping
	public ModelAndView pesquisar(CategoriaFilter categoriaFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("categoria/PesquisaCategorias");

		PageWrapper<Categoria> paginaWrapper = new PageWrapper<>(categorias.filtrar(categoriaFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Categoria categoria) {
		ModelAndView mv = nova(categoria);
		mv.addObject(categoria);
		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Categoria categoria) {
		try {
			cadastroCategoriaService.excluir(categoria);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
