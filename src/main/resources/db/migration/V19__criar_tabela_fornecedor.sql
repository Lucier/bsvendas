CREATE TABLE fornecedor (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    tipo_pessoa VARCHAR(15) NOT NULL,
    cpf_cnpj VARCHAR(30),
    telefone VARCHAR(20),
    email VARCHAR(50) NOT NULL,
    logradouro VARCHAR(50),
    numero VARCHAR(15),
    complemento VARCHAR(20),
    cep VARCHAR(15),
    cidade VARCHAR(200),
    estado VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;