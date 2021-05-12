# Projeto de Seleção Back-End Júnior da Mobicare #

Feito em java, utilizando Spring Boot como framework.

## Requisitos ##

* jre 11
* Mysql
* Maven

### Requisitos Feitos ###
* As quatro funcionalidades requisitadas
* Listar colaboradores agrupados por setor retorna um DTO com apenas os campos pertinentes
* Validação para a inserção de dados do colaborador
* Regras de inserção de um novo colaborador aplicadas

### Extras ###

* Documentação da API via swagger em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html), uma vez que o código esteja rodando como um spring boot app
* Utilização de caching para a listagem de colaboradores agrupados por setor
* Atualização da idade dos colaboradores via Scheduler todo dia às meia-noite e um minuto (00:01)
* Utilização de DTO de colaborador para a requisição POST para reduzir exposição do banco de dados
* Soft delete de colaboradores

### Desenvolvedor ###

* Michel Jean Katsilis (michelkatsilis@gmail.com)