package classes;

import tratamentoexcecoes.ExcecaoAtribuirHOST;
import tratamentoexcecoes.ExcecaoRemoverHOST;

public class RoteadorPortatil extends Roteador {

    // Atributos específicos da classe segundo o UML
    private double nivelBateria;
    private String operadora;

    // Construtor herdando os atributos da classe mãe e adicionando específicos
    public RoteadorPortatil(String marca, String modelo, double preco, String gateway, double nivelBateria, String operadora) {
        super(marca, modelo, preco, gateway);
        this.nivelBateria = nivelBateria;
        this.operadora = operadora;
    }



    @Override
    public void gerarIP() {
        // Exemplo simples de geração de IP dinâmico
        String novoIp = "192.168.1." + (ipsAtribuidos.size() + 2);
        System.out.println("IP gerado pelo roteador portátil: " + novoIp);
    }

    @Override
    public void atribuirIP(String ip) throws ExcecaoAtribuirHOST {
        // Validação opcional para disparar a exceção se o IP for inválido ou nulo
        if (ip == null || ip.isEmpty()) {
            throw new ExcecaoAtribuirHOST("Falha ao atribuir: IP inválido.");
        }

        ipsAtribuidos.add(ip);
        System.out.println("O roteador portátil " + this.getModelo() + " atribuiu o IP: " + ip);

        // Incrementa o contador estático global de dispositivos conectados
        Roteador.totalDispositivosConectados++;
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
    public void conectar(String ip) throws ExcecaoAtribuirHOST {
        // Verifica se o IP já está na lista de atribuídos
        if (ipsAtribuidos.contains(ip)) {
            throw new ExcecaoAtribuirHOST("Erro! O dispositivo com IP " + ip + " já está conectado!");
        }
        System.out.println("Conectando dispositivo...");
        this.atribuirIP(ip); // Pode lançar ExcecaoAtribuirIP
        System.out.println("Conectado com sucesso!");

    }


    @Override
    public void desconectar(String ip) throws ExcecaoRemoverHOST {
            if (ipsAtribuidos.contains(ip)) {
                ipsAtribuidos.remove(ip);
                Roteador.totalDispositivosConectados--;
                System.out.println("Dispositivo com IP " + ip + " desconectado com sucesso.");
            } else {
                throw new ExcecaoRemoverHOST("Erro! O IP " + ip + " não foi encontrado na rede.");
            }

    }

    // Métodos do RoteadorPortatil

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
