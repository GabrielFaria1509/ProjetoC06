package classes;

import enumns.Protocolo;
import interfaces.CompartilhamentoUSB;
import tratamentoexcecoes.ExcecaoAtribuirHOST;

import java.util.Random;


public class RoteadorDomestico extends Roteador implements CompartilhamentoUSB {
    private String senhaWifi;
    private boolean controleParentalAtivo;
    private String ipRoteador;

    public RoteadorDomestico(String marca, String modelo, double preco, String gateway, String senhaWifi, boolean controleParentalAtivo, String ipRoteador) {
        super(marca, modelo, preco, gateway);
        this.senhaWifi = senhaWifi;
        this.controleParentalAtivo = controleParentalAtivo;
        this.ipRoteador = null;
    }

    public void ativarControleParental(){
        //TODO: show this to Lidia, because it still doesn't make sense to me to have a method that only prints something...
        if (controleParentalAtivo) {
            System.out.println("O controle parental foi ativado com sucesso. Agora você terá acesso ao que a sua criança acessar e conseguirá bloquear conteudos indesejados.");
        } else {
            System.out.println("Você optou por não ativar o controle parental. Caso mude de ideia, é só acessar o nosso menu de configurações.");
        }
    }



    //TODO: the ip related methods should be abstracts methods in the superclass (aparently, I will have as many "to-dos" as lines of code... help), I will only erase this todo when we finish this corrections
    @Override
    public void gerarIP(){
        Random random = new Random();
        int tentativas = 0;

        while (tentativas < 50){
            int suffix = random.nextInt(253)+2;
            String ipTemporario = "192.168.1." + suffix; 

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

    public Protocolo verificarProtocoloIP(){
        if(lerArquivosIPs().contains(ipRoteador)){
            System.out.println("O IP " + ipRoteador + " possui o protocolo TCP"); //I REFUSE to use a todo again... This method is the one that returns the protocol, but the rest of the code uses "cat" and "pig", this needs to change!);
            return Protocolo.PIG;
        }

        System.out.println("O IP " + ipRoteador + " possui o protocolo UDP");
        return Protocolo.CAT;
    }

    //superclass methods
    //TODO: The code is NOT as it should be, based on the UML, u must update it to match the code (yep, good luck...)
    //TODO: implement the exeptions handling on classesfilhas.RoteadorPortatil
    @Override
    public void atribuirIP(String ip) throws ExcecaoAtribuirIP{
        try{
            gerarIP();

            if(ipRoteador == null){
                throw new ExcecaoAtribuirHOST("O roteador ainda não tem um IP atribuído");
            }

            ipsAtribuidos.add(ipRoteador);
            System.out.println("O IP: " + ipRoteador + " foi atribuito ao roteador doméstico " + this.getModelo() + " com sucesso!");
            Roteador.totalDispositivosConectados++;

        } catch (ExcecaoAtribuirHOST e) {
            System.out.println("Erro ao atribuir IP: " + e.getMessage());
        }
    }

    @Override
    public void bloquearSite(String url){
        urlsBloqueadas.add(url);
        System.out.println("O site " + url + " foi bloqueado com sucesso");
        //TODO: add some integration with the controlePArental later
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

    //The classes.Roteador expects that this will be used for checking if the ip is already assigned, but it would make more sense to be a way to connect to the wifi, otherwise. wifi would never really be used.
    //TODO: talk about with the group AND point out that it would be cooler to shown the Wifi velocity when connecting
    @Override
    public void conectar(String ip) throws ExcecaoAtribuirHOST {//The AtribuirIP method alredy handle this, and it makes more sense to be there, this will also need to change, the name is not ok
        if(senhaWifi == null || senhaWifi.isEmpty()){
            throw new ExcecaoAtribuirHOST("A senha não foi configurada");
        }
        System.out.println("Conectado com sucesso ao roteador " + this.getModelo());
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
