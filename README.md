# 🌐 Sistema de Gerenciamento de Roteadores (ProvedorManagement)

## 📌 Sobre o Projeto
Este projeto é um simulador de gerenciamento de dispositivos de rede, focado na modelagem do comportamento de **Roteadores**. Desenvolvido com forte base em **Programação Orientada a Objetos (POO)** em Java, o sistema permite instanciar equipamentos, gerenciar conexões de dispositivos locais (Hosts), bloquear acessos indesejados e monitorar o tráfego de rede.

O objetivo principal da arquitetura não é apenas simular uma rede, mas demonstrar a aplicação prática de pilares essenciais do desenvolvimento de software, como Abstração, Encapsulamento, Regras de Negócio de Imutabilidade e Escopo de Variáveis.

---

## ⚙️ Arquitetura e Padrões de Projeto (POO)

O coração do sistema é a classe `Roteador.java`. Ela foi projetada seguindo as melhores práticas da engenharia de software:

* **Abstração (`abstract class`):** A classe `Roteador` é abstrata. No mundo real, não compramos um "roteador genérico", mas sim modelos específicos (Domésticos, Empresariais, etc.). A classe serve como um molde rigoroso para que todas as subclasses herdem os mesmos comportamentos de rede.
* **Encapsulamento Rígido:** Atributos fundamentais como `marca`, `modelo` e `preco` são protegidos (`protected`) e definidos apenas no momento de instanciação via **Construtor**. Omitimos deliberadamente os métodos `Setters` para garantir a **Imutabilidade** do hardware — uma vez instanciado, um aparelho não pode ter sua marca alterada em tempo de execução.
* **Escopo de Memória (`static`):** O sistema diferencia perfeitamente o estado individual de cada objeto do estado global da aplicação. Utilizamos uma variável estática (`totalDispositivosConectados`) para manter o histórico global de dispositivos conectados em toda a infraestrutura, demonstrando domínio sobre o ciclo de vida da memória em Java.
* **Validação e Segurança:** Métodos internos de manipulação de dados (como o agrupamento de IPs) trabalham em conjunto com métodos públicos (`conectar()`) para evitar injeções diretas e garantir que as regras de negócio (como impedir a conexão de IPs duplicados) sejam estritamente respeitadas.

---

## 🚀 Funcionalidades Principais

1. **enumns.Protocolo de Conexão Rigoroso:** O método `conectar(String ip)` verifica ativamente se o IP requisitado já existe na rede local. Se o IP estiver livre, a conexão é estabelecida e o contador global é incrementado. Caso contrário, a conexão é barrada, prevenindo conflitos de rede.
2. **Gestão de Gateway:** Permite a atualização dinâmica do IP do Gateway do próprio roteador (`atualizarIP`), adaptando-o a diferentes topologias de rede.
3. **Controle de Acesso (Blacklist):** Implementa um mecanismo de bloqueio de URLs (`bloquearSite`), simulando um painel de controle de rede para restrição de acessos.
4. **Monitoramento Global:** Possui um método estático (`getTotalDispositivosConectados()`) que fornece um relatório unificado do total de conexões ativas na infraestrutura.

---

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java
* **Paradigmas:** Programação Orientada a Objetos (POO)
* **Estruturas de Dados:** `ArrayList` (para gerenciamento dinâmico de conexões e blacklist)

  ---

  ## 👥 Autores e Contribuições

Este projeto foi desenvolvido colaborativamente em gupo. Abaixo estão as responsabilidades e implementações principais de cada desenvolvedor:

* **André Alves Araújo** - Classe Roteador
  * Desenvolvimento de toda a estrutura base e lógica de negócio da classe mãe `Roteador`.
  * Implementação do encapsulamento rígido (atributos protegidos e criação dos Getters).
  * Criação da lógica de escopo de memória (variáveis e métodos estáticos para o contador global de dispositivos).
  * Desenvolvimento da lógica interna dos métodos operacionais (`conectar`, `atribuirIP`, `bloquearSite` e `atualizarIP`).
 
