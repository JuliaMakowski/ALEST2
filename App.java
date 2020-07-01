
public class App {
    public static void main(String[] args) {
        
        GeneralTree arv = new GeneralTree();
        arv.add(1, null); // insere a raiz
        arv.add(2,1); // insere 2 como filho de 1
        arv.add(3,1); // insere 3 como filho de 1
        arv.add(4,2); // insere 4 como filho de 2
        arv.add(5,2); // insere 5 como filho de 2
        arv.add(6,2); // insere 6 como filho de 2
        
      System.out.println("Caminhamento em largura:");
      System.out.println(arv.positionsWidth());
        
      System.out.println("Caminhamento pré-fixado:");
      System.out.println(arv.positionsPre());    
        
      System.out.println("Caminhamento pós-fixado:");
      System.out.println(arv.positionsPos()); 
      
      arv.removeBranch(2);
      
      System.out.println("Caminhamento pós-fixado apos remocao do 2:");
      System.out.println(arv.positionsPos());     
    }
}
