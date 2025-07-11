# Microserviço de Produtos - Tech Challenge FIAP

Este é um microserviço responsável pelo gerenciamento de produtos, parte do sistema de e-commerce desenvolvido como Tech Challenge da FIAP. O serviço fornece operações CRUD (Create, Read, Update, Delete) para produtos e implementa boas práticas de desenvolvimento com Spring Boot.

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- JUnit 5
- Maven
- Swagger/OpenAPI

## 📁 Estrutura do Projeto

O projeto segue uma arquitetura limpa (Clean Architecture) com as seguintes camadas:

```
src/
├── main/
│   ├── java/
│   │   └── br/com/fiap/
│   │       ├── adapter/
│   │       │   ├── controller/    # Controllers da API
│   │       │   ├── gateway/       # Implementações de repositories
│   │       │   ├── mapper/        # Conversores de DTO
│   │       │   └── repository/    # Interfaces JPA
│   │       ├── core/
│   │       │   ├── domain/        # Entidades de domínio
│   │       │   ├── usecase/       # Casos de uso da aplicação
│   │       │   └── exception/     # Exceções customizadas
│   │       └── infrastructure/    # Configurações
│   └── resources/
│       ├── application.properties # Configurações da aplicação
│       └── db/                    # Scripts de migração
└── test/
    └── java/                      # Testes automatizados
```

## 🔧 Configuração e Instalação

### Pré-requisitos

- Docker e Docker Compose
- Java 17
- Maven (opcional, pode usar ./mvnw)

### 🐳 Rodando com Docker

1. Clone o repositório:
```bash
git clone https://github.com/fabiopinheirobatista/tech-challenge-4a-fase_produto.git
cd tech-challenge-4a-fase_produto
```

2. Construa e inicie os containers:
```bash
docker-compose up -d
```

O serviço estará disponível em `http://localhost:8080`

### 🖥️ Rodando Localmente

1. Clone o repositório
2. Configure o banco de dados PostgreSQL em `application.properties`
3. Execute:
```bash
./mvnw spring-boot:run
```

## 📚 API Endpoints

### Produtos

- `POST /produtos` - Criar novo produto
- `GET /produtos` - Listar todos os produtos
- `GET /produtos/{id}` - Buscar produto por ID
- `GET /produtos/nome/{nome}` - Buscar produtos por nome
- `GET /produtos/sku/{sku}` - Buscar produto por SKU
- `PUT /produtos/{id}` - Atualizar produto
- `DELETE /produtos/{id}` - Deletar produto

## 🧪 Testes

O projeto inclui testes unitários e de integração. Para executar os testes:

```bash
./mvnw test
```

### Cobertura de Testes

Para gerar o relatório de cobertura de testes:

```bash
./mvnw verify
```

O relatório será gerado em `target/site/jacoco/index.html`

## 📋 Exemplos de Requisições

### Criar Produto
```json
POST /produtos
{
    "nome": "Produto Teste",
    "sku": "SKU123",
    "preco": 99.90,
    "descricao": "Descrição do produto",
    "quantidade": 10
}
```

### Atualizar Produto
```json
PUT /produtos/{id}
{
    "nome": "Produto Atualizado",
    "sku": "SKU123",
    "preco": 149.90,
    "descricao": "Nova descrição",
    "quantidade": 20
}
```

## 🔍 Documentação da API

A documentação completa da API está disponível através do Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

## 🏗️ Arquitetura

O projeto segue os princípios da Clean Architecture:

1. **Core (Domain)**: Contém as regras de negócio e entidades
2. **Use Cases**: Implementa os casos de uso da aplicação
3. **Adapters**: Contém controllers, repositories e conversores
4. **Infrastructure**: Configurações e detalhes técnicos

### Padrões de Projeto Utilizados

- DTO (Data Transfer Object)
- Repository Pattern
- Dependency Injection
- Factory Method
- Builder

## 🔐 Segurança

- Validação de entrada de dados
- Tratamento de exceções
- Logs de operações
- Transações atômicas

## 🚀 CI/CD

O projeto utiliza GitHub Actions para:

- Execução de testes
- Análise de código
- Build do projeto
- Geração de imagem Docker
- Deploy automático

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Contribuindo

1. Faça um fork do projeto
2. Crie sua feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 🐛 Reportando Problemas

Encontrou um bug? Por favor, abra uma issue descrevendo o problema e como reproduzi-lo.
