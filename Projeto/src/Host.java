package Casa;

// IMPORTANTE: Implementar Runnable para o Host poder rodar como Thread
public class Host implements Runnable {

    // Atributos originais da sua UML
    protected String ip;
    protected String nome;

    // Atributos novos que vão estar dentro das subclasses roteadores
    private String protocolo; // Vai receber "PIG" ou "CAT"
    private int pingMs;        // Vai receber 32 ou 180

    // Construtor padrão
    public Host(String nome) {
        this.nome = nome;
        this.ip = null; // O IP começa nulo até o roteador atribuir um
    }

    // Método para o roteador passar as configurações após ler o arquivo .txt
    public void configurarConexao(String ip, String protocolo, int pingMs) {
        this.ip = ip;
        this.protocolo = protocolo;
        this.pingMs = pingMs;
    }

    // O MÉTODO DA THREAD: É aqui que a simulação física do seu desenho acontece!
    @Override
    public void run() {
        System.out.println("\n🚀 [CONEXÃO] " + this.nome + " conectado no IP: " + this.ip);
        System.out.println("📦 Iniciando transmissão de dados via protocolo: " + this.protocolo + "...");

        // CENÁRIO 1: Se o roteador carimbou como PIG (TCP - Conhecido/Seguro)
        if ("PIG".equals(this.protocolo)) {
            System.out.println("🐷 [MODO PIG] Conexão estável. Enviando pacotes ordenados:");

            for (int i = 1; i <= 4; i++) {
                try {
                    // Simula o tempo de resposta físico (Física 2: propagação estável de 32ms)
                    Thread.sleep(this.pingMs);
                    System.out.println("   🟢 [TCP] Pacote [" + i + "] enviado com sucesso! (Ping: " + this.pingMs + "ms)");
                } catch (InterruptedException e) {
                    System.out.println("Erro na transmissão da Thread PIG.");
                }
            }
            System.out.println("✅ [SUCESSO] Todos os dados chegaram intactos: [1][2][3][4]");

            // CENÁRIO 2: Se o roteador carimbou como CAT (UDP - Desconhecido/Instável)
        } else if ("CAT".equals(this.protocolo)) {
            System.out.println("🐱 [MODO CAT] Conexão instável! Risco de perda de pacotes:");

            // Simulação idêntica ao seu desenho do quadro:
            // Pacote 1 vai, Pacote 2 se perde (XX), Pacote 3 e 4 vão, mas chegam bagunçados
            for (int i = 1; i <= 4; i++) {
                try {
                    // Simula o tempo de resposta físico lento (180ms)
                    Thread.sleep(this.pingMs);

                    if (i == 2) {
                        // Simula a perda do pacote 2 (o XX do seu quadro)
                        System.out.println("   ❌ [UDP] Pacote [" + i + "] SE PERDEU NO CAMINHO! (XX) - (Ping: " + this.pingMs + "ms)");
                    } else {
                        System.out.println("   🟡 [UDP] Pacote [" + i + "] enviado. (Ping: " + this.pingMs + "ms)");
                    }
                } catch (InterruptedException e) {
                    System.out.println("Erro na transmissão da Thread CAT.");
                }
            }
            System.out.println("⚠️ [FINALIZADO] Transmissão encerrada com perdas. Conteúdo recebido: [4][1][3]");
        }
    }

    // Getters e Setters caso precise acessar de fora
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getProtocolo() { return protocolo; }
    public int getPingMs() { return pingMs; }
}