package roteador;

import java.util.ArrayList; // Necessário para usar listas

public abstract class Roteador {
    // Criando os atributos protegidos
    protected String marca;
    protected String modelo;
    protected String gateway;
    protected double preco;

    // Lista para guardar os IPs atribuídos
    protected ArrayList<String> ipsAtribuidos = new ArrayList<>();

    // Lista para guardar os Urls bloqueados
    protected ArrayList<String> urlsBloqueadas = new ArrayList<>();

    // Criando o construtor Roteador
    public Roteador(String marca, String modelo, double preco, String gateway) {
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.gateway = gateway;
    }

    // Criando metodo para atribuir IP
    public void atribuirIP(String ip) {
        ipsAtribuidos.add(ip); // chamando o array list para adicionar novos ips atribuídos
        System.out.println("O roteador " + this.modelo + " atribuiu o IP: " + ip);
    }

    // Criando metodo para Bloquear site
    public void bloquearSite(String url){
        urlsBloqueadas.add(url);
        System.out.println("Acesso negado: O site " + url + " foi adicionado à lista de bloqueio.");
    }

    // Criando metodo para Alterar IP
    public void atualizarIP(String ip){
        this.gateway = ip;
        System.out.println("Gateway atualizado com sucesso, para: " + this.gateway);
    }

    // fazer try catch dps
    public void conectar(String ip){
        if (ipsAtribuidos.contains(ip)) { // Se na lista de ips atribuidos conter o ip, mostra o erro
            System.out.println("Erro: O dispositivo com IP " + ip + " já está conectado ao " + this.modelo);
        } else {
            // 2. Se não estiver, chamamos o metodo de atribuir o IP
            System.out.println("Iniciando protocolo de conexão para o IP: " + ip);
            atribuirIP(ip); // Passa o papel de atribuir o ip para o metodo "atribuir ip" e passa apenas o ip de parâmetro: uma função ajudando outra
            System.out.println("Conexão estabelecida com sucesso!");
        }
    }

    public int dispositivosConectados() {
        // Retorna o tamanho da lista diretamente, sem criar variáveis intermediárias
        return ipsAtribuidos.size();
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
