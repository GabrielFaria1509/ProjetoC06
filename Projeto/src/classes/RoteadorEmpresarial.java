package classes;

import java.util.Random;

public class RoteadorEmpresarial extends Roteador {
    private String licencaFirewall;
    private String ipRoteador;

    public RoteadorEmpresarial(String marca, String modelo, double preco, String gateway, String licencaFirewall, String ipRoteador) {
        super(marca, modelo, preco, gateway);
        this.licencaFirewall = licencaFirewall;
        this.ipRoteador = null;
    }


    //unique methods

    //superclass methods
    //TODO: remember to alter the domestic router!! And since we will be using host we cant use ipHost in here. (My dear coleague's are sure that we are ALREDY using host, they didn't even bother to read THEIR OWN code, so I'm sure that they won't read this comment, but right now, Host is not being implemented anywhere, neither is Wifi)
    @Override
    public void gerarIP(){
        Random random = new Random();
        int tentativas = 0;

        while (tentativas < 50){
            int sufix = random.nextInt(253)+2;
            String ipTemporario = "10.0.0." + sufix; 

            if(ipTemporario != this.gateway && !ipsAtribuidos.contains(ipTemporario)){
                this.ipRoteador = ipTemporario;
                System.out.println("o IP " + ipRoteador + " foi atribuído ao roteador com sucesso!");
                return;
            }else if(ipTemporario.equals(this.gateway)){
                System.out.println("O IP " + ipTemporario + " não pode ser atribuído ao roteador, pois é o mesmo do gateway. Tentando outro IP...");

            }else if(ipsAtribuidos.contains(ipTemporario)){
                System.out.println("O IP " + ipTemporario + " já está atribuído a outro dispositivo. Tentando outro IP...");
            }

            tentativas++;
        }
    }
    //interface methods
}
