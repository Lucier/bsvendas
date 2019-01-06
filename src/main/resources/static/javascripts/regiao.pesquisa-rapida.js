Brewer = Brewer || {};

Brewer.PesquisaRapidaRegiao = (function() {
	
	function PesquisaRapidaRegiao() {
		this.pesquisaRapidaRegioesModal = $('#pesquisaRapidaRegioes');
		this.nomeInput = $('#nomeRegiaoModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-regioes-btn'); 
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaRegioes');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-regiao').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
	}
	
	PesquisaRapidaRegiao.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
		this.pesquisaRapidaRegioesModal.on('shown.bs.modal', onModalShow.bind(this));

	}
	
	function onModalShow() {
		this.nomeInput.focus();
	}
	
	function onPesquisaRapidaClicado(event) {
		event.preventDefault();
		
		$.ajax({
			url: this.pesquisaRapidaRegioesModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.nomeInput.val()
			}, 
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});
	}
	
	function onPesquisaConcluida(resultado) {
		this.mensagemErro.addClass('hidden');
		
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);
		
		var tabelaRegiaoPesquisaRapida = new Brewer.TabelaRegiaoPesquisaRapida(this.pesquisaRapidaRegioesModal);
		tabelaRegiaoPesquisaRapida.iniciar();
	} 
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaRegiao;
	
}());

Brewer.TabelaRegiaoPesquisaRapida = (function() {
	
	function TabelaRegiaoPesquisaRapida(modal) {
		this.modalRegiao = modal;
		this.regiao = $('.js-regiao-pesquisa-rapida');
	}
	
	TabelaRegiaoPesquisaRapida.prototype.iniciar = function() {
		this.regiao.on('click', onRegiaoSelecionada.bind(this));
	}
	
	function onRegiaoSelecionada(evento) {
		this.modalRegiao.modal('hide');
		
		var regiaoSelecionada = $(evento.currentTarget);
		$('#nomeRegiao').val(regiaoSelecionada.data('nome'));
		$('#codigoRegiao').val(regiaoSelecionada.data('codigo'));
	}
	
	return TabelaRegiaoPesquisaRapida;
	
}());

$(function() {
	var pesquisaRapidaRegiao = new Brewer.PesquisaRapidaRegiao();
	pesquisaRapidaRegiao.iniciar();
});