# 🏠 AirbnTruta

O **AirbnTruta** é um sistema de gerenciamento de acomodações e reservas, desenvolvido para facilitar a conexão entre anfitriões e hóspedes (e trutas!). O projeto foi construído utilizando **Java** com **Spring Boot** no back-end e **MySQL** como banco de dados.

## 🚀 Tecnologias Utilizadas

* **Java 17** (ou superior)
* **Spring Boot 3** (Framework principal)
* **Spring Data JPA** (Persistência de dados)
* **MySQL** (Banco de dados relacional)
* **Maven** (Gerenciamento de dependências)

---

## 🛠️ Pré-requisitos

Antes de começar, você precisa ter instalado em sua máquina:

1.  [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
2.  [Maven](https://maven.apache.org/download.cgi) (Geralmente já vem no VS Code)
3.  [MySQL Server](https://dev.mysql.com/downloads/installer/) e um cliente (Workbench, DBeaver ou Extensão do VS Code).
4.  [VS Code](https://code.visualstudio.com/) com o **Extension Pack for Java**.

---

## ⚙️ Configuração do Banco de Dados

Antes de rodar o projeto, é necessário criar o banco de dados.

1.  Abra seu cliente MySQL (Terminal, Workbench ou Extensão do VS Code).
2.  Execute o seguinte comando SQL:

```sql
CREATE DATABASE airbntruta;
