package classes;

public class WIFI {
    // A velocidade agora é uma variável (atributo) protegida da classe
    private double velocidadewifi;
    private int pingMs;

    // O construtor recebe a velocidade e já calcula o ping automático
    public WIFI(double velocidadewifi) {
        this.velocidadewifi = velocidadewifi;
        this.calcularPing();
    }

    // A lógica de redes que decide o ping com base na sua variável
    private void calcularPing() {
        if (this.velocidadewifi >= 300.0) {
            this.pingMs = 60; // Rede rápida -> Latência baixa
        } else {
            this.pingMs = 180; // Rede lenta -> Latência alta
        }
    }

    // Getters para a Main e o Roteador conseguirem ler os valores
    public int getPingMs() {
        return this.pingMs;
    }

    public double getVelocidadewifi() {
        return this.velocidadewifi;
    }
}