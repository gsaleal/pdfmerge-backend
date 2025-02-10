# PDF Merge Backend

Este é um serviço backend que permite mesclar múltiplos arquivos PDF em um único arquivo. O serviço inclui uma API RESTful para realizar operações de mesclagem de PDFs e gerenciar o histórico das operações.
Necessário criar o topico pdf-merge-group no kafka

## Funcionalidades

- **Mesclar arquivos PDF**: Aceita múltiplos arquivos PDF e os mescla em um único PDF.
- **Histórico de mesclagem**: Armazena o histórico de todas as operações de mesclagem com detalhes sobre os arquivos mesclados e o link de download.

## Endpoints da API

### `POST /api/pdf/merge`
Este endpoint aceita múltiplos arquivos PDF e os mescla em um único arquivo.

#### Request
- **URL**: `/api/pdf/merge`
- **Método**: `POST`
- **Body**:
  - **files**: Lista de arquivos PDF (multipart).
  - **outputFileName**: Nome desejado para o arquivo de saída.

#### Exemplo de Request no Postman:
```json
{
  "files": [
    "file1.pdf",
    "file2.pdf"
  ],
  "outputFileName": "mesclado.pdf"
}
