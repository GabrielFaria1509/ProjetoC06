package classes;
import tratamentoexcecoes.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList; // Necessário para usar listas
import java.util.List;

import enums.Protocolo;

import java.util.Arrays;

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
    //Throws é para sinalizar que deve tratar exceção ou quem chama o metodo use try catch
    public String[] lerArquivosIPs() throws ExcecaoLeituraArquivos{
        String caminho = "arquivo.txt";

        try{
            List<String> linhas = Files.readAllLines(Paths.get(caminho));
            return linhas.toArray(new  String[0]);
        } catch (IOException e){
            throw new ExcecaoLeituraArquivos("Falha ao ler o arquivo.txt: " + e.getMessage());
        }
    }

    // 1 e 3: Adicionado o parâmetro (String ipRoteador) e o aviso da exceção (throws)
    public Protocolo verificarProtocoloIP(String ipRoteador) throws ExcecaoLeituraArquivos {

        // 2: Arrays.asList() transforma o vetor em uma lista temporária para usar o .contains()
        if(Arrays.asList(lerArquivosIPs()).contains(ipRoteador)){

            System.out.println("O IP " + ipRoteador + " possui o protocolo TCP");
            return Protocolo.PIG;
        }

        System.out.println("O IP " + ipRoteador + " possui o protocolo UDP");
        return Protocolo.CAT;
    }

    //Criando metodo para criarIP
    public abstract void gerarIP();

    // Criando metodo para atribuir IP
    public abstract void atribuirIP(String ip) throws ExcecaoAtribuirIP;


    // Criando metodo para Bloquear site
    public abstract void bloquearSite(String url);


    public abstract void atualizarIP(String ip);


    public abstract void conectar(Host novohostS) throws ExcecaoAtribuirHOST;//obrigado a tratar exceção

    //
    public abstract void desconectar(Host novohost) throws ExcecaoRemoverHOST; //obrigado a tratar exceção

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
