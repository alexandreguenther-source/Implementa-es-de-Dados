import filaComArray.FilaComArray;
import filaComEncadeamento.FilaComEncadeamento;
import pilhaComArray.PilhaComArray;
import pilhaComEncadeamento.PilhaComEncadeamento;
import arvoreBinariaComArray.ArvoreBinariaComArray;
import tabelaHash.TabelaHash;

// Roda o teste (main) de todas as estruturas, uma depois da outra
public class App {

    public static void main(String[] args) {
        titulo("FilaComArray");
        FilaComArray.main(args);

        titulo("FilaComEncadeamento");
        FilaComEncadeamento.main(args);

        titulo("PilhaComArray");
        PilhaComArray.main(args);

        titulo("PilhaComEncadeamento");
        PilhaComEncadeamento.main(args);

        titulo("ArvoreBinariaComArray");
        ArvoreBinariaComArray.main(args);

        titulo("TabelaHash");
        TabelaHash.main(args);
    }

    private static void titulo(String nome) {
        System.out.println();
        System.out.println("========== " + nome + " ==========");
    }
}
