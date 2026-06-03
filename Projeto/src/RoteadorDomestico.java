import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RoteadorDomestico extends Roteador implements CompartilhamentoUSB{
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

    //TODO: Pass this to the superclass, it's the same for every router
    private List<String>lerArquivosIPs(){
        try{
            return Files.readAllLines(Paths.get("Projeto/arquivo.txt"));
        }catch(IOException e){
            System.out.println("Erro ao ler lista de ips: " + e.getMessage());
            return List.of();
        }
    }

    //TODO: the ip related methods should be abstracts methods in the superclass (aparently, I will have as many "to-dos" as lines of code... help)
    private void gerarIP(){
        Random random = new Random();
        int tentativas = 0;

        while (tentativas < 50){
            int ipHost = random.nextInt(253)+2;
            String ipTemporario = "192.168.1." + ipHost; 

            //TODO: see if it would be more apropriate to use try catch here
            if(ipTemporario != this.gateway && !ipsAtribuidos.contains(ipTemporario)){
                this.ipRoteador = ipTemporario;
                System.out.println("o IP " + ipRoteador + " foi atribuído ao roteador com sucesso!");
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
        return Protocolo.CAT;
    }

    //superclass methods
    //TODO: The code is NOT as it should be, based on the UML, u must update it to match the code (yep, good luck...)
    @Override
    public void atribuirIP(String ip){

    }

    @Override
    public void bloquearSite(String url){}

    @Override
    public void atualizarIP(String ip){}

    @Override
    public void conectar(String ip) throws ExcecaoAtribuirIP{}

    //interface methods
    @Override
    public void montarUnidadeUSB(double capacidade) {}

    @Override
    public boolean ejetarUnidade() {
        return false;
    }

    //getters and setters
    public boolean isControleParentalAtivo() {
        return controleParentalAtivo;
    }

    public void setControleParentalAtivo(boolean controleParentalAtivo) {
        this.controleParentalAtivo = controleParentalAtivo;
    }

    public String getIpRoteador() {
        return ipRoteador;
    }
}
