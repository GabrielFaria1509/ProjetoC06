package Casa;

public class Main {
    public static void main(String[] args) {
        System.out.println("====== INICIANDO SIMULAÇÃO COM ROTEADOR REAL ======\n");

        // 1. Instanciando o Roteador Portátil (com dados fictícios para o construtor)
        RoteadorPortatil meuPortatil = new RoteadorPortatil(
                "Huawei", "Pocket-WiFi", 399.90, "192.168.1.1", 12.5, "Vivo"
        );

        // Testando os métodos específicos do seu roteador portátil
        meuPortatil.conectarRedeCelular();
        meuPortatil.alertarBateriaFraca(); // Vai alertar pq passei 12.5% no construtor
        System.out.println();

        // 2. Criando o dispositivo (Host) que vai se conectar
        Host celularDoPai = new Host("Celular do Pai");

        // 3. Gerando o IP para a conexão (simulando a faixa 1.X do arquivo de vocês)
        String ipSorteado = "192.168.1.150";

        // 4. Tentando conectar usando o código real do seu RoteadorPortatil
        try {
            // Chama o método conectar que você me mandou (que joga pro atribuirIP)
            meuPortatil.conectar(ipSorteado);

            // Se o roteador conectou com sucesso e não jogou exceção,
            // a gente configura o Host como CAT (UDP - 180ms) por ser rede móvel portátil
            celularDoPai.configurarConexao(ipSorteado, "CAT", 180);

            // 5. DISPARANDO A THREAD FÍSICA (Sua simulação do quadro!)
            System.out.println("\n📶 Conexão estabelecida no meio físico! Ativando transmissão...");
            Thread threadCelular = new Thread(celularDoPai);
            threadCelular.start();

        } catch (ExcecaoAtribuirIP e) {
            // Se o IP já existisse, o seu roteador portátil trataria o erro aqui
            System.out.println("⚠️ Falha na simulação: " + e.getMessage());
        }
    }
}