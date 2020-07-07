
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

public class GeneralTree {

    // Classe interna Node
    private static class Node {
        public Node father;
        public Character element;
        public LinkedList<Node> subtrees;
        public boolean finalLetter = false;
        public String significado;

        // Métodos da classe Node
        public Node(Character element) {
            father = null;
            this.element = element;
            subtrees = new LinkedList<>();
            finalLetter = false;
            significado = null;
        }
        private void addSubtree(Node n) {
            n.father = this;
            subtrees.add(n);
        }
        private void removeSubtree(Node n) {
            n.father = null;
            subtrees.remove(n);
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

        public boolean isFinalLetter() {
            return finalLetter;
        }

        public void setFinalLetterTrue(){
            this.finalLetter = true;
        }

        public void setSignificado(String sig){
            this.significado = sig;
        }

        public String getSignificado(){
            return this.significado;
        }
    }

    private Node root;
    private int count;
    private int totalWords;
    
    public GeneralTree() {
        root = null;
        count = 0;
    }

    
    private Node searchNodeRef(Character elem, Node n) {
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

    private boolean fimDaPalavra=false;

    private String significado="";

    public void setSignificado(String significado){
        this.significado=significado;
    }

    private void setFimDaPalavra(){
        this.fimDaPalavra = true;
    }

    public void addAux(String palavra, String significado){
        Node current = root;
        if (current==null){
            for (int i=0; i<palavra.length()-1; i++){
                if (i==0 && current!=null) add(palavra.charAt(0),current.element);
                if (i==0 && current==null) add(palavra.charAt(0),null);
                if (i!=0) add(palavra.charAt(i),palavra.charAt(i-1));
                if (i==palavra.length()-1) addSignificado(palavra.charAt(i),palavra.charAt(i-1),significado);
            }
            totalWords++;
        } else {

        }

    }

    public void addSignificado(Character elem, Character father, String significado){
        Node n = new Node (elem);
        Node aux = searchNodeRef(father, root);//vai procurar o pai começando pela RAIZ
        if(aux != null){ //se encontrou o pai
            aux.addSubtree(n); // adiciona N como galho do Father
            n.father = aux; //faz o n apontar pro pai Father
            count++;
        }
        n.setFinalLetterTrue();
        n.setSignificado(significado);
    }

    public boolean add(Character elem, Character father) {
        Node n = new Node(elem);
        if(father == null){
            if(root != null){
                n.addSubtree(root);
                root.father = n;
            }
            root = n; //faz o N ser a raíz da arvore toda
            count++;
            return true;
        }
        else{
            Node aux = searchNodeRef(father, root);//vai procurar o pai começando pela RAIZ
            if(aux != null){ //se encontrou o pai
                aux.addSubtree(n); // adiciona N como galho do Father
                n.father = aux; //faz o n apontar pro pai Father
                count++;
                return true;
            }
        }
        return false;
    }


    public boolean contains(Character elem) {
        Node aux = searchNodeRef(elem, root);
        if(aux == null)
            return false;
        else
            return true;
    }


//     Retorna uma lista com todos os elementos da árvore numa ordem de
    // caminhamento em largura
    public LinkedList<Character> positionsWidth() {
        LinkedList<Character> lista = new LinkedList<>();

        Stack<Node> fila = new Stack<>();
        if (root != null) {
            fila.push(root); // coloca a raiz na fila
            while (!fila.isEmpty()) {
                Node aux = fila.pop();
                lista.add(aux.element); // coloca o elemento na lista
                // coloca os filhos na fila
                for(int i=0; i<aux.getSubtreesSize(); i++) {
                    fila.push(aux.getSubtree(i));
                }
            }
        }

        return lista;
    }

    
    // Retorna uma lista com todos os elementos da árvore numa ordem de 
    // caminhamento pré-fixado
    public LinkedList<Character> positionsPre() {
        LinkedList<Character> lista = new LinkedList<>();
        positionsPreAux(root,lista);
        return lista;
    }  
    private void positionsPreAux(Node n, LinkedList<Character> lista) { // recursao
        if (n != null) {
            lista.add(n.element);
            for (int i=0; i<n.getSubtreesSize(); i++) {
                positionsPreAux(n.getSubtree(i),lista);
            }
        } 
    }

    // Retorna uma lista com todos os elementos da árvore numa ordem de 
    // caminhamento pós-fixado
    public LinkedList<Character> positionsPos() {
        LinkedList<Character> lista = new LinkedList<>();
        positionsPosAux(root,lista);
        return lista;
    }

    private void positionsPosAux(Node n, LinkedList<Character> lista) {
        if (n != null) {
            for (int i=0; i<n.getSubtreesSize(); i++) {
                positionsPosAux(n.getSubtree(i),lista);
            }
            lista.add(n.element);            
        } 
    }    
    
    // Retorna em que nível o elemento está 
    public int level(Character element) {
        
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
    public boolean removeBranch(Character element) {
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
    public boolean isExternal(Character element) {
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
        if(n != null){
            int maior = n.getSubtreesSize(); //primeiro vai pegar o numero de filhos da raiz

            for(int i = 0; i < n.getSubtreesSize(); i++){ //então vai percorrer os filhos
                numFilhos = getMaxChildren(n.getSubtree(i), numFilhos); //os filhos vão trazer o maior numero de filhos entre eles
            }

            if(maior > numFilhos){ //se o numero de filhos da raiz for maior que o numero de filhos encontrado nos filhos, numFilhos = o numero de filhos da raiz
                numFilhos = maior;
            }
            return numFilhos;

        }
        return 0;
    }
}

