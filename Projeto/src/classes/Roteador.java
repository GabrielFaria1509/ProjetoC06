package classes;
import tratamentoexcecoes.ExcecaoAtribuirIP;
import tratamentoexcecoes.ExcecaoLeituraArquivos;
import tratamentoexcecoes.ExcecaoRemoverIP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList; // Necessário para usar listas
import java.util.List;

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
    //Agregação com Casa.classes.Host
    Host[]host = new Host[100]; //roteador pode ou não ter vários hosts/dispositivos conectados

    //válido para cada instância que surge a partir da classes herdadas de roteador

    // Lista para guardar os IPs atribuídos
    protected ArrayList<String> ipsAtribuidos = new ArrayList<>();

    // Lista para guardar os Urls bloqueados
    protected ArrayList<String> urlsBloqueadas = new ArrayList<>();

    // Criando o construtor Casa.classes.Roteador(junto com wi fi)
    public Roteador(String marca, String modelo, double preco, String gateway) {
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.gateway = gateway;
        this.wifi = new WIFI();
    }

    //Criando metodo para ler arquivo.txt e devolver um vetor com cada IP
    public String[] lerArquivosIPs() throws ExcecaoLeituraArquivos{
        String caminho = "arquivo.txt";

        try{
            List<String> linhas = Files.readAllLines(Paths.get(caminho));
            return linhas.toArray(new  String[0]);
        } catch (IOException e){
            throw new ExcecaoLeituraArquivos("Falha ao ler o arquivo.txt: " + e.getMessage());
        }
    }

    //Criando metodo para criarIP
    public abstract void gerarIP();

    // Criando metodo para atribuir IP
    public abstract void atribuirIP(String ip) throws ExcecaoAtribuirIP;


    // Criando metodo para Bloquear site
    public abstract void bloquearSite(String url);


    public abstract void atualizarIP(String ip);


    public abstract void conectar(String ip) throws ExcecaoAtribuirIP;//obrigado a tratar exceção

    // Criando metodo para desconectar um IP da rede
    public void desconectar(String ip) {
        // 1. Verifica se o IP realmente está conectado na lista atual
        try {
            if (ipsAtribuidos.contains(ip)) {
                ipsAtribuidos.remove(ip);
                totalDispositivosConectados--;
            }
        }catch (ExcecaoRemoverIP e){
            System.out.println("Erro ao desconectar,IP inexistente" + e.getMessage());
        }

    }

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
