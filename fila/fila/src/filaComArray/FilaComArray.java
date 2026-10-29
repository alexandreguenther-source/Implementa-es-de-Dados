package filaComArray;

public class FilaComArray {

    // ===== Atributos =====
    private Integer[] array;
    private boolean resizable;
    private int initialCapacity;
    private int counter;
    private int firstIndex;    // posição do início da fila (de onde sai)
    private int lastIndex;     // posição do fim da fila (onde entrou o último)
    private static int X = 10; // capacidade padrão

    // ===== Construtores =====
    public FilaComArray() {
        this(X, true);
    }

    public FilaComArray(int initialCapacity) {
        this(initialCapacity, true);
    }

    public FilaComArray(int initialCapacity, boolean resizable) {
        this.initialCapacity = initialCapacity;
        this.resizable = resizable;
        this.array = new Integer[initialCapacity];
        this.counter = 0;
        this.firstIndex = 0;
        this.lastIndex = -1;
    }

    // ===== Métodos =====

    // adiciona no FIM da fila
    public void enqueue(Integer element) {
        if (counter == array.length) {
            if (resizable) {
                aumentarCapacidade();
            } else {
                throw new RuntimeException("Fila cheia!");
            }
        }
        // anda uma posição; se passar do fim do array, volta para o 0 (array circular)
        lastIndex = (lastIndex + 1) % array.length;
        array[lastIndex] = element;
        counter++;
    }

    // remove do INÍCIO da fila
    public Integer dequeue() {
        if (isEmpty()) {
            return null;
        }
        Integer valor = array[firstIndex];
        array[firstIndex] = null;
        firstIndex = (firstIndex + 1) % array.length;
        counter--;
        return valor;
    }

    // mostra quem está no início, sem remover
    public Integer front() {
        if (isEmpty()) {
            return null;
        }
        return array[firstIndex];
    }

    public void clear() {
        array = new Integer[initialCapacity];
        counter = 0;
        firstIndex = 0;
        lastIndex = -1;
    }

    public boolean isEmpty() {
        return counter == 0;
    }

    public int size() {
        return counter;
    }

    @Override
    public String toString() {
        // mostra do início para o fim, ex.: [10, 20, 30]
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < counter; i++) {
            sb.append(array[(firstIndex + i) % array.length]);
            if (i < counter - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // dobra o tamanho do array, colocando os elementos em ordem a partir do 0
    private void aumentarCapacidade() {
        Integer[] novo = new Integer[array.length * 2];
        for (int i = 0; i < counter; i++) {
            novo[i] = array[(firstIndex + i) % array.length];
        }
        array = novo;
        firstIndex = 0;
        lastIndex = counter - 1;
    }

    // ===== Teste =====
    public static void main(String[] args) {
        FilaComArray fila = new FilaComArray(3); // capacidade pequena para testar o crescimento

        System.out.println("Vazia? " + fila.isEmpty());        // true

        fila.enqueue(10);
        fila.enqueue(20);
        fila.enqueue(30);
        System.out.println("Fila: " + fila);                   // [10, 20, 30]
        System.out.println("Tamanho: " + fila.size());         // 3
        System.out.println("Início: " + fila.front());         // 10

        System.out.println("Dequeue: " + fila.dequeue());      // 10
        System.out.println("Fila: " + fila);                   // [20, 30]

        fila.enqueue(40);  // usa a posição que o 10 liberou (circular)
        fila.enqueue(50);  // array cheio: dobra de tamanho
        System.out.println("Fila: " + fila);                   // [20, 30, 40, 50]

        fila.clear();
        System.out.println("Depois do clear: " + fila);        // []
        System.out.println("Vazia? " + fila.isEmpty());        // true
        System.out.println("Dequeue na vazia: " + fila.dequeue()); // null
    }
}
