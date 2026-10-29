package filaComEncadeamento;

public class FilaComEncadeamento {

    // ===== Atributos =====
    private NoLista firstNode; // início da fila (de onde sai)
    private NoLista lastNode;  // fim da fila (onde entra)
    private int counter;

    // ===== Construtor =====
    public FilaComEncadeamento() {
        firstNode = null;
        lastNode = null;
        counter = 0;
    }

    // ===== Métodos =====

    // adiciona no FIM da fila
    public void enqueue(Integer element) {
        NoLista novo = new NoLista(element, null);
        if (isEmpty()) {
            firstNode = novo;          // fila vazia: o novo é o primeiro e o último
        } else {
            lastNode.setNext(novo);    // o antigo último aponta para o novo
        }
        lastNode = novo;
        counter++;
    }

    // remove do INÍCIO da fila
    public Integer dequeue() {
        if (isEmpty()) {
            return null;
        }
        Integer valor = firstNode.getInfo();
        firstNode = firstNode.getNext();
        if (firstNode == null) {
            lastNode = null;           // a fila ficou vazia
        }
        counter--;
        return valor;
    }

    // mostra quem está no início, sem remover
    public Integer front() {
        if (isEmpty()) {
            return null;
        }
        return firstNode.getInfo();
    }

    public void clear() {
        firstNode = null;
        lastNode = null;
        counter = 0;
    }

    public boolean isEmpty() {
        return firstNode == null;
    }

    public int size() {
        return counter;
    }

    @Override
    public String toString() {
        // mostra do início para o fim, ex.: [10, 20, 30]
        StringBuilder sb = new StringBuilder("[");
        NoLista atual = firstNode;
        while (atual != null) {
            sb.append(atual.getInfo());
            if (atual.getNext() != null) {
                sb.append(", ");
            }
            atual = atual.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    // ===== Teste =====
    public static void main(String[] args) {
        FilaComEncadeamento fila = new FilaComEncadeamento();

        System.out.println("Vazia? " + fila.isEmpty());        // true

        fila.enqueue(10);
        fila.enqueue(20);
        fila.enqueue(30);
        System.out.println("Fila: " + fila);                   // [10, 20, 30]
        System.out.println("Tamanho: " + fila.size());         // 3
        System.out.println("Início: " + fila.front());         // 10

        System.out.println("Dequeue: " + fila.dequeue());      // 10
        System.out.println("Fila: " + fila);                   // [20, 30]

        fila.enqueue(40);
        System.out.println("Fila: " + fila);                   // [20, 30, 40]

        fila.clear();
        System.out.println("Depois do clear: " + fila);        // []
        System.out.println("Vazia? " + fila.isEmpty());        // true
        System.out.println("Dequeue na vazia: " + fila.dequeue()); // null
    }
}

// =====================================================
// NoLista (o "nó" que guarda cada elemento da fila)
// =====================================================
class NoLista {

    private Integer info;
    private NoLista next;

    public NoLista() {
        this.info = null;
        this.next = null;
    }

    public NoLista(Integer info, NoLista next) {
        this.info = info;
        this.next = next;
    }

    public Integer getInfo() {
        return info;
    }

    public void setInfo(Integer info) {
        this.info = info;
    }

    public NoLista getNext() {
        return next;
    }

    public void setNext(NoLista next) {
        this.next = next;
    }
}