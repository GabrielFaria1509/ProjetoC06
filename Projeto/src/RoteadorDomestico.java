public class RoteadorDomestico extends Roteador implements CompartilhamentoUSB{
    private String senhaWifi;
    private boolean controleParentalAtivo;

    public RoteadorDomestico(String marca, String modelo, double preco, String gateway, String senhaWifi, boolean controleParentalAtivo){
        super(marca, modelo, preco, gateway);
        this.senhaWifi = senhaWifi;
        this.controleParentalAtivo = controleParentalAtivo;
    }


    public void ativarControleParental(){
        //TO-DO: show this to Lidia, because it still doesn't make sense to me to have a method that only prints something...
        if (controleParentalAtivo) {
            System.out.println("O controle parental foi ativado com sucesso. Agora você terá acesso ao que a sua criança acessar e conseguirá bloquear conteudos indesejados.");
        } else {
            System.out.println("Você optou por não ativar o controle parental. Caso mude de ideia, é só acessar o nosso menu de configurações.");
        }
    }

    //superclass methods
    //TO-DO: The code is NOT as it should be, based on the UML, u must update it to match the code (yep, good luck...)
    @Override
    public void atribuirIP(String ip){}

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
}
