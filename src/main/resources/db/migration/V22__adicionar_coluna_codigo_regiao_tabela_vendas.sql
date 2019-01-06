ALTER TABLE venda
	ADD codigo_regiao BIGINT(20),
	ADD CONSTRAINT codigo_regiao FOREIGN KEY (codigo_regiao) REFERENCES regiao(codigo);