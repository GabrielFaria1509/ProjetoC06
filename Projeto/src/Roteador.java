import java.util.ArrayList; // Necessário para usar listas
//banana
public abstract class Roteador {
    // Criando os atributos protegidos
    protected String marca;
    protected String modelo;
    protected String gateway;
    protected double preco;
    protected static int totalDispositivosConectados = 0;  //inicia zero
    //Composição com Wifi
    protected WIFI wifi;
    //Agregação com Host
    Host[]host = new Host[100]; //roteador pode ou não ter vários hosts/dispositivos conectados

    //válido para cada instância que surge a partir da classes herdadas de roteador

    // Lista para guardar os IPs atribuídos
    protected ArrayList<String> ipsAtribuidos = new ArrayList<>();

    // Lista para guardar os Urls bloqueados
    protected ArrayList<String> urlsBloqueadas = new ArrayList<>();

    // Criando o construtor Roteador(junto com wi fi)
    public Roteador(String marca, String modelo, double preco, String gateway) {
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.gateway = gateway;
        this.wifi = new WIFI();
    }

    // Criando metodo para atribuir IP
    public abstract void atribuirIP(String ip);
    //IMPLEMTNAR NA FILHA ASSIM
     //ipsAtribuidos.add(ip); // chamando o array list para adicionar novos ips atribuídos
        //System.out.println("O roteador " + this.modelo + " atribuiu o IP: " + ip);

    //fazendo a conta do total de dispositivos conectados
    //totalDispositivosConectados++;

    // Criando metodo para Bloquear site
    public abstract void bloquearSite(String url);
    //Implementar na filha
     //urlsBloqueadas.add(url);
    //System.out.println("Acesso negado: O site " + url + " foi adicionado à lista de bloqueio.");

    // Criando metodo para Alterar IP
    public abstract void atualizarIP(String ip);
    //IMPLEMENTAR NA FILHA
    //this.gateway = ip;
    //System.out.println("Gateway atualizado com sucesso, para: " + this.gateway);

    public abstract void conectar(String ip) throws ExcecaoAtribuirIP;//obrigado a tratar exceção

    //IMPLEMENTAR NA FILHA
    //verifico se o IP está atribuido
        //if(ipsAtribuidos.contains(ip)){
        //throw new ExcecaoAtribuirIP("Erro ! O dispositivo já está conectado!");
    //}else {
        //System.out.println("Conectado com sucesso!");
        //this.atribuirIP(ip);
    //}


    //Pertence a classe geral
    //Cada dipositivo que um roteador tipo x conectar vai conta
    public static int getTotalDispositivosConectados(){
        return totalDispositivosConectados;
    }

    
    // Getter para exibir marca
    public String getMarca() {
        return this.marca;
    }

    // Getter para exibir modelo
    public String getModelo() {
        return this.modelo;
    }

    // Getter para exibir preço
    public double getPreco() {
        return this.preco;
    }

    // Getter para exibir gateway
    public String getGateway() {
        return this.gateway;
    }
}
