package Casa;

public class WIFI { // Se o seu arquivo físico chama WIFI.java, a classe fica assim

    protected double velocidade;

    // LINHA 9: O nome aqui precisa ser idêntico ao da classe (Tudo Maiúsculo) ✅
    public WIFI() {
        this.velocidade = 0.0;
    }

    // LINHA 14: Aqui também! ✅
    public WIFI(double velocidade) {
        this.velocidade = velocidade;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }
}