public class RoteadorPortatil extends Roteador {

    // Atributos específicos da classe segundo o UML
    private double nivelBateria;
    private String operadora;

    // Construtor herdando os atributos da classe mãe e adicionando os específicos
    public RoteadorPortatil(String marca, String modelo, double preco, String gateway, double nivelBateria, String operadora) {
        super(marca, modelo, preco, gateway);
        this.nivelBateria = nivelBateria;
        this.operadora = operadora;
    }

    // --- Implementação dos Métodos Abstratos da Classe Mãe ---

    @Override
    public void atribuirIP(String ip) {
        ipsAtribuidos.add(ip);
        System.out.println("O roteador portátil " + this.getModelo() + " atribuiu o IP: " + ip);

        // Incrementa o contador estático global de dispositivos conectados
        totalDispositivosConectados++;
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
    public void conectar(String ip) throws ExcecaoAtribuirIP {
        // Verifica se o IP já está na lista de atribuídos
        if (ipsAtribuidos.contains(ip)) {
            throw new ExcecaoAtribuirIP("Erro! O dispositivo já está conectado!");
        } else {
            System.out.println("Conectando dispositivo...");
            this.atribuirIP(ip); // Chama o método para adicionar e contabilizar
            System.out.println("Conectado com sucesso!");
        }
    }

    // --- Métodos Específicos do RoteadorPortatil (Segundo o UML) ---

    public boolean alertarBateriaFraca() {
        // Exemplo de lógica: se a bateria estiver abaixo de 15%, dispara o alerta
        if (this.nivelBateria < 15.0) {
            System.out.println("Alerta: Bateria fraca (" + this.nivelBateria + "%)!");
            return true;
        }
        return false;
    }

    public boolean conectarRedeCelular() {
        System.out.println("Conectando à rede celular da operadora " + this.operadora + "...");
        // Simulação de conexão bem-sucedida
        return true;
    }

    // --- Getters e Setters para os atributos específicos (Opcional, mas boa prática) ---

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

