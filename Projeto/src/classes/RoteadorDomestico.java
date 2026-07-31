package classes;

import enums.Protocolo;
import interfaces.CompartilhamentoUSB;
import tratamentoexcecoes.ExcecaoAtribuirHOST;
import tratamentoexcecoes.ExcecaoAtribuirIP;
import tratamentoexcecoes.ExcecaoLeituraArquivos;
import tratamentoexcecoes.ExcecaoRemoverHOST;

import java.util.Random;

public class RoteadorDomestico extends Roteador implements CompartilhamentoUSB {
    private String senhaWifi;
    private boolean controleParentalAtivo;
    private String ipRoteador;

    public RoteadorDomestico(String marca, String modelo, double preco, String gateway, String senhaWifi, boolean controleParentalAtivo, String ipRoteador) {
        super(marca, modelo, preco, gateway,300);
        this.senhaWifi = senhaWifi;
        this.controleParentalAtivo = controleParentalAtivo;
        this.ipRoteador = null;
    }

    public void ativarControleParental(){
        if (controleParentalAtivo) {
            System.out.println("O controle parental foi ativado com sucesso. Agora você terá acesso ao que a sua criança acessar e conseguirá bloquear conteudos indesejados.");
        } else {
            System.out.println("Você optou por não ativar o controle parental. Caso mude de ideia, é só acessar o nosso menu de configurações.");
        }
    }

    @Override
    public void gerarIP(){
        Random random = new Random();
        int tentativas = 0;

        while (tentativas < 50){
            int suffix = random.nextInt(253)+2;
            String ipTemporario = "192.168.1." + suffix; 

            if(!ipTemporario.equals(this.gateway) && !ipsAtribuidos.contains(ipTemporario)){
                this.ipRoteador = ipTemporario;
                System.out.println("o IP " + ipRoteador + " foi atribuído ao roteador com sucesso!");
                return;
            }else if(ipTemporario.equals(this.gateway)){
                System.out.println("O IP " + ipTemporario + " não pode ser atribuído ao roteador, pois é o mesmo do gateway. Tentando outro IP...");

            }else if(ipsAtribuidos.contains(ipTemporario)){
                System.out.println("O IP " + ipTemporario + " já está atribuído a outro dispositivo. Tentando outro IP...");
            }

            tentativas++;
        }
    }

    //superclass methods
    @Override
    public void atribuirIP(String ip) throws ExcecaoAtribuirIP{

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
    public void bloquearSite(String url){
        urlsBloqueadas.add(url);
        System.out.println("O site " + url + " foi bloqueado com sucesso");
    }

    @Override
    public void atualizarIP(String ip){
        String ipAnterior = ipRoteador;
        gerarIP();

        if(ipRoteador == null){
            throw new IllegalStateException("Não foi possível gerar o novo IP");
        }

        ipsAtribuidos.remove(ipAnterior);
        ipsAtribuidos.add(ipRoteador);
        System.out.println("IP atualizado com sucesso! O novo IP do roteador é: " + ipRoteador);
    }

    @Override
    public void conectar(Host novoHost) throws ExcecaoAtribuirHOST {
        try{
            this.atribuirIP(null);
        }catch(ExcecaoAtribuirIP e){
            throw new ExcecaoAtribuirHOST(e.getMessage());
        }

        boolean hostConectado = false;

        for (int i = 0; i < this.host.length; i++){
            if(this.host[i] == null){
                this.host[i] = novoHost;

                try {
                    Protocolo protocoloValidado = verificarProtocoloIP(this.ipRoteador);
                    int pingDefinido = (protocoloValidado == Protocolo.PIG) ? 32 : 100;
                    this.host[i].configurarConexao(this.ipRoteador, protocoloValidado.name(), pingDefinido);
                    
                } catch (ExcecaoLeituraArquivos e) {
                    throw new ExcecaoAtribuirHOST("Falha ao configurar a rede no Host: " + e.getMessage());
                }

                System.out.println("Host " + novoHost.getNome() + " conectado com sucesso ao roteador" + this.getModelo());
                new Thread(this.host[i]).start();
                hostConectado = true;
                break;
            }
        }

        if(!hostConectado){
            throw new ExcecaoAtribuirHOST("Erro: não há portas disponíveis para conectar o host");
        }
    }

    @Override
    public void desconectar(Host hostRemovido) throws ExcecaoRemoverHOST {
        for (int i = 0; i < this.host.length; i++){
            if(this.host[i] == hostRemovido){

                ipsAtribuidos.remove(this.host[i].getIp());
                this.host[i] = null;

                System.out.println("Host " + hostRemovido.getNome() + " desconectado com sucesso.");
                return;
            
            }
        }
        throw new ExcecaoRemoverHOST("O host " + hostRemovido.getNome() + " não está conectado a este roteador domestico.");
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

    //getters and setters
    public boolean isControleParentalAtivo() {
        return controleParentalAtivo;
    }

    public void setControleParentalAtivo(boolean controleParentalAtivo) {
        this.controleParentalAtivo = controleParentalAtivo;
    }

    public void setSenhaWifi(String senhaWifi) {
        this.senhaWifi = senhaWifi;
    }

    public String getSenhaWifi() {
        return senhaWifi;
    }
}
