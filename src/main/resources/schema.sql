DROP TABLE IF EXISTS colaborador;

DROP TABLE IF EXISTS setor;

CREATE TABLE setor (
  id bigint NOT NULL AUTO_INCREMENT,
  descricao varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE colaborador (
  id bigint NOT NULL AUTO_INCREMENT,
  cpf varchar(11) NOT NULL,
  created_at datetime(6) NOT NULL,
  deleted boolean NOT NULL,
  data_nascimento date NOT NULL,
  email varchar(255) NOT NULL,
  idade int NOT NULL,
  nome varchar(255) NOT NULL,
  telefone varchar(13) NOT NULL,
  setor_id bigint NOT NULL,
  PRIMARY KEY (id),
  KEY FKia1xdsbmy6pkus4mrgy90c3he (setor_id),
  CONSTRAINT FKia1xdsbmy6pkus4mrgy90c3he FOREIGN KEY (setor_id) REFERENCES setor (id)
);