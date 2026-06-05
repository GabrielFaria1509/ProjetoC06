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
 
🤖 Uso de Inteligência Artificial (Declaração de Transparência)
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