* **Gabriel Faria** - Classe Roteador
  * Modelagem e design majoritário do diagrama UML da arquitetura.
  * Implementação do tratamento de exceções (`try/catch`) para a segurança das conexões.
  * Estruturação da relação de Agregação com a classe `Host`.
  * Implementação de métodos abstratos como desconectar em Roteador
  * Estruturação da relação de Composição com a classe `WIFI`, incluindo a adaptação avançada do construtor da superclasse.
  * Definição e assinatura dos métodos que as classes filhas (subclasses) irão implementar.
  * Estruturação do metódo responsável pela leitura do arquivo.txt,gerando um array com essa leitura.
  * Organização dos pacotes na pasta do projeto.
 
* **Guilherme Carvalho** - Classe Host e WIFI
  * Estruturação da relação de Composição com a classe WIFI, garantindo o controle do ciclo de vida pela classe mãe.
  * Desenvolvimento da lógica (if/else) na classe WIFI para o cálculo da latência (pingMs) conforme o modelo do roteador.
  * Implementação da interface Runnable na classe Host para habilitar concorrência no envio simultâneo de pacotes.
  * Criação do método configurarConexao na classe Host, garantindo a injeção segura dos dados de rede gerados pelo Roteador.
  * Implementação do TCP no método run() da classe Host para simular a transmissão estável, ordenada e confiável.
  * Implementação do UDP no método run() da classe Host para simular a perda de pacotes e a instabilidade real do protocolo.

Uso de Inteligência Artificial

Durante o desenvolvimento das classes de rede e do roteador, utilizamos o assistente virtual Gemini para auxiliar na compreensão de conceitos de Programação Orientada a Objetos (POO) e no tratamento correto de exceções em Java. Nenhuma lógica de negócio principal foi gerada pela IA; o uso foi estritamente focado em arquitetura de código, sintaxe e code review (revisão de código).

Abaixo está o histórico de prompts e os conceitos discutidos:

Prompt 1: "Como faço mesmo o método para ler esses arquivos.txt e devolver uma variável que é uma array de String ? Sendo que cada elemento desse array é uma linha do arquivo.txt" (Acompanhado da imagem da assinatura do método lerArquivosIPs).

Resultado: Discussão sobre a classe java.nio.file.Files, o uso de Files.readAllLines e a conversão de List<String> para String[].

Prompt 2: (Envio de imagem mostrando os erros de compilação Unhandled exception: java.io.IOException e Missing return statement na IDE).

Resultado: Explicação sobre a obrigatoriedade do Java em tratar exceções nativas de I/O e a necessidade de todos os caminhos de um método retornarem um valor.

Prompt 3: "ok ok,porque uso new String[0] ? quero retornar um vetor de Strings"

Resultado: Esclarecimento sobre o conceito de Type Erasure no Java e como a JVM otimiza a criação de vetores usando [0] como parâmetro de tipagem no método .toArray().

Prompt 4: "Estava analisando essa parte do meu colega, e acho que por ter o throws no método dessa classe filha, o certo seria usar try catch e não if else, correto ?" (Acompanhado de imagem do método atribuirIP lançando exceção personalizada).

Resultado: Validação do uso de if/throw para regras de negócio (disparo de erro) e delegação do try-catch (captura do erro) para a classe principal.

Prompt 5: "Então esse try catch desse metódo da filha devia tá na main ,correto ?" (Acompanhado de imagens comparando o método conectar com a classe Main).

Resultado: Confirmação da arquitetura correta de exceções: classes de modelo lançam o erro (throws), e classes controladoras ou a Main tratam o erro (try-catch).

Prompt 6 e 7: "Esse método é da classe mãe Roteador,eu que fiz,veja se está certo.Ele não é abstrato mesmo" e "esse try catch com throws no próprio metódo então está certo msm ? e como assim as classes herdadas ganham esse metódo de graça ?"

Resultado: Validação da técnica de "Tradução de Exceção" (capturar um IOException nativo e lançar uma ExcecaoLeituraArquivos do próprio sistema). Discussão sobre reuso de código e como métodos concretos na classe mãe evitam duplicação de código nas classes filhas.

