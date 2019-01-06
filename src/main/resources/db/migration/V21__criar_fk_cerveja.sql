ALTER TABLE cerveja
	ADD codigo_categoria BIGINT(20),
	ADD codigo_fornecedor BIGINT(20),
	ADD CONSTRAINT codigo_categoria FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo),
	ADD CONSTRAINT codigo_fornecedor FOREIGN KEY (codigo_fornecedor) REFERENCES fornecedor (codigo);




	
