package com.algaworks.brewer.repository.helper.lancamento;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Lancamento;
import com.algaworks.brewer.repository.filter.LancamentoFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class LancamentosImpl implements LancamentosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Lancamento> filtrar(LancamentoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Lancamento.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(LancamentoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Lancamento.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(LancamentoFilter filtro, Criteria criteria) {
		if (filtro != null) {

			if (!StringUtils.isEmpty(filtro.getDescricao())) {
				criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
			}

			if (filtro.getTipoLancamento() != null) {
				criteria.add(Restrictions.eq("tipoLancamento", filtro.getTipoLancamento()));
			}

			if (filtro.getDataVencimentoDe() != null) {
				criteria.add(Restrictions.ge("dataVencimentoDe", filtro.getDataVencimentoDe()));
			}

			if (filtro.getDataVencimentoAte() != null) {
				criteria.add(Restrictions.le("dataVencimentoAte", filtro.getDataVencimentoAte()));
			}
		}
	}

}
