package arvoreBinariaComArray;

// Árvore binária de busca guardada em um array.
// Posições (raiz no índice 0):
//   filho da esquerda de i -> 2*i + 1
//   filho da direita  de i -> 2*i + 2
// Valores menores vão para a esquerda, maiores para a direita.
public class ArvoreBinariaComArray {

    // ===== Atributos =====
    private Integer[] array;
    private boolean resizable;
    private int initialCapacity;
    private int counter;
    private static int X = 15; // capacidade padrão (4 níveis completos)

    // ===== Construtores =====
    public ArvoreBinariaComArray() {
        this(X, true);
    }

    public ArvoreBinariaComArray(int initialCapacity) {
        this(initialCapacity, true);
    }

    public ArvoreBinariaComArray(int initialCapacity, boolean resizable) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        this.initialCapacity = initialCapacity;
        this.resizable = resizable;
        this.array = new Integer[initialCapacity];
        this.counter = 0;
    }

    // ===== Posições =====
    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    // existe um nó na posição i?
    private boolean exists(int i) {
        return i < array.length && array[i] != null;
    }

    // ===== Métodos =====

    // insere seguindo a regra: menor à esquerda, maior à direita (ignora repetidos)
    public void insert(Integer element) {
        if (element == null) {
            throw new IllegalArgumentException("Elemento não pode ser nulo");
        }
        int i = 0;
        while (exists(i)) {
            if (element.equals(array[i])) {
                return; // já existe
            }
            if (element < array[i]) {
                i = left(i);
            } else {
                i = right(i);
            }
        }
        if (i >= array.length) {
            if (resizable) {
                aumentarCapacidade(i);
            } else {
                throw new RuntimeException("Árvore cheia!");
            }
        }
        array[i] = element;
        counter++;
    }

    // procura um valor: true se encontrou
    public boolean contains(Integer element) {
        int i = 0;
        while (exists(i)) {
            if (element.equals(array[i])) {
                return true;
            }
            if (element < array[i]) {
                i = left(i);
            } else {
                i = right(i);
            }
        }
        return false;
    }

    // menor valor: o nó mais à esquerda
    public Integer min() {
        if (isEmpty()) {
            return null;
        }
        int i = 0;
        while (exists(left(i))) {
            i = left(i);
        }
        return array[i];
    }

    // maior valor: o nó mais à direita
    public Integer max() {
        if (isEmpty()) {
            return null;
        }
        int i = 0;
        while (exists(right(i))) {
            i = right(i);
        }
        return array[i];
    }

    // pré-ordem: raiz, esquerda, direita
    public String preOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder(0, sb);
        return sb.toString().trim();
    }

    private void preOrder(int i, StringBuilder sb) {
        if (!exists(i)) {
            return;
        }
        sb.append(array[i]).append(" ");
        preOrder(left(i), sb);
        preOrder(right(i), sb);
    }

    // em ordem: esquerda, raiz, direita (sai em ordem crescente)
    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(0, sb);
        return sb.toString().trim();
    }

    private void inOrder(int i, StringBuilder sb) {
        if (!exists(i)) {
            return;
        }
        inOrder(left(i), sb);
        sb.append(array[i]).append(" ");
        inOrder(right(i), sb);
    }

    // pós-ordem: esquerda, direita, raiz
    public String postOrder() {
        StringBuilder sb = new StringBuilder();
        postOrder(0, sb);
        return sb.toString().trim();
    }

    private void postOrder(int i, StringBuilder sb) {
        if (!exists(i)) {
            return;
        }
        postOrder(left(i), sb);
        postOrder(right(i), sb);
        sb.append(array[i]).append(" ");
    }

    // altura: árvore vazia = 0, só a raiz = 1
    public int height() {
        return height(0);
    }

    private int height(int i) {
        if (!exists(i)) {
            return 0;
        }
        return 1 + Math.max(height(left(i)), height(right(i)));
    }

    public void clear() {
        array = new Integer[initialCapacity];
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
        // mostra o array por dentro, com "-" nas posições vazias
        // ex.: [50, 30, 70, 20, 40, -, 80]
        StringBuilder sb = new StringBuilder("[");
        int ultimo = array.length - 1;
        while (ultimo >= 0 && array[ultimo] == null) {
            ultimo--;
        }
        for (int i = 0; i <= ultimo; i++) {
            sb.append(array[i] == null ? "-" : array[i]);
            if (i < ultimo) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // aumenta o array até caber a posição pedida
    private void aumentarCapacidade(int posicao) {
        int novoTamanho = array.length;
        while (novoTamanho <= posicao) {
            novoTamanho = novoTamanho * 2 + 1; // cabe mais um nível inteiro
        }
        Integer[] novo = new Integer[novoTamanho];
        for (int i = 0; i < array.length; i++) {
            novo[i] = array[i];
        }
        array = novo;
    }

    // ===== Teste =====
    public static void main(String[] args) {
        ArvoreBinariaComArray arvore = new ArvoreBinariaComArray(7);

        System.out.println("Vazia? " + arvore.isEmpty());          // true

        int[] valores = {50, 30, 70, 20, 40, 60, 80, 35};
        for (int v : valores) {
            arvore.insert(v);
        }
        //            50
        //          /    \
        //        30      70
        //       /  \    /  \
        //     20   40  60   80
        //          /
        //        35
        System.out.println("Array: " + arvore);                    // [50, 30, 70, 20, 40, 60, 80, -, -, 35]
        System.out.println("Tamanho: " + arvore.size());           // 8
        System.out.println("Altura: " + arvore.height());          // 4

        System.out.println("Pré-ordem: " + arvore.preOrder());     // 50 30 20 40 35 70 60 80
        System.out.println("Em ordem:  " + arvore.inOrder());      // 20 30 35 40 50 60 70 80
        System.out.println("Pós-ordem: " + arvore.postOrder());    // 20 35 40 30 60 80 70 50

        System.out.println("Contém 40? " + arvore.contains(40));   // true
        System.out.println("Contém 99? " + arvore.contains(99));   // false
        System.out.println("Mínimo: " + arvore.min());             // 20
        System.out.println("Máximo: " + arvore.max());             // 80

        ArvoreBinariaComArray fixa = new ArvoreBinariaComArray(3, false);
        fixa.insert(10);
        fixa.insert(5);
        fixa.insert(15);
        try {
            fixa.insert(1); // precisaria da posição 3, mas o array só tem 3 posições
        } catch (RuntimeException e) {
            System.out.println("Erro esperado: " + e.getMessage()); // Árvore cheia!
        }

        arvore.clear();
        System.out.println("Depois do clear: " + arvore);          // []
        System.out.println("Vazia? " + arvore.isEmpty());          // true
    }
}