Prompt 8: "então o abstrato é legal usar quando quero que a classe filha faça algo mais específico né"

Resultado: Consolidação do conceito de métodos abstratos (abstract) como contratos, forçando as classes filhas a implementarem lógicas específicas (como o método gerarIP, que varia por tecnologia).

Prompt 9: "desconectar está com mesmo problema do conectar de antes né" (Acompanhado de imagem do método desconectar).

Resultado: Correção final de code review, aplicando os conceitos aprendidos sobre delegação de exceções para refatorar o método desconectar, removendo o try-catch indevido e aplicando o throws.

Guilherme Carvalho: utilizado o Gemini para fazer a parte de programação para mim com o intuito de como implementar a matéria de telecomunicações com programação, por mais que tivesse idéia de como funcionaria o projeto o Gemini quem me ensinou a utilizar no código. Porém o chat me deu respostas que eu não esperava:

Prompt 1: "mas pq o wifi seria uma composição?"

Resultado: Em termos simples, na composição, a classe filha não tem vida própria fora da classe mãe. Quem cria e quem destrói o WIFI é o Roteador.

Prompt 2: "ue mas se eu mudou a classe velocidade como variavel tenho que mudar algo na classe mae e filha num tenho"

Resposta: Sim, você tem toda razão! Se você transformar a velocidade em uma variável (um atributo) com o seu próprio private double velocidade; dentro da classe WIFI, você vai precisar ajustar como a informação é passada de uma classe para outra.

Prompt 3: "mas pq tem que passar pela main? não consigo enviar o ping que vem do roteador domestico direto para o host?"

Resposta: A resposta curta é: Não, o roteador também não envia o IP direto para o Host. Na verdade, tudo precisa passar pela Main porque ela funciona como o "orquestrador" (o maestro) do seu sistema.

Prompt 4: "isso e esse IP esta sendo buscado e gerado na subclasse roteador?"

Resposta: Sim, exatamente. No código que estruturámos, o IP é gerado de forma aleatória e buscado (verificado se está no arquivo .txt) inteiramente dentro da subclasse RoteadorDomestico.

Prompt 5: "espera o que foi isso que vc colocou na main? isso não é poo"

Reposta: Tens toda a razão em travar o código aí! Foste muito rápido a perceber o deslize. Peço-te desculpa, deixei-me levar pela lógica de um menu rápido e acabei por meter uma linha que quebra completamente o conceito de Polimorfismo e de POO.

Prompt 6: "eu não posso colocar um valor de ping na main isso sai do conceito de POO"

Resposta: Você tem toda a razão e peço desculpas pela nossa confusão! Me deu um "nó" na cabeça aqui e você me deu um puxão de orelha mais do que justo.

Prompt 7: "host é o equipamento que se conecta né?"

Resposta: Sim, exatamente! Em redes de computadores, o termo Host (que significa "hospedeiro") representa qualquer equipamento ou dispositivo que está conectado à rede, possui um endereço IP próprio e é capaz de enviar ou receber dados.


Luis:
Prompt 1: o código da classe RoteadorPortatil, fiquei com uma dúvida no método conectar(). Por que o IP é gerado antes de verificar se já está em uso? Não seria mais eficiente checar primeiro?

Resposta Boa pergunta! Esse comportamento é intencional e reflete como roteadores reais funcionam no modelo DHCP dinâmico.

O fluxo no método conectar() segue essa ordem:

    gerarIP() → cria "192.168.1.(150 + n)"
    verificar colisão → checa ipsAtribuidos
    atribuirIP() → adiciona à lista
    configurarConexao() → configura o Host A razão para gerar antes de checar é que o IP precisa existir para ser verificado. O método gerarIP() calcula o valor baseado no tamanho atual da lista (ipsAtribuidos.size() + 150), e só depois esse valor gerado é confrontado com os IPs já ocupados.

Se a checagem fosse feita antes, o que você verificaria? Ainda não haveria um IP concreto para comparar.
