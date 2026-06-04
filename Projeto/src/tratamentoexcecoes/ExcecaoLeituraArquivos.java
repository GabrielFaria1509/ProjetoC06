package tratamentoexcecoes;

import java.io.IOException;

public class ExcecaoLeituraArquivos extends IOException {
    public ExcecaoLeituraArquivos(String message) {
        super(message);
    }
}
