# App Lista Telefônica

Aplicativo Android de agenda de contatos com armazenamento local em SQLite, conta também com ações para ligar, mandar email e localizar o endereço com a api do GoogleMaps.

---

## Contexto

Projeto pessoal desenvolvido em 2022 para aprender um pouco sobre desenvolvimento Android.

---

## Funcionalidades

- Cadastro, edição e exclusão de contatos
- Listagem dos contatos
- Ligar para o contato pelo discador do sistema
- Enviar e-mail pelo aplicativo padrão
- Visualizar o endereço do contato em um mapa

---

## Decisões técnicas

### Restrições no banco e na coleta de dados

O código verifica se as informações do contato foram preenchidas, mas também cria o banco de dados com as colunas de nome e telefone como `UNIQUE`, ou seja, a tabela não aceita novas linhas com valores que já existem para essas colunas.

### Consultas parametrizadas

Todas as operações de busca, atualização e exclusão passam os valores como parâmetros separados, não como uma string concatenado, assim evitam problemas de tipagem e, principalmente, impedem SQL injection, ou seja, impedem que comandos SQL dados como entrada sejam processados, evitando vazamento do banco de dados.

### Uso de RecyclerView

Ao invés de criar uma estrutura que cria novas linhas quando um contato é adicionado, usa-se um RecyclerView, que cria apenas Views suficientes para preencher a tela e conforme o usuário rola a tela, o RecyclerView pega as Views já criadas e empilha no final da tela com os novos valores, economizando processamento e memória.

### Lista só é reprocessada quando necessário

As Activities que geram mudanças no banco de dados retornam um código de saída específico à Activity principal(ContatoAcitivity), assim a lista só é reprocessada e o `RecyclerView` reconstruído quando o banco de dados é alterado.

### Delegar as ações ao sistema

As ações de ligar, mandar email e localizar usam Intent's implícitas, ou seja, delegam ao sistema escolher quem vai recebê-las.

---

## Stack

- **Java**
- **SQLite** (`SQLiteOpenHelper`) — persistência local
- **RecyclerView** — listagem dos contatos
- **Google Maps SDK** + **Geocoder** — exibição do endereço no mapa

---

## Como executar

### Pré-requisitos

- **Android Studio**
- **JDK 8+**
- Uma **chave de API do Google Maps** (obtida no Google Cloud Console, com a Maps SDK for Android ativada)

### Instalação

```bash
git clone https://github.com/SEU-USUARIO/app-lista-telefonica.git
```

Abra a pasta no Android Studio e aguarde a sincronização do Gradle.

### Configuração

Preencha sua chave do Google Maps nos dois arquivos abaixo, substituindo `YOUR_KEY_HERE`:

```
app/src/debug/res/values/google_maps_api.xml
app/src/release/res/values/google_maps_api.xml
```

Sem a chave o aplicativo compila e funciona normalmente, mas a tela do mapa aparece em branco.

### Execução

Rode em um emulador ou dispositivo físico pelo próprio Android Studio.

---

## Erros e limitações conhecidas

- **O `onUpgrade` do banco está mal implementado:** a função apenas exclui a tabela já existente e chama o `onCreate`, ou seja, caso o aplicativo recebesse uma atualização, todo o banco de dados seria excluído, assim perderia-se os contatos. O certo seria ir acrescentando as mudanças das atualizações no banco de uma em uma versão, com uma condicional para cada.
- **A lista e a RecyclerView é reconstruída inteira a cada alteração:** quando a Activity principal(ContatoActivity) recebe um código de retorno de Intent que sinaliza que o banco de dados foi modificado, ela reconstroi toda a lista a partir do banco de dados e reinicializa o `RecyclerView` ao invés de deletar e/ou inserir as mudanças com `notifyItemInserted(pos)`, `notifyItemChanged(pos)`, `notifyItemRemoved(pos)`, ou seja, da forma atual não se extrai todo o potencial de otimização do `RecyclerView`.
- **O DAO e o construtor de conexão são juntos:** não há um objeto DAO que receba a conexão e faça exclusivamente as operações no banco de dados, ele é implementado junto com o criador de conexão do banco de dados, poderia-se separar os dois, como a arquitetura padrão sugere, assim o objeto DAO seria inicializado com a conexão e não teria acesso à conexão do banco em si.
- **Os cursores das consultas não são fechados:** como o banco é local, quando se cria uma conexão, o SQLiteOpenHelper apenas retorna a referência na memória, não cria e empilha conexões como em um banco de dados externo, assim não é necessário se preocupar com o fechamento de conexões. Contudo, quando se faz uma requisição, os requerimentos e respostas ainda são acumulados na memória, assim, após cada consulta, deveria-se fechar esses cursores, otimizando recursos, algo que o código não faz.
- **Os listeners dos botões das linhas do RecyclerView são redeclarados excessivamente:** os listeners dos botões do RecyclerView são redeclarados no onBind, ou seja, quando se rola o RecyclerView e recicla-se os Views já existentes, o que é executado quando os botões são clicados é reescrito, contudo essas funções nunca são modificadas, apenas dependem do id da linha, que pode ser obtido pelo RecyclerView, assim essas declarações dos listeners poderia estar no onCreate do RecyclerView, sendo criadas apenas nas poucas views que o RecyclerView efetivamente cria, otimizando a alocação de recursos.
- **O projeto não segue o padrão de arquitetura mais moderno:** não precisa seguir, pois é muito pequeno e seria até overengineering, mas cabe notar as diferenças. A arquitetura mais moderna proposta para esses casos de uso de SQLite é a MVVM, a primeira camada (de interface) é a Activity, a segunda (a de negócio) é a ViewModel e a terceira (a de persistência) é o Repository com o Room. A Activity somente recebe os valores e envia-os para o ViewModel. O ViewModel realiza a lógica em si e pede ao Repository para modificar o banco. Já o Repository controla o banco de dados indiretamente, o ViewModel envia as modificações a serem feitas para ele e ele acessa o Room para inicializar e realizar as operações. O Room é uma decisão de template, você escolhe o AppDataBase, que é o inicializador do banco de dados e o DAO é escrito automaticamente para essas configurações, assim o Room naturalmente já fecha os cursores da operação, sem o ViewModel ter que conhecer o DAO e fechá-los ativamente. A questão de conexão continua não sendo um problema, pois a conexão local é muito barata e única (é uma referência apenas), então fica sempre aberta, ou seja, o ViewModel não precisa se preocupar em quando fechar ou não a conexão, e os cursores o próprio DAO cuida, como previsto na arquitetura, assim são seguidas as convenções da arquitetura em camadas, diferente de agora em que a interface e os negócios estão misturados. Além disso, a Activity precisa receber essas modificações e mostrá-las para o usuário, mas ao mesmo tempo nem o ViewModel nem o Repository devem chamar a Activity, pois isso formaria um ciclo e quebraria a arquitetura em camadas, para isso utiliza-se LiveData, uma estrutura que recebe as modificações do Room e que a Activity está sempre monitorando, assim quando algo é modificado no banco, o LiveData é atualizado e comunica a Activity, que repassa para o usuário, mas a arquitetura não é rompida, pois nenhuma camada de baixo conhece a de cima, é só uma forma de sincronização. Também pode usar um LiveData entre o ViewModel e a Activity para emitir mensagens Toast para o usuário quando algo é feito no ViewModel.

---
