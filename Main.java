
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static GeneralTree lista = new GeneralTree();
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        leituraArquivo();
        String letras= "";
        do {
            System.out.println("Digite as letras iniciais: (Entre 2 a 3 letras)");
            letras = in.next();
        }while (letras.length()-1>2 || letras.length()-1<1);
        ArrayList<Palavra> listaPalavra = lista.searchAll(letras);

        for (Palavra palavra : listaPalavra) {
            System.out.println(palavra.getPalavra());
        }

        System.out.println("Escolha a palavra que deseja ver o significado");
        String nome = in.next();
        for (Palavra palavra1 : listaPalavra) {
            if(palavra1.getPalavra().equalsIgnoreCase(nome)){
                System.out.println(palavra1.getSignificado());
            }
        }

    }


    public static void leituraArquivo(){
        String aux[];
        Path path1 = Paths.get("nomes.csv");
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String linha = null;
            while ((linha = reader.readLine()) != null) {
                aux = linha.split(";");
                Palavra p = new Palavra(aux[0],aux[1]);
                lista.addWord(p.getPalavra(),p.getSignificado()); // manda palavra e significado para add na arvore
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }
    }

}
