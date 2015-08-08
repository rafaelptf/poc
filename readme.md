# poc
Repositório com POCs realizadas utilizando Spring-boot, Hibernate, Rest-Assured, Mockito, etc ...

### CORREIOS-INTEGRATION
O projeto correios-integration é responsável por mostrar a integracao com um serviço Rest de terceiros e prover uma API Rest para consulta de CEP.

Para executar a aplicação:
Realizar o build:
-> mvn package

Depois iniciar a aplicação:
-> java -jar target/correios-integration-{VERSAO}.jar

Exemplo:
   java -jar target/correios-integration-1.0-SNAPSHOT.jar

A aplicação estará disponível na porta 8080. 

CEPs poderão ser consultados a partir da seguinte URL:
https://localhost:8080/cep/{cep}

Exemplo:
https://localhost:8080/cep/01504-001

### ADDRESS-MANAGER
O projeto addres-manager é responsável por mostrar um CRUD completo, desde a API Rest até a integração com o bando de dados. Neste projeto também tem POC de testes integrados com rest-assured.

Para realizar o build com os testes integrados:
-> mvn integration-test -P integration

Para executar a aplicação:
Realizar o build:
-> mvn package

Depois iniciar a aplicação:
-> java -jar target/address-manager-{VERSAO}.jar

Exemplo:
   java -jar target/address-manager-1.0-SNAPSHOT.jar

A aplicação estará disponível na porta 8081. 

Esta aplicação depende da aplicação correios-integration. Para correto funcionamento, subir também esta aplicação. A integração é utilizada nas features de criação e atualização de endereços, para validar o CEP inserido.