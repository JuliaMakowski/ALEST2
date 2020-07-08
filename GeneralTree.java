import java.util.LinkedList;

import java.util.ArrayList;

public class GeneralTree {
    private CharNode root;
    private int totalNodes = 0;
    private int totalWords = 0;


    private static class CharNode {
        private final char character;
        private CharNode father;
        private String significado;
        private final boolean isFinal;
        private final LinkedList<CharNode> children;

        public CharNode(char character) {
            this.character = character;
            this.isFinal = false;
            children = new LinkedList<>();
        }

        public CharNode(char character, String significado) {
            this.character = character;
            this.isFinal = true;
            this.significado = significado;
            children = new LinkedList<>();
        }

        public String getWord(){
            if(father.character == '!'){return character + "";}
            return father.getWord()+character + "";
        }

        /**
         * Adiciona um filho (caracter) no nodo. Não pode aceitar caracteres repetidos.
         * @param character - caracter a ser adicionado
         */
        public CharNode addChild (char character) {
            CharNode n = new CharNode(character);
            for(CharNode c: children) {
                if (c.character == character) {
                    return null;
                }
            }
            children.add(n);
            return n;
        }


        public CharNode addChild (char character, String significado) {
            CharNode n = new CharNode(character,significado);
            for(CharNode c: children) {
                if (c.character == character) {
                    return null;
                }
            }
            children.add(n);
            return n;
        }


        public int getNumberOfChildren () {
            return children.size();
        }

        //Retorna o filho na possicao index
        public CharNode getChild (int index) {
            if(index < 0 || index >= children.size()){
                throw new IndexOutOfBoundsException();
            }
            return children.get(index);
        }

        /**
         * Encontra e retorna o nodo que tem determinado caracter.
         * @param character - caracter a ser encontrado.
         */
        public CharNode findChildChar (char character) {
            for(CharNode c : children){
                if(c.character == character){
                    return c;
                }
            }
            return null;
        }
    }

    public GeneralTree() {
        root = new CharNode('!');
        this.totalWords = 0;
        this.totalNodes = 0;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    /**
     *Adiciona palavra na estrutura em árvore
     *@param word
     */
    public void addWord(String word,String significado) {
        CharNode aux = findLastCharNodeForWord(word);
        if(aux != null){
            int i = aux.getWord().length();
            char [] palavra = word.toCharArray();
            CharNode aux1 = aux;
            for(;i< palavra.length;i++){
                if(i==palavra.length-1){
                    aux = aux.addChild(palavra[i],significado);
                    aux.father = aux1;
                }else{
                    aux = aux.addChild(palavra[i]);
                    aux.father = aux1;
                    aux1 = aux;
                }
                totalNodes++;
            }
        }else{
            char [] palavra = word.toCharArray();
            CharNode aux1 = aux;
            for(int i=1;i< palavra.length;i++){
                if(i==palavra.length-1){
                    aux = aux.addChild(palavra[i],significado);
                    aux.father = aux1;
                }else{
                    aux = aux.addChild(palavra[i]);
                    aux.father = aux1;
                    aux1 = aux;

                }
                totalNodes++;
            }
        }
        totalWords++;
    }

    private CharNode findLastCharNodeForWord(String word) {
        CharNode aux1 = null;
        char [] palavra = word.toCharArray();
        CharNode aux = root.findChildChar(palavra[0]);

        if(aux != null){
            aux1 = aux;
            for (int i = 1; i<palavra.length && aux!=null;i++) {
                aux = aux.findChildChar(palavra[i]);
                if(aux != null) aux1 = aux;
            }
        }
        return aux1;
    }



    /**
     * Vai descendo na árvore até onde conseguir encontrar a palavra
     * @param word
     * @return o nodo final encontrado
     */
    private CharNode findCharNodeForWord(String word) {
        char [] palavra = word.toCharArray();
        CharNode aux = root.findChildChar(palavra[0]);
        if(aux != null){
            for (int i = 1; i<palavra.length && aux != null;i++) {
                if(aux == null) return null;
                aux = aux.findChildChar(palavra[i]);
            }

            return aux;
        }
        return null;
    }

    /**
     * Percorre a árvore e retorna uma lista com as palavras iniciadas pelo prefixo dado.
     * Tipicamente, um método recursivo.
     * @param prefix
     */
    public ArrayList<Palavra> searchAll(String prefix) {
        ArrayList<Palavra> ListaDePalavras = new ArrayList<>();
        CharNode aux = findCharNodeForWord(prefix);
        if(aux == null){return null;}
        else{
            searchAllAux(aux,ListaDePalavras);
        }
        return ListaDePalavras;
    }

    private void searchAllAux(CharNode aux, ArrayList<Palavra> lista){
        if(aux != null){
            if(aux.isFinal == true){lista.add(new Palavra(aux.getWord(),aux.significado));}
            for(int i=0;i<aux.getNumberOfChildren();i++){
                searchAllAux(aux.getChild(i),lista);
            }
        }
    }

}
