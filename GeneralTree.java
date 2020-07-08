
import javax.print.DocFlavor;
import javax.print.attribute.standard.NumberOfDocuments;
import java.awt.*;
import java.util.*;
import java.util.List;

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

        public void setFinalLetter(boolean status){
            this.finalLetter = status;
        }

        public void setSignificado(String sig){
            this.significado = sig;
        }

        public String getSignificado(){
            return this.significado;
        }

        public String getWord(){
            Node aux = father;
            Stack<Character> pilha = new Stack<>();
            pilha.push(element);
            while (aux.father!=null){
                pilha.push(aux.element);
                aux = aux.father;
            }
            StringBuilder word = new StringBuilder();
            while (!pilha.empty()){
                word.append(Character.toString(pilha.pop()));
            }
            return word.toString();
        }

    }

    private Node root;
    private int count;
    private int totalWords;

    public GeneralTree() {
        root = new Node('-');
        count = 0;
        totalWords=0;
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

    public void addWord(String palavra, String significado){
        Node current = new Node(null);
        if (totalWords==0){
            for (int i=0; i<palavra.length(); i++){
                if (i==0) current = add(palavra.charAt(0),current);
                if (i!=0) current = add(palavra.charAt(i),current);
                if (i==palavra.length()-1) addSignificado(palavra.charAt(i),current,significado);
            }
            totalWords++;
        }
        else {
            if(totalWords!=0){
                Node aux = findWord(palavra);
                for (int i=0; i<palavra.length(); i++){
                    if (i==0) current = add(palavra.charAt(0),aux);
                    if (i!=0) current = add(palavra.charAt(i),current);
                    if (i==palavra.length()-1) addSignificado(palavra.charAt(i),current,significado);
                }
                totalWords++;
            }
        }

    }


    private  void searchAllAux(Node node ,ArrayList<Palavra> palavras) {

        if (node!=null){
            if (node.isFinalLetter()) palavras.add(new Palavra(node.getWord(),node.getSignificado()));
            for (int i=0; i<node.getSubtreesSize(); i++) {
                searchAllAux(node.getSubtree(i),palavras);
            }
        }
    }
    public ArrayList<Palavra> searchAll(String prefix) {
        ArrayList<Palavra> palavra = new ArrayList<>();
        Node current = findWord(prefix);

        searchAllAux(current ,palavra);
        return palavra;
    }

    private Node findWord(String word){
        char [] palavra = word.toCharArray();
        Node current = root;
        Node aux =new Node('-');
        boolean find = false;
        while (!find){
            for (int i=0; i<current.getSubtreesSize(); i++){
                for (int j=0; j<palavra.length;j++){
                    if(current.getSubtree(i).element==palavra[j]){
                        aux = current.getSubtree(i);
                        find = true;
                    }
                }

            }
            if (!find) {
                aux=root; break;
            }
        }
        return aux;
    }

    private void addSignificado(Character elem, Node father, String significado){
        Node n = new Node (elem);
        Node aux = searchNodeRef(father.element, root);//vai procurar o pai começando pela RAIZ
        if(aux != null){ //se encontrou o pai
            aux.addSubtree(n); // adiciona N como galho do Father
            n.father = aux; //faz o n apontar pro pai Father
            n.setFinalLetter(true);
            n.setSignificado(significado);
            count++;
        }

    }

    public Node add(Character elem, Node father) {
        Node n = new Node(elem);
        if(father == null){
            if(root != null){
                n.addSubtree(root);
                root.father = n;
            }
            root = n; //faz o N ser a raíz da arvore toda
            count++;
            return n;
        }
        else{
            Node aux = father;//vai procurar o pai começando pela RAIZ
            aux.addSubtree(n); // adiciona N como galho do Father
            n.father = aux; //faz o n apontar pro pai Father
            count++;
            return n;

        }

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

