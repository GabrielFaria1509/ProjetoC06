# 🌐 Sistema de Gerenciamento de Roteadores (ProvedorManagement)

## 📌 Sobre o Projeto
Este projeto é um simulador de gerenciamento de dispositivos de rede, focado na modelagem do comportamento de **Roteadores**. Desenvolvido com forte base em **Programação Orientada a Objetos (POO)** em Java, o sistema permite instanciar equipamentos, gerenciar conexões de dispositivos locais (Hosts), bloquear acessos indesejados e monitorar o tráfego de rede.

O objetivo principal da arquitetura não é apenas simular uma rede, mas demonstrar a aplicação prática de pilares essenciais do desenvolvimento de software, como Abstração, Encapsulamento, Regras de Negócio de Imutabilidade e Escopo de Variáveis.

---

## ⚙️ Arquitetura e Padrões de Projeto (POO)

O coração do sistema é a classe `Casa.Roteador.java`. Ela foi projetada seguindo as melhores práticas da engenharia de software:

* **Abstração (`abstract class`):** A classe `Casa.Roteador` é abstrata. No mundo real, não compramos um "roteador genérico", mas sim modelos específicos (Domésticos, Empresariais, etc.). A classe serve como um molde rigoroso para que todas as subclasses herdem os mesmos comportamentos de rede.
* **Encapsulamento Rígido:** Atributos fundamentais como `marca`, `modelo` e `preco` são protegidos (`protected`) e definidos apenas no momento de instanciação via **Construtor**. Omitimos deliberadamente os métodos `Setters` para garantir a **Imutabilidade** do hardware — uma vez instanciado, um aparelho não pode ter sua marca alterada em tempo de execução.
* **Escopo de Memória (`static`):** O sistema diferencia perfeitamente o estado individual de cada objeto do estado global da aplicação. Utilizamos uma variável estática (`totalDispositivosConectados`) para manter o histórico global de dispositivos conectados em toda a infraestrutura, demonstrando domínio sobre o ciclo de vida da memória em Java.
* **Validação e Segurança:** Métodos internos de manipulação de dados (como o agrupamento de IPs) trabalham em conjunto com métodos públicos (`conectar()`) para evitar injeções diretas e garantir que as regras de negócio (como impedir a conexão de IPs duplicados) sejam estritamente respeitadas.

---

## 🚀 Funcionalidades Principais

1. **Protocolo de Conexão Rigoroso:** O método `conectar(String ip)` verifica ativamente se o IP requisitado já existe na rede local. Se o IP estiver livre, a conexão é estabelecida e o contador global é incrementado. Caso contrário, a conexão é barrada, prevenindo conflitos de rede.
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

* **André Alves Araújo** - CLasse Casa.Roteador
  * Desenvolvimento de toda a estrutura base e lógica de negócio da classe mãe `Casa.Roteador`.
  * Implementação do encapsulamento rígido (atributos protegidos e criação dos Getters).
  * Criação da lógica de escopo de memória (variáveis e métodos estáticos para o contador global de dispositivos).
  * Desenvolvimento da lógica interna dos métodos operacionais (`conectar`, `atribuirIP`, `bloquearSite` e `atualizarIP`).
 
* **Gabriel Faria** - CLasse Casa.Roteador
  * Modelagem e design majoritário do diagrama UML da arquitetura.
  * Implementação do tratamento de exceções (`try/catch`) para a segurança das conexões.
  * Estruturação da relação de Agregação com a classe `Casa.Host`.
  * Estruturação da relação de Composição com a classe `Casa.WIFI`, incluindo a adaptação avançada do construtor da superclasse.
  * Definição e assinatura dos métodos que as classes filhas (subclasses) irão implementar.
