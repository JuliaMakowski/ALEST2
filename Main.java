
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
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
        String[] aux;
        Path path1 = Paths.get("nomes.csv");
        boolean primeiraPalavra =true;

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                aux = line.split(";");
                lista.addWord(aux[0],aux[1]);
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

//        System.out.println("Caminhamento em largura:");
//        System.out.println(lista.positionsWidth());
//
//        System.out.println("Caminhamento pré-fixado:");
//        System.out.println(lista.positionsPre());
//
//        System.out.println("Caminhamento pós-fixado:");
//        System.out.println(lista.positionsPos());

        ArrayList<Palavra> word = lista.searchAll(letras);

        for (Palavra p: word){
            System.out.println(p.getPalavra());
        }
    }
}
