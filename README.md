# SimpleMicroserviceProject
### Projeto backend java com spring framework.
Este projeto foi desenvolvido como parte de uma desafio backend solicitado na formação backend java da EBAC
Nele foi desenvolvido usando a arquitetura de micrisserviços um ecommerce.

### Sobre Microsserviços
Microservices ou Microsserviços são pequenos módulos independentes que possuem fronteiras claras entre e si e podem se comunicar ou não
sendo esta comunicação normalmente via protocolos HTTP.
Em resumo um microsserviço encapsula uma funcionalidade e a trona acessível através da rede, esta arquitetura é orientada a serviços com uma definição bem clara sobre as fronteiras dos serviços e como devem ser traçadas. Neste caso as funcionalidade de cada serviço e exposta através de APIs REST

#### Composição do projeto
Neste projeto temos os servios
- CLiente
- Produto
- Vendas
Adicionalmente temos um servidor de configuração e um servidor de descoberta (Service Discovery)

#### Cliente - Service
Este serviço gerencia e expõe as APIs REST responsáveis por cliente, endereço e contatos

- Java 17
- Spring Boot 3.5.6
- Hibernate
- JPA
- DataBase - PostgreSQL
- 

#### Produto - Service
Este serviço gerencia e expõe a API REST responsável por produto

- Java 17
- Spring Boot 3.5.6
- Spring data-mongo
- DataBase - Mongodb

  #### Vendas - Service
Este serviço gerencia e expõe a API REST responsável por vendas

- Java 17
- Spring Boot 3.5.6
- Spring data-mongo
- DataBase - Mongodb
- Adicionalmente ele consome via OpenFeign os serviços Produto e Cliente

## Como executar o projeto
Instruções para executar o projeto

-  Docker compose up na raiz do projeto - subirar os bancos de dados.
-  Inicie a execução atraves do projeto ConfigServer - reponsável por disponibilizar as configurações que serão usadas.
-  Inicie o EurekaSevice para que os demais possam se registrar ao serem iniciados.
-  Inicie os demais serivços, Produto, CLiente e Vendas.

# Diagrama

