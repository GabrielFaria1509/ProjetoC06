package classes;

import interfaces.CompartilhamentoUSB;
import tratamentoexcecoes.ExcecaoAtribuirHOST;
import tratamentoexcecoes.ExcecaoAtribuirIP;
import tratamentoexcecoes.ExcecaoRemoverHOST;
import tratamentoexcecoes.ExcecaoLeituraArquivos;
import enums.Protocolo;

public class RoteadorPortatil extends Roteador implements CompartilhamentoUSB {

    // Atributos específicos da classe segundo o UML
    private double nivelBateria;
    private String operadora;
    private String ipRoteador; // Armazena o IP atual gerado para o host conectado

    // Construtor herdando os atributos da classe mãe e adicionando os específicos
    public RoteadorPortatil(String marca, String modelo, double preco, String gateway, double nivelBateria, String operadora) {
        super(marca, modelo, preco, gateway, 11);
        this.nivelBateria = nivelBateria;
        this.operadora = operadora;
        this.ipRoteador = null;
    }

    @Override
    public void gerarIP() {
        // Geração de IP dinâmico seguindo o padrão da rede portátil
        this.ipRoteador = "192.168.1." + (ipsAtribuidos.size() + 150);
        System.out.println("IP gerado pelo roteador portátil: " + this.ipRoteador);
    }

    @Override
    public void atribuirIP(String ip) throws ExcecaoAtribuirIP {

        try{
            gerarIP();

            if(ipRoteador == null){
                throw new ExcecaoAtribuirIP("O roteador ainda não tem um IP atribuído");
            }

            ipsAtribuidos.add(ipRoteador);
            System.out.println("O IP: " + ipRoteador + " foi atribuito ao roteador empresarial " + this.getModelo() + " com sucesso!");
            Roteador.totalDispositivosConectados++;

        } catch (ExcecaoAtribuirIP e) {
            System.out.println("Erro ao atribuir IP: " + e.getMessage());
        }
    }

    @Override
    public void bloquearSite(String url) {
        urlsBloqueadas.add(url);
        System.out.println("Acesso negado: O site " + url + " foi adicionado à lista de bloqueio do roteador portátil.");
    }

    @Override
    public void atualizarIP(String ip) {
        this.gateway = ip;
        System.out.println("Gateway do roteador portátil atualizado com sucesso para: " + this.gateway);
    }

    @Override
    public void conectar(Host novoHost) throws ExcecaoAtribuirHOST {
        System.out.println("Solicitando conexão para o dispositivo: " + novoHost.getNome() + "...");

        // 1. Gera o IP dinamicamente para este novo host
        this.gerarIP();

        // 2. Verifica se este IP recém-gerado por acaso já está em uso
        if (ipsAtribuidos.contains(this.ipRoteador)) {
            throw new ExcecaoAtribuirHOST("Erro! O IP " + this.ipRoteador + " já está conectado na rede portátil!");
        }

        // 3. Efetiva a atribuição do IP nas listas de controle do roteador
        this.atribuirIP(this.ipRoteador);

        // 4. Procura uma vaga no vetor físico de conexões (agregação de Hosts)
        boolean inseridoComSucesso = false;
        for (int i = 0; i < this.host.length; i++) {
            if (this.host[i] == null) {
                this.host[i] = novoHost; // Associa o host ao roteador

                try {
                    // 5. VALIDAÇÃO DE REDE: Lê o arquivo.txt usando o método da classe mãe
                    Protocolo protocoloValidado = verificarProtocoloIP(this.ipRoteador);

                    // 6. FÍSICA DA REDE: Roteador portátil é instável por padrão (Modo CAT = 180ms)
                    // Se o arquivo validar como PIG, ele opera em 32ms estáveis, caso contrário cai para 180ms
                    int pingDefinido = (protocoloValidado == Protocolo.PIG) ? 32 : 180;

                    // 7. INJEÇÃO DE DADOS: Configura as variáveis internas do Host com as decisões do Roteador
                    this.host[i].configurarConexao(this.ipRoteador, protocoloValidado.name(), pingDefinido);

                } catch (ExcecaoLeituraArquivos e) {
                    throw new ExcecaoAtribuirHOST("Falha na leitura de dados para configurar o Host: " + e.getMessage());
                }

                System.out.println("✅ Dispositivo " + novoHost.getNome() + " conectado com sucesso ao " + this.getModelo() + "!");
                new Thread(this.host[i]).start();
                inseridoComSucesso = true;
                break;
            }
        }

        if (!inseridoComSucesso) {
            throw new ExcecaoAtribuirHOST("Erro: Capacidade máxima de conexões do roteador portátil atingida.");
        }
    }

    @Override
    public void desconectar(Host hostRemovido) throws ExcecaoRemoverHOST {
        for (int i = 0; i < this.host.length; i++) {
            if (this.host[i] != null && this.host[i].getIp().equals(hostRemovido.getIp())) {

                // Remove o IP da lista de controle
                ipsAtribuidos.remove(this.host[i].getIp());
                // Remove o objeto do vetor de agregação
                this.host[i] = null;
                // Decrementa o contador global da classe mãe
                Roteador.totalDispositivosConectados--;

                System.out.println("📴 Dispositivo " + hostRemovido.getNome() + " desconectado com sucesso da rede portátil.");
                return;
            }
        }
        throw new ExcecaoRemoverHOST("Erro! O dispositivo " + hostRemovido.getNome() + " não está conectado a este roteador portátil.");
    }

    // Métodos específicos do RoteadorPortatil
    public boolean alertarBateriaFraca() {
        if (this.nivelBateria < 15.0) {
            System.out.println("Alerta: Bateria fraca (" + this.nivelBateria + "%)!");
            return true;
        }
        return false;
    }

    public boolean conectarRedeCelular() {
        System.out.println("Conectando à rede celular da operadora " + this.operadora + "...");
        return true;
    }

    //interface methods
    @Override
    public void montarUnidadeUSB(double capacidade) {
        System.out.println("Unidade USB montada com capacidade de " + capacidade + " GB");
    }

    @Override
    public boolean ejetarUnidade() {
        System.out.println("Unidade USB ejetada com sucesso");
        return true;
    }


    // --- Getters e Setters ---
    public double getNivelBateria() {
        return nivelBateria;
    }

    public void setNivelBateria(double nivelBateria) {
        this.nivelBateria = nivelBateria;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }
}