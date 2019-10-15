# Crud Spring Boot - Pet Schedule

# Criação do banco de dados e Massa de Dados

## Configuração do banco de dados
- Deve ser criada uma base de dados chamada "pet";
- Configurar o usuário e senha nos arquivos application.properties e liquibase.properties;
- A executar a aplicação, o liquibase irá criar as tabelas e inserir os dados necessários para testes.

## Massa de Dados gerada pelo Liquibase
 São gerados 2 (dois) clientes, sendo que cada um possui 2 Pets. Os CPFs dos dois clientes são os seguintes:
 - 96710386031 (Cliente 1);
 - 31280914041 (Cliente 2);

Também são gerados 3 funcionários, com os seguintes CPFs:
- 79616992015 (Fucnionário 1);
- 19269098010 (Veterinário 1);
- 15106241049 (Veterinário 2).

#### Alguns outros detalhes da massa de dados inicial
- Cada cliente possui 2 (dois) pets;
- Cada pet possui 1 (uma) consulta agendada;
- Cada veterinário possui 2 (duas) consultas agendadas.

# Documentação da API Rest
   * Todos os endpoints foram documentados com o Swagger;
   * O acesso ao swagger deve ser feito pela url http://localhost:8080/swagger-ui.html; 
   * É possível testar todo o funcionamento da API pelo Swagger, inclusive a autenticação e a utilização do token JWT;

# Autenticação
   **ATENÇÃO: O username pode ser qualquer um dos CPFs dos funcionários e a senha padrão é "1234".**
   
   * Para efetuar o login, deve ser invocado o /login, passando um objeto Json com username e password;
   * No retorno com sucesso da solicitação de login, será devolvido no header do response o token JWT que deverá ser informado nas requisições feitas aos endpoints protegidos;
   * O endpoint /consultas/findByCliente é o único endpoint que não exige que seja informado o token.

