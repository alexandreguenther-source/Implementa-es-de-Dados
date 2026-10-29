package pilhaComEncadeamento;

import pilhaComArray.Pilha;

public class PilhaComEncadeamento implements Pilha {

    // ===== Atributos =====
    private NoLista topNode;
    private int counter;

    // ===== Construtor =====
    public PilhaComEncadeamento() {
        topNode = null;
        counter = 0;
    }

    // ===== Métodos =====
    @Override
    public void push(Integer element) {
        // o novo nó aponta para o antigo topo e passa a ser o topo
        topNode = new NoLista(element, topNode);
        counter++;
    }

    @Override
    public Integer pop() {
        if (isEmpty()) {
            return null;
        }
        Integer valor = topNode.getInfo();
        topNode = topNode.getNext();
        counter--;
        return valor;
    }

    @Override
    public Integer top() {
        if (isEmpty()) {
            return null;
        }
        return topNode.getInfo();
    }

    @Override
    public void clear() {
        topNode = null;
        counter = 0;
    }

    @Override
    public boolean isEmpty() {
        return topNode == null;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public String toString() {
        // mostra do topo para a base, ex.: [30, 20, 10]
        StringBuilder sb = new StringBuilder("[");
        NoLista atual = topNode;
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
        PilhaComEncadeamento pilha = new PilhaComEncadeamento();

        System.out.println("Vazia? " + pilha.isEmpty());    // true

        pilha.push(10);
        pilha.push(20);
        pilha.push(30);
        System.out.println("Pilha: " + pilha);              // [30, 20, 10]
        System.out.println("Tamanho: " + pilha.size());     // 3
        System.out.println("Topo: " + pilha.top());         // 30

        System.out.println("Pop: " + pilha.pop());          // 30
        System.out.println("Pilha: " + pilha);              // [20, 10]

        pilha.clear();
        System.out.println("Depois do clear: " + pilha);    // []
        System.out.println("Vazia? " + pilha.isEmpty());    // true
        System.out.println("Pop na vazia: " + pilha.pop()); // null
    }
}

// =====================================================
// NoLista (o "nó" que guarda cada elemento da pilha)
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