# CompassUOL-SP-Challenge-03-Jonathan
## PROGRAMA DE BOLSAS SP – Backend Journey (Srping Boot) AWS Cloud Context CHALLENGE 3 - Jonathan Julião
___

### Portas utilizadas:
*  api-gateway: 8080
*  ms-products: 8081
*  ms-authorization: 8082
*  ms-notification: 8083
*  Eureka-server: 8761


### Informações sobre o DB:
A senha do usuários inserido diretamente no banco foram criptografada
usando o site https://bcrypt-generator.com/. A senha para o usuario admin é admin123
e o usuario é user123. Se houver problemas com o acesso, tente criar o user, role,
category e product de forma manual pelo Postman. O Role só pode ser criado por um user 
do tipo ADMIN e o um ADMIN só pode ser criado diretamente no BD ou por outro ADMIN através do
path /users/admin

### Em relação ao envio de e-mails:
No ms-notification vá até o package MailConfig, e substitua o email e senha que estiver lá
pelo seu email e senha. Não deixei o meu lá pelo motivo obvio de segurança. 
Vale ressaltar que a senha do gmail para utilizar na parte de desenvolvimento é gerada
a parte, se você colocar a senha usual o micro-serviço não irá rodar.

### Sobre o token
O token que gerei não funciona na parte de authorization do Postamn, 
pois não se trata de bearer token.
Portanto para autenticar utilizando o token no Postman, vá até Headers, na Key selecione Authorization
e em value adicione o token gerado no login.

### Sobre os testes
Não conseguir ter tempo para fazer tudo e também implementar os testes.
Como essa parte de testes já foi avaliada no desafio passado, resolvir deixar por último justamente
para caso não desse tempo, ser a "única" coisa que ficou faltando.