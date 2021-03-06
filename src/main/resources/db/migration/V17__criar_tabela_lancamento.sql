CREATE TABLE lancamento (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento VARCHAR(20) NOT NULL,
	data_pagamento VARCHAR(20),
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipoLancamento VARCHAR(20) NOT NULL,
	codigo_categoria BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;