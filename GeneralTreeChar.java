
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class GeneralTreeOfInteger {

    // Classe interna Node
    private class Node {
        // Atributos da classe Node
        public Node father;
        public Integer element;
        public LinkedList<Node> subtrees;
        // Métodos da classe Node
        public Node(Integer element) {
            father = null;
            this.element = element;
            subtrees = new LinkedList<>();
        }
        private void addSubtree(Node n) {
            n.father = this;
            subtrees.add(n);
        }
        private boolean removeSubtree(Node n) {
            n.father = null;
            return subtrees.remove(n);
        }
        public Node getSubtree(int i) {
            if ((i < 0) || (i >= subtrees.size())) {
                throw new IndexOutOfBoundsException();
            }
            return subtrees.get(i);
        }
        public int getSubtreesSize() {
            return subtrees.size();
        }
    }



    // Atributos da classe GeneralTreeOfInteger
    private Node root;
    private int count;


    // Metodos da classe GeneralTreeOfInteger

    public GeneralTreeOfInteger() {
        root = null;
        count = 0;
    }
//    [6, /      |       \]
//       [3,-] [8, |]   [5, -]
//                [4, -]

    private Node searchNodeRef(Integer elem, Node n) {
        if (n == null) // se n for null, nao tem como procurar por elem
            return null; // retorna null porque não encontrou elem

        if (elem.equals(n.element)) { //achou o elem (equivale ao "visita a raiz")
            return n; // retorna a referencia para o nodo no qual elem esta armazenado
        }
        else { // procura por elem nos filhos de n (equivale ao "visita os filhos")
            Node aux = null;
            int i=0;
            while ( (aux==null) && (i<n.getSubtreesSize()) ) {
                aux = searchNodeRef(elem, n.getSubtree(i));
                i++;
            }
            return aux;
        }
    }

    public boolean add(Integer elem, Integer father) {
        Node n = new Node(elem);

        if (father == null) { // inserir elem na raiz
            if( root != null ) { // se a arvore nao estava vazia
                n.addSubtree(root);
                root.father = n;
            }
            root = n;
            count++;
            return true;
        }
        else {
            Node aux = searchNodeRef(father,root);
            if (aux != null) { // se encontrou o pai
                aux.addSubtree(n);
                n.father = aux;
                count++;
                return true;
            }
        }

        return false; // caso nao encontre o pai na arvore
    }

    public boolean contains(Integer elem) {
        Node aux = searchNodeRef(elem, root);
        if(aux == null)
            return false;
        else
            return true;
    }


    // Retorna uma lista com todos os elementos da árvore numa ordem de
    // caminhamento em largura
    public LinkedList<Integer> positionsWidth() {
        LinkedList<Integer> lista = new LinkedList<>();

        Queue<Node> fila = new Queue<>();
        if (root != null) {
            fila.enqueue(root); // coloca a raiz na fila
            while (!fila.isEmpty()) {
                Node aux = fila.dequeue();
                lista.add(aux.element); // coloca o elemento na lista
                // coloca os filhos na fila
                for(int i=0; i<aux.getSubtreesSize(); i++) {
                    fila.enqueue(aux.getSubtree(i));
                }
            }
        }

        return lista;
    }

    // Retorna uma lista com todos os elementos da árvore numa ordem de
    // caminhamento pré-fixado
    public LinkedList<Integer> positionsPre() {
        LinkedList<Integer> lista = new LinkedList<>();
        positionsPreAux(root,lista);
        return lista;
    }
    private void positionsPreAux(Node n, LinkedList<Integer> lista) { // recursao
        if (n != null) {
            lista.add(n.element);
            for (int i=0; i<n.getSubtreesSize(); i++) {
                positionsPreAux(n.getSubtree(i),lista);
            }
        }
    }

    // Retorna uma lista com todos os elementos da árvore numa ordem de
    // caminhamento pós-fixado
    public LinkedList<Integer> positionsPos() {
        LinkedList<Integer> lista = new LinkedList<>();
        positionsPosAux(root,lista);
        return lista;
    }
    private void positionsPosAux(Node n, LinkedList<Integer> lista) {
        if (n != null) {
            for (int i=0; i<n.getSubtreesSize(); i++) {
                positionsPosAux(n.getSubtree(i),lista);
            }
            lista.add(n.element);
        }
    }

    // Retorna em que nível o elemento está
    public int level(Integer element) {

        Node n = searchNodeRef(element, root);

        if (n==null) // elemento nao foi encontrado
            throw new NoSuchElementException();

        int c = 0;
        while (n != root) {
            c++;
            n = n.father;
        }
        return c;

    }

    // Remove um galho da arvore. Retorna true se houve remocao.
    public boolean removeBranch(Integer element) {
        if (root == null) // se a arvore estiver vazia
            return false;

        if (root.element.equals(element)) { // se element estiver na raiz
            // arvore ficara vazia
            root = null;
            count = 0;
            return true;
        }

        Node aux = searchNodeRef(element, root);

        if (aux == null) // se nao encontrou "element"
            return false;

        int c = countNodes(aux);
        count = count - c;
        Node father = aux.father;
        father.removeSubtree(aux);
        return true;
    }

    // Conta o numero de nodos da subarvore cuja raiz eh passada por parametro
    private int countNodes(Node n) {
        if (n==null)
            return 0;
        int c=0;
        for (int i=0; i<n.getSubtreesSize(); i++) {
            c = c + countNodes(n.getSubtree(i));
        }
        return 1+c;
    }

    // Retorna true se element esta armazenado em um nodo interno
    public boolean isInternal(Integer element) {
        return false;
    }

    // Retorna true se element esta armazenado em um nodo folha
    public boolean isExternal(Integer element) {
        Node n = searchNodeRef(element, root);
        if (n==null)
            return false;
        if (n.getSubtreesSize()==0)
            return true;
        else
            return false;
    }

    // Retorna quantos filhos tem, aquele nodo que tem o maior numero de filhos
    public int getMaxChildren() {
        return getMaxChildren(root,0);
    }

    private int getMaxChildren(Node n, int numFilhos) {
        // Implemente este metodo
        return 0;
    }
}


