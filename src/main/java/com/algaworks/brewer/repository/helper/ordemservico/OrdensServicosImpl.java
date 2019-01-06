package com.algaworks.brewer.repository.helper.ordemservico;

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

import com.algaworks.brewer.model.OrdemServico;
import com.algaworks.brewer.repository.filter.OrdemServicoFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class OrdensServicosImpl implements OrdensServicosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<OrdemServico> filtrar(OrdemServicoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(OrdemServico.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(OrdemServicoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(OrdemServico.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(OrdemServicoFilter filtro, Criteria criteria) {
		if (filtro != null) {

			if (!StringUtils.isEmpty(filtro.getNumeroSerieEquipamento())) {
				criteria.add(Restrictions.ilike("numeroSerieEquipamento", filtro.getNumeroSerieEquipamento(),
						MatchMode.ANYWHERE));
			}

			if (filtro.getStatusOS() != null) {
				criteria.add(Restrictions.eq("statusOS", filtro.getStatusOS()));
			}

		}
	}

}
