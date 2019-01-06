CREATE TABLE ordemservico (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descricao_equipamento VARCHAR(100) NOT NULL,
    numero_serie_equipamento VARCHAR(50) NOT NULL,
    marca_equipamento VARCHAR(100),
    modelo_equipamento VARCHAR(100),
    defeito_equipamento VARCHAR(200) NOT NULL,
    data_emissao VARCHAR(30),
    data_encerramento VARCHAR(30),
    codigo_cliente BIGINT(20) NOT NULL,
    statusOS VARCHAR(50) NOT NULL,
    FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;