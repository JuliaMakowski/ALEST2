
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.LinkedList;

import java.util.Scanner;

public class Main {
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        String letras= "";
        do {
            System.out.println("Digite as letras iniciais: (Não maior que 3 letras)");
            letras = in.next();
        }while (letras.length()-1>2);
        arvore(letras);
    }

    public static void arvore(String letras){
        LinkedList<Palavra> dicionario = leituraArquivo();
        GeneralTree tree = new GeneralTree();
        char[] letra = letras.toCharArray();


    }

    public static LinkedList<Palavra> leituraArquivo(){
        LinkedList<Palavra> lista = new LinkedList<>();
        String[] aux;

        Path path1 = Paths.get("nomes.csv");

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                aux = line.split(";");
                Palavra p = new Palavra(aux[0],aux[1]);
                lista.add(p);
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }
        return lista;
    }
}
