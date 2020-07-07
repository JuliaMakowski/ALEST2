
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import java.util.Scanner;

public class Main {
    private static GeneralTree lista = new GeneralTree();
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        String letras= "";
        do {
            System.out.println("Digite as letras iniciais: (Não maior que 3 letras)");
            letras = in.next();
        }while (letras.length()-1>2);
        leituraArquivo(letras);
    }


    public static void leituraArquivo(String letras){
        char [] letraInicial = letras.toCharArray();
        String[] aux;
        Path path1 = Paths.get("nomes.csv");
        boolean primeiraPalavra =true;

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                aux = line.split(";");
                if (iniciaisCorrespondem(aux[0],letraInicial)){
                    Palavra p = new Palavra(aux[0],aux[1]);
                    for (int i=0; i<p.getPalavra().length()-1; i++){
                        if (i==0 && !primeiraPalavra) lista.add(aux[0].charAt(0),letraInicial[0]);
                        if (i==0 && primeiraPalavra) lista.add(aux[0].charAt(0),null);
                        if (i!=0) lista.add(aux[0].charAt(i),aux[0].charAt(i-1));
                    }
                    primeiraPalavra = false;
                }
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

        LinkedList<Character> list= lista.positionsWidth();
        for (char c : list){
            System.out.print(c);
        }
    }
    chama a arvore(String significado, char[] letrinhas);

    public static boolean iniciaisCorrespondem(String palavra, char [] letras){
        for(int i =0 ; i<letras.length-1; i++){
            if (palavra.charAt(i)!=letras[i]) return false;
        }
        return true;
    }
}
