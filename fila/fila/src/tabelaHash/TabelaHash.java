package tabelaHash;

// Tabela hash que guarda pares chave -> valor.
// A posição de cada chave no array é calculada pela função hash (chave % tamanho).
// Quando duas chaves caem na mesma posição (colisão), elas ficam numa
// lista encadeada naquela posição (encadeamento separado).
public class TabelaHash {

    // ===== Atributos =====
    private NoHash[] array;
    private int initialCapacity;
    private int counter;
    private static int X = 10;                    // capacidade padrão
    private static double FATOR_CARGA_MAX = 0.75; // passou disso, o array dobra

    // ===== Construtores =====
    public TabelaHash() {
        this(X);
    }

    public TabelaHash(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        this.initialCapacity = initialCapacity;
        this.array = new NoHash[initialCapacity];
        this.counter = 0;
    }

    // ===== Função hash =====
    // transforma a chave numa posição válida do array (Math.abs evita posição negativa)
    private int hash(Integer key) {
        return Math.abs(key % array.length);
    }

    // ===== Métodos =====

    // insere o par; se a chave já existir, só atualiza o valor
    public void put(Integer key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Chave não pode ser nula");
        }
        int pos = hash(key);
        NoHash atual = array[pos];
        while (atual != null) {
            if (atual.getKey().equals(key)) {
                atual.setValue(value); // chave repetida: atualiza
                return;
            }
            atual = atual.getNext();
        }
        // chave nova: entra no início da lista daquela posição
        array[pos] = new NoHash(key, value, array[pos]);
        counter++;

        if ((double) counter / array.length > FATOR_CARGA_MAX) {
            aumentarCapacidade();
        }
    }

    // retorna o valor da chave, ou null se não existir
    public String get(Integer key) {
        if (key == null) {
            return null;
        }
        NoHash atual = array[hash(key)];
        while (atual != null) {
            if (atual.getKey().equals(key)) {
                return atual.getValue();
            }
            atual = atual.getNext();
        }
        return null;
    }

    public boolean containsKey(Integer key) {
        return get(key) != null;
    }

    // remove a chave e retorna o valor dela, ou null se não existir
    public String remove(Integer key) {
        if (key == null) {
            return null;
        }
        int pos = hash(key);
        NoHash anterior = null;
        NoHash atual = array[pos];
        while (atual != null) {
            if (atual.getKey().equals(key)) {
                if (anterior == null) {
                    array[pos] = atual.getNext();       // era o primeiro da lista
                } else {
                    anterior.setNext(atual.getNext());  // pula o nó removido
                }
                counter--;
                return atual.getValue();
            }
            anterior = atual;
            atual = atual.getNext();
        }
        return null;
    }

    public void clear() {
        array = new NoHash[initialCapacity];
        counter = 0;
    }

    public boolean isEmpty() {
        return counter == 0;
    }

    public int size() {
        return counter;
    }

    @Override
    public String toString() {
        // mostra cada posição ocupada e a lista de pares dela
        // ex.: [3] 13=Maria -> 3=Ana
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                continue;
            }
            sb.append("[").append(i).append("] ");
            NoHash atual = array[i];
            while (atual != null) {
                sb.append(atual.getKey()).append("=").append(atual.getValue());
                if (atual.getNext() != null) {
                    sb.append(" -> ");
                }
                atual = atual.getNext();
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // dobra o array e recalcula a posição de todos os pares
    private void aumentarCapacidade() {
        NoHash[] antigo = array;
        array = new NoHash[antigo.length * 2];
        counter = 0;
        for (int i = 0; i < antigo.length; i++) {
            NoHash atual = antigo[i];
            while (atual != null) {
                put(atual.getKey(), atual.getValue());
                atual = atual.getNext();
            }
        }
    }

    // ===== Teste =====
    public static void main(String[] args) {
        TabelaHash tabela = new TabelaHash(10);

        System.out.println("Vazia? " + tabela.isEmpty());              // true

        tabela.put(3, "Ana");
        tabela.put(13, "Maria");   // 13 % 10 = 3: colide com a Ana
        tabela.put(7, "Pedro");
        tabela.put(25, "João");
        System.out.println("Tabela:");
        System.out.print(tabela);
        // [3] 13=Maria -> 3=Ana
        // [5] 25=João
        // [7] 7=Pedro
        System.out.println("Tamanho: " + tabela.size());               // 4

        System.out.println("get(13): " + tabela.get(13));              // Maria
        System.out.println("get(99): " + tabela.get(99));              // null

        tabela.put(3, "Ana Paula"); // chave repetida: atualiza
        System.out.println("get(3) depois de atualizar: " + tabela.get(3)); // Ana Paula

        System.out.println("remove(13): " + tabela.remove(13));        // Maria
        System.out.println("Contém 13? " + tabela.containsKey(13));    // false
        System.out.println("Tamanho: " + tabela.size());               // 3

        // inserindo mais pares para passar de 75% e o array dobrar
        for (int i = 30; i < 36; i++) {
            tabela.put(i, "Pessoa" + i);
        }
        System.out.println("Tamanho depois de inserir mais: " + tabela.size()); // 9
        System.out.println("get(33): " + tabela.get(33));              // Pessoa33

        tabela.clear();
        System.out.println("Depois do clear, vazia? " + tabela.isEmpty()); // true
    }
}

// =====================================================
// NoHash (o "nó" que guarda cada par chave -> valor)
// =====================================================
class NoHash {

    private Integer key;
    private String value;
    private NoHash next;

    public NoHash(Integer key, String value, NoHash next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public NoHash getNext() {
        return next;
    }

    public void setNext(NoHash next) {
        this.next = next;
    }
}
