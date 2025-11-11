# Gestor de Estoque (Projeto de Aplicativos Móveis)
Este é um aplicativo Android nativo para gerenciamento de estoque, desenvolvido em Kotlin utilizando Jetpack Compose. O projeto foi criado como parte da disciplina de Desenvolvimento de Aplicativos Móveis, demonstrando a implementação de uma arquitetura moderna (MVVM), persistência de dados local (Room) e sincronização em nuvem (Firebase Firestore).

## Funcionalidades
O aplicativo permite o gerenciamento completo de produtos e suas respectivas categorias, com as seguintes funcionalidades:

### - Gerenciamento de Produtos:

Listar e filtrar produtos por nome.

Cadastrar novos produtos, associando-os a uma categoria existente.

Editar informações detalhadas de um produto (nome, preço, quantidade, categoria).

Excluir produtos do estoque.

### - Gerenciamento de Categorias:

Listar e filtrar categorias por nome.

Cadastrar novas categorias.

Editar o nome e a descrição de categorias existentes.

Excluir categorias.

### - Persistência e Sincronização:

Os dados são armazenados localmente em um banco de dados Room, permitindo o uso offline do app.

O app sincroniza os dados (criação, edição e exclusão) com o Firebase Firestore ao iniciar e ao realizar operações, garantindo um backup na nuvem.

## Tecnologias Utilizadas
Linguagem: 100% Kotlin

UI: Jetpack Compose para uma interface de usuário declarativa e moderna.

Navegação: Navigation Compose para gerenciar o fluxo de telas em uma Single-Activity.

Arquitetura: MVVM (Model-View-ViewModel).

Persistência Local: Room (parte do Jetpack) para o banco de dados local SQLite.

Banco de Dados na Nuvem: Firebase Firestore para sincronização de dados.

O banco de dados na nuvem sendo o principal, e o local servindo como cache, isso faz o app funcionar sem internet, e ao adicionar um item sem conexão com a internet, ele aparecera nos menus assim que a conexão for reestabelecida.

Gerenciamento de Estado: ViewModel com StateFlow e collectAsStateWithLifecycle para um gerenciamento de estado reativo e seguro.

Programação Assíncrona: Kotlin Coroutines para gerenciar chamadas de banco de dados e de rede sem travar a UI.

## Arquitetura
O projeto segue rigorosamente a arquitetura MVVM, separando as responsabilidades em camadas claras:

View (UI Layer): Composta por Composable functions (ex: ProdutoPage.kt, DetalhesProdutoPage.kt). Esta camada apenas observa o estado do ViewModel e envia eventos de usuário (como cliques).

ViewModel: Classes como ProdutoViewModel e CategoriaViewModel. Elas contêm a lógica de negócios, gerenciam o estado da UI (via StateFlow) e se comunicam com o Repository.

Model (Data Layer): Composta pelos Repository (ex: ProdutoRepository). O Repository é a única fonte da verdade, decidindo se busca dados do Room (DAO) ou do Firebase, e mantendo ambos sincronizados.

Data Sources:

data.local (Room DAOs): Define a interface de acesso ao banco de dados local.

FirebaseFirestore: Acesso direto ao SDK do Firebase para operações na nuvem.

## Documentação do Projeto
Os diagramas de fluxo de navegação e da estrutura do banco de dados (relacionamento entre entidades) estão disponíveis nos documentos de planejamento do projeto (Presentation.pdf e Sistema-de-Gerenciamento-de-Estoque.pdf).

Fluxo de Navegação
O app utiliza uma MainActivity como container principal (Single-Activity) e o Navigation Compose gerencia a troca entre as telas (Composables).

Estrutura do Banco de Dados (Room e Firestore)
O banco de dados (local e remoto) é composto por duas entidades principais com um relacionamento 1-N:

Categorias (1): Armazena os dados das categorias.

id (String)

nome (String)

descricao (String)

Produtos (N): Armazena os produtos, onde cada produto pertence a uma categoria.

id (String)

nome (String)

descricao (String)

quantidade (Int)

preco (Double)

dataCadastro (Long/Timestamp)

categoriaId (String) - Chave estrangeira que referencia o id da Categoria.

## Como Executar o Projeto
Pré-requisitos
Android Studio (versão mais recente recomendada)

Uma conta Google para acesso ao Firebase.

1. Configuração do Firebase (Obrigatório)
O projeto requer uma conexão com o Firebase para funcionar. O arquivo google-services.json incluído no repositório NÃO é válido e deve ser substituído pelo seu.

Acesse o Console do Firebase.

Crie um novo projeto.

No painel do projeto, adicione um novo aplicativo Android.

No campo "Nome do pacote Android", insira exatamente: com.example.gestordeestoque.

Siga os passos para registrar o app e baixe o arquivo google-services.json.

Substitua o arquivo app/google-services.json no projeto pelo arquivo que você acabou de baixar.

No console do Firebase, vá até a seção "Cloud Firestore".

Crie um novo banco de dados (comece em modo de teste, se preferir).

Importante: Você precisará criar manualmente as coleções produtos e categorias para que o app possa sincronizar com elas, ou o app as criará na primeira escrita.

2. Executando o App
Clone este repositório:

Bash

git clone 
Abra o projeto no Android Studio.

Espere o Gradle sincronizar (após ter substituído o google-services.json).

Compile e execute o aplicativo em um emulador ou dispositivo Android físico.
