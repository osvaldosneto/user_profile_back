# Spring Boot Backend

## Descrição

Este projeto é um backend desenvolvido em Spring Boot que fornece uma API RESTful para gerenciamento de perfis de usuário. Ele inclui funcionalidades para autenticação, registro, atualização de perfis e gerenciamento de permissões, servindo como a camada de serviço da aplicação Angular correspondente.

## Funcionalidades Principais

- **Autenticação e Registro de Usuários:** Permite que os usuários façam login e registro com autenticação baseada em JWT.
- **Gerenciamento de Perfis:** API para visualizar e atualizar informações do perfil do usuário.
- **Gerenciamento de Permissões:** Controle de acesso baseado em papéis (roles), permitindo que apenas usuários autorizados acessem certas funcionalidades.

## Requisitos

Para executar esta aplicação, você precisará dos seguintes softwares instalados:

- **Java JDK 17 ou superior**: [Instale Java](https://adoptium.net/)
- **Maven 3.8.4 ou superior**: [Instale Maven](https://maven.apache.org/install.html)
- **Git**: Para controle de versão e clonagem do repositório.

## Instalação

Siga os passos abaixo para configurar e executar a aplicação localmente:

1. **Clone o Repositório:**

   ```bash
   https://github.com/osvaldosneto/user_profile_back.git
   cd user_profile_back
   ``` 

2. **Instale as Dependências:**

Execute o comando abaixo para instalar as dependências do projeto:

   ```bash
   mvn clean install
  ```

3. **Configuração de Variáveis de Ambiente:**

Execute o comando abaixo para instalar as dependências do projeto:

   ```properties
   DB_USER: user do banco de dados
   DB_PASSWORD: senha do banco
   DB_HOST: host do banco
   DB_PORT: porta do mysql
   DB_SCHEMA_NAME: schema
```

## Executando a Aplicação
Para iniciar o servidor Spring Boot, execute o comando:
   ```bash
   mvn spring-boot:run
   ```
