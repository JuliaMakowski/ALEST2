
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
            System.out.println("Digite as letras iniciais: (Entre 2 a 3 letras)");
            letras = in.next();
        }while (letras.length()-1>2 || letras.length()-1<1);
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
                if (iniciaisCorrespondem(aux[0].toLowerCase(),letraInicial)){
                    lista.addWord(aux[0].toLowerCase(),aux[1]);
                }
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

        System.out.println("Caminhamento em largura:");
        System.out.println(lista.positionsWidth());

        System.out.println("Caminhamento pré-fixado:");
        System.out.println(lista.positionsPre());

        System.out.println("Caminhamento pós-fixado:");
        System.out.println(lista.positionsPos());

        System.out.println("Maior numero de filhos");
        System.out.println(lista.getMaxChildren());
    }
    public static boolean iniciaisCorrespondem(String palavra, char [] letras){
        for(int i =0 ; i<letras.length-1; i++){
            if (palavra.charAt(i)!=letras[i]) return false;
        }
        return true;
    }
}
