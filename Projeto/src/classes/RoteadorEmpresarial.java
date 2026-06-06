package classes;

import java.util.Random;

import enums.Protocolo;
import interfaces.CompartilhamentoUSB;
import tratamentoexcecoes.ExcecaoAtribuirHOST;
import tratamentoexcecoes.ExcecaoLeituraArquivos;
import tratamentoexcecoes.ExcecaoRemoverHOST;
import tratamentoexcecoes.ExcecaoAtribuirIP;

public class RoteadorEmpresarial extends Roteador implements CompartilhamentoUSB {
    private String licencaFirewall;
    private String ipRoteador;

    public RoteadorEmpresarial(String marca, String modelo, double preco, String gateway, String licencaFirewall, String ipRoteador) {
        super(marca, modelo, preco, gateway);
        this.licencaFirewall = licencaFirewall;
        this.ipRoteador = null;
    }

    //superclass methods
    @Override
    public void gerarIP(){
        Random random = new Random();
        int tentativas = 0;

        while (tentativas < 50){
            int suffix = random.nextInt(253)+2;
            String ipTemporario = "10.0.0." + suffix; 

            if(ipTemporario != this.gateway && !ipsAtribuidos.contains(ipTemporario)){
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

    @Override
    public void atribuirIP(String ip) throws ExcecaoAtribuirIP{
        try{
            if (licencaFirewall == null || licencaFirewall.trim().isEmpty()) {
                throw new ExcecaoAtribuirIP("O roteador empresarial não possui uma licença de firewall válida.");
            }

            gerarIP();

            if(ipRoteador == null){//I believe that this should be an "IlleagalStateException"...
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
        System.out.println("O site " + url + " foi bloqueado com sucesso na rede empresarial");
    }

    @Override
    public void atualizarIP(String ip){
        if (this.licencaFirewall == null || this.licencaFirewall.trim().isEmpty()) {
            throw new IllegalStateException("O roteador empresarial não possui uma licença de firewall válida. Não é possível atualizar o IP.");
        }

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
        if (this.licencaFirewall == null || this.licencaFirewall.trim().isEmpty()) {
            throw new IllegalStateException("O roteador empresarial não possui uma licença de firewall válida. Não é possível conectar o host.");
        }

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
                Roteador.totalDispositivosConectados--;

                System.out.println("Host " + hostRemovido.getNome() + " desconectado com sucesso.");
                return;
            
            }
        }
        throw new ExcecaoRemoverHOST("O host " + hostRemovido.getNome() + " não está conectado a este roteador.");
    }

    //unique method
    public void gerenciarTrafego() {

        int hostsAtivos = 0;

        for (int i = 0; i < this.host.length; i++) {
            if (this.host[i] != null) {
                hostsAtivos++;
            }
        }
        double cargaAtual = ((double) hostsAtivos / this.host.length) * 100.0;

        System.out.println("Gerenciando tráfego. Carga atual calculada: " + cargaAtual + "% (" + hostsAtivos + " instâncias ativas)");
        
        for (int i = 0; i < this.host.length; i++) {
            if (this.host[i] != null) {
                if (cargaAtual > 80.0) {
                    this.host[i].configurarConexao(this.host[i].getIp(), Protocolo.CAT.name(), 180);
                } else {
                    this.host[i].configurarConexao(this.host[i].getIp(), Protocolo.PIG.name(), 32);
                }
                new Thread(this.host[i]).start();
            }
        }
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
    public String getLicencaFirewall() {
        return licencaFirewall;
    }

    public void setLicencaFirewall(String licencaFirewall) {
        this.licencaFirewall = licencaFirewall;
    }
}
