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
            System.out.println("1 - Roteador Doméstico (Velocidade: 300 Mbps)");
            System.out.println("2 - Roteador Empresarial (Velocidade: 300 Mbps)");
            System.out.println("3 - Roteador Portátil (Velocidade: 11 Mbps)");
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

            // Executa a lógica baseada na escolha do usuário
            switch (opcao) {
                case 1:
                    // Instancia o doméstico passando a velocidade de 300.0 Mbps
                    RoteadorDomestico domestico = new RoteadorDomestico(
                            "TP-Link", "Archer C6", 249.90, "192.168.1.1", "senha123", false, null
                    );
                    System.out.println("Roteador selecionado: Doméstico " + domestico.getModelo());
                    // Conecta e injeta as configurações em cada Host
                    for (Host h : hostsDestaRodada) {
                        try {
                            domestico.conectar(h);
                        } catch (ExcecaoAtribuirHOST e) {
                            System.out.println("Erro ao conectar dispositivo: " + e.getMessage());
                        }
                    }
                    break;

                case 2:
                    // Instancia o empresarial passando a velocidade de 300.0 Mbps
                    RoteadorEmpresarial empresarial = new RoteadorEmpresarial(
                            "Cisco", "Catalyst 9000", 1850.00, "10.0.0.1", "LICENCA-FIREWALL-2026", null
                    );
                    System.out.println("Roteador selecionado: Empresarial " + empresarial.getModelo());
                    for (Host h : hostsDestaRodada) {
                        try {
                            empresarial.conectar(h);
                        } catch (ExcecaoAtribuirHOST e) {
                            System.out.println("Erro ao conectar dispositivo: " + e.getMessage());
                        }
                    }
                    break;

                case 3:
                    // Instancia o portátil passando os atributos específicos e a velocidade de 11.0 Mbps
                    RoteadorPortatil portatil = new RoteadorPortatil(
                            "Huawei", "Pocket-WiFi 4G", 399.90, "192.168.43.1", 85.0, "Vivo"
                    );
                    System.out.println("Roteador selecionado: Portátil " + portatil.getModelo());
                    portatil.conectarRedeCelular(); // Metodo específico do portátil
                    portatil.alertarBateriaFraca(); // Metodo específico do portátil
                    for (Host h : hostsDestaRodada) {
                        try {
                            portatil.conectar(h);
                        } catch (ExcecaoAtribuirHOST e) {
                            System.out.println("Erro ao conectar dispositivo: " + e.getMessage());
                        }
                    }
                    break;
            }

            // =========================================================================
            // DISPARO DO MEIO FÍSICO: Executa a concorrência (Multithreading)
            // =========================================================================
            System.out.println("\n📶 Dispositivos configurados! Ativando as Threads de transmissão concorrente...\n");
            Thread[] threads = new Thread[qtdEquipamentos];

            // 1. Cria e inicia todas as Threads ao mesmo tempo para rodarem juntas
            for (int i = 0; i < qtdEquipamentos; i++) {
                // SÓ DISPARA A THREAD SE A CONEXÃO DEU CERTO E O IP NÃO É NULO
                if (hostsDestaRodada[i].getIp() != null) {
                    threads[i] = new Thread(hostsDestaRodada[i]); // Host implementa Runnable
                    threads[i].start(); // Dispara o método run() do Host em paralelo
                } else {
                    System.out.println("❌ A Thread do equipamento [" + hostsDestaRodada[i].getNome() + "] foi cancelada devido à falha de configuração de rede.");
                }
            }

            // 2. Garante que o menu principal espere as transmissões terminarem
            for (int i = 0; i < qtdEquipamentos; i++) {
                // SÓ ESPERA (JOIN) SE A THREAD REALMENTE TIVER SIDO CRIADA
                if (threads[i] != null) {
                    try {
                        threads[i].join();
                    } catch (InterruptedException e) {
                        System.out.println("Erro ao sincronizar a transmissão dos dados.");
                    }
                }
            }

            // =========================================================================
            // RELATÓRIO DO CONTADOR GLOBAL (ESTÁTICO)
            // =========================================================================
            System.out.println("\n-------------------------------------------------------");
            System.out.println("📊 RELATÓRIO DE TRÁFEGO E INFRAESTRUTURA:");
            System.out.println("➡️ Total de dispositivos conectados no sistema (geral): " + Roteador.getTotalDispositivosConectados());
            System.out.println("-------------------------------------------------------");
            System.out.println("Simulação da rodada finalizada com sucesso!");
            System.out.println("-------------------------------------------------------");
        }
        scanner.close();
    }
}