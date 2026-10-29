package pilhaComArray;

public class PilhaComArray implements Pilha {

    // ===== Atributos =====
    private Integer[] array;
    private boolean resizable;
    private int initialCapacity;
    private int counter;
    private int topIndex;
    private static int X = 10; // capacidade padrão (sublinhado no diagrama = static)

    // ===== Construtores =====
    public PilhaComArray() {
        this(X, true);
    }

    public PilhaComArray(int initialCapacity) {
        this(initialCapacity, true);
    }

    public PilhaComArray(int initialCapacity, boolean resizable) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        this.initialCapacity = initialCapacity;
        this.resizable = resizable;
        this.array = new Integer[initialCapacity];
        this.counter = 0;
        this.topIndex = -1; // -1 indica pilha vazia
    }

    // ===== Métodos =====
    @Override
    public void push(Integer element) {
        if (counter == array.length) {
            if (resizable) {
                aumentarCapacidade();
            } else {
                throw new RuntimeException("Pilha cheia!");
            }
        }
        topIndex++;
        array[topIndex] = element;
        counter++;
    }

    @Override
    public Integer pop() {
        if (isEmpty()) {
            return null;
        }
        Integer valor = array[topIndex];
        array[topIndex] = null;
        topIndex--;
        counter--;
        return valor;
    }

    @Override
    public Integer top() {
        if (isEmpty()) {
            return null;
        }
        return array[topIndex];
    }

    @Override
    public void clear() {
        array = new Integer[initialCapacity];
        counter = 0;
        topIndex = -1;
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public String toString() {
        // mostra do topo para a base, ex.: [30, 20, 10]
        StringBuilder sb = new StringBuilder("[");
        for (int i = topIndex; i >= 0; i--) {
            sb.append(array[i]);
            if (i > 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // dobra o tamanho do array quando ele enche
    private void aumentarCapacidade() {
        Integer[] novo = new Integer[array.length * 2];
        for (int i = 0; i < counter; i++) {
            novo[i] = array[i];
        }
        array = novo;
    }

    // ===== Teste =====
    public static void main(String[] args) {
        PilhaComArray pilha = new PilhaComArray(2); // capacidade pequena para testar o crescimento

        System.out.println("Vazia? " + pilha.isEmpty());    // true

        pilha.push(10);
        pilha.push(20);
        pilha.push(30);  // array cheio: dobra de tamanho
        System.out.println("Pilha: " + pilha);              // [30, 20, 10]
        System.out.println("Tamanho: " + pilha.size());     // 3
        System.out.println("Topo: " + pilha.top());         // 30

        System.out.println("Pop: " + pilha.pop());          // 30
        System.out.println("Pilha: " + pilha);              // [20, 10]

        PilhaComArray fixa = new PilhaComArray(1, false);
        fixa.push(1);
        try {
            fixa.push(2);
        } catch (RuntimeException e) {
            System.out.println("Erro esperado: " + e.getMessage()); // Pilha cheia!
        }

        pilha.clear();
        System.out.println("Depois do clear: " + pilha);    // []
        System.out.println("Vazia? " + pilha.isEmpty());    // true
        System.out.println("Pop na vazia: " + pilha.pop()); // null
    }
}