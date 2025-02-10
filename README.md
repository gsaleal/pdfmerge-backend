# PDFMerge Backend

Este é um serviço de mesclagem de PDFs construído com **Spring Boot**, **Kafka** e **PostgreSQL**. Ele recebe arquivos PDF, processa a mesclagem de forma assíncrona e retorna um link para download.

## 📌 Funcionalidades
- Upload de arquivos PDF para mesclagem
- Processamento assíncrono com **Kafka**
- Histórico de operações armazenado no banco de dados
- Retorno de um link para download do PDF final

## 🚀 Tecnologias
- **Java 17** + **Spring Boot**
- **Kafka** para processamento assíncrono
- **PostgreSQL** para armazenamento
- **Docker** para ambiente conteinerizado

## ⚙️ Configuração do Projeto

### Pré-requisitos
Certifique-se de ter instalado:
- **JDK 17+**
- **Docker** e **Docker Compose**
- **Kafka** e **Zookeeper**
- **PostgreSQL**

### Passos para rodar o projeto localmente

1. Clone o repositório:
   ```sh
   git clone https://github.com/gsaleal/pdfmerge-backend.git
   cd pdfmerge-backend
   ```

3. A API estará disponível em:
  ```http
  http://localhost:8080
  ```
