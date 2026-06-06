import java.util.Scanner;
import classes.Host;
import classes.Roteador;
import classes.RoteadorDomestico;
import classes.RoteadorEmpresarial;
import classes.RoteadorPortatil;
import tratamentoexcecoes.ExcecaoAtribuirHOST;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        System.out.println("====== SISTEMA DE SIMULAÇÃO DE REDES E PROTOCOLOS ======");

        while (opcao != 4) {
            System.out.println("\nSelecione qual roteador deseja utilizar para a simulação:");
            System.out.println("1 - Roteador Doméstico (Testar USB e Controle Parental)");
            System.out.println("2 - Roteador Empresarial (Testar Firewall e Gestão de Tráfego)");
            System.out.println("3 - Roteador Portátil (Testar Operadora e Bateria)");
            System.out.println("4 - Sair do Programa");
            System.out.print("Sua opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do teclado

            if (opcao == 4) {
                System.out.println("\nEncerrando o simulador de rede. Até logo!");
                break;
            }

            if (opcao < 1 || opcao > 4) {
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            // Pergunta a quantidade de equipamentos para a rodada atual
            System.out.print("Quantos equipamentos/hosts você deseja conectar a este roteador por vez? ");
            int qtdEquipamentos = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            if (qtdEquipamentos <= 0) {
                System.out.println("Quantidade inválida! Deve ser pelo menos 1 dispositivo.");
                continue;
            }

            // Array para guardar os dispositivos criados nesta rodada
            Host[] hostsDestaRodada = new Host[qtdEquipamentos];
            for (int i = 0; i < qtdEquipamentos; i++) {
                System.out.print("Digite o nome do dispositivo " + (i + 1) + " (ex: Notebook, Celular): ");
                String nomeHost = scanner.nextLine();
                hostsDestaRodada[i] = new Host(nomeHost); // Instancia a classe Host
            }

            System.out.println("\n--- Iniciando Processamento de Infraestrutura de Rede ---");

            // Executa a lógica baseada na escolha do usuário e testa TODAS as funcionalidades
            switch (opcao) {
                case 1:
                    RoteadorDomestico domestico = new RoteadorDomestico(
                            "TP-Link", "Archer C6", 249.90, "192.168.1.1", "senha123", true, null
                    );
                    System.out.println("\n[CONFIGURAÇÃO] Roteador selecionado: Doméstico " + domestico.getModelo());

                    // Testando métodos exclusivos e de interface
                    domestico.ativarControleParental();
                    domestico.bloquearSite("cassino-online.com");
                    domestico.montarUnidadeUSB(64.0);

                    // Conecta os Hosts
                    for (Host h : hostsDestaRodada) {
                        try {
                            domestico.conectar(h);
                        } catch (ExcecaoAtribuirHOST e) {
                            System.err.println("Erro ao conectar dispositivo: " + e.getMessage());
                        }
                    }
                    domestico.ejetarUnidade();
                    break;

                case 2:
                    RoteadorEmpresarial empresarial = new RoteadorEmpresarial(
                            "Cisco", "Catalyst 9000", 1850.00, "10.0.0.1", "LICENCA-FIREWALL-2026", null
                    );
                    System.out.println("\n[CONFIGURAÇÃO] Roteador selecionado: Empresarial " + empresarial.getModelo());

                    // Testando métodos exclusivos
                    empresarial.bloquearSite("redes-sociais.com");
                    empresarial.atualizarIP("10.0.0.2"); // Força a atualização de IP

                    for (Host h : hostsDestaRodada) {
                        try {
                            empresarial.conectar(h);
                        } catch (ExcecaoAtribuirHOST e) {
                            System.err.println("Erro ao conectar dispositivo: " + e.getMessage());
                        }
                    }
                    // Chama a gestão de tráfego após os hosts estarem nas portas
                    System.out.println("\n[SISTEMA] Acionando o balanceamento de carga da empresa...");
                    empresarial.gerenciarTrafego();
                    break;

                case 3:
                    RoteadorPortatil portatil = new RoteadorPortatil(
                            "Huawei", "Pocket-WiFi 4G", 399.90, "192.168.43.1", 12.0, "Vivo"
                    );
                    System.out.println("\n[CONFIGURAÇÃO] Roteador selecionado: Portátil " + portatil.getModelo());

                    // Testando métodos exclusivos
                    portatil.conectarRedeCelular();
                    portatil.alertarBateriaFraca(); // Vai disparar alerta pois coloquei 12.0 no construtor

                    for (Host h : hostsDestaRodada) {
                        try {
                            portatil.conectar(h);
                        } catch (ExcecaoAtribuirHOST e) {
                            System.err.println("Erro ao conectar dispositivo: " + e.getMessage());
                        }
                    }
                    break;
            }

            // =========================================================================
            // DISPARO DO MEIO FÍSICO: Executa a concorrência (Multithreading)
            // =========================================================================
            System.out.println("\n📶 Dispositivos configurados! Verificando Threads de transmissão concorrente...\n");

            // ATENÇÃO: Como você deixou o "new Thread(this.host[i]).start();" DENTRO dos métodos conectar()
            // e gerenciarTrafego(), as threads já estão rodando!
            // Este bloco abaixo agora serve apenas para fazer um JOIN simbólico na Main
            // caso queira aguardar processos futuros ou tratar falhas graves de IP nulo.

            boolean falhaGeral = false;
            for (int i = 0; i < qtdEquipamentos; i++) {
                if (hostsDestaRodada[i].getIp() == null) {
                    System.err.println("❌ A Thread do equipamento [" + hostsDestaRodada[i].getNome() + "] falhou na rede. IP nulo.");
                    falhaGeral = true;
                }
            }

            if(!falhaGeral) {
                // Apenas dá um tempo para as Threads que já foram disparadas internamente nas classes terminarem seus prints no console
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // =========================================================================
            // RELATÓRIO DO CONTADOR GLOBAL (ESTÁTICO)
            // =========================================================================
            System.out.println("\n-------------------------------------------------------");
            System.out.println("📊 RELATÓRIO DE TRÁFEGO E INFRAESTRUTURA:");
            System.out.println("➡️ Total de dispositivos validados no sistema (geral): " + Roteador.getTotalDispositivosConectados());
            System.out.println("-------------------------------------------------------");
            System.out.println("Simulação da rodada finalizada!");
            System.out.println("-------------------------------------------------------");
        }
        scanner.close();
    }
}