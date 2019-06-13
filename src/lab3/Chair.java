package lab3;

public class Chair extends Decoration{
    Chair(){
        double rand = Math.random();
        if (rand<=lab3.Main.third){
            System.out.println("Стулья готовятся быть поставленными.");
        } else if(rand <= Main.twothirds){
            System.out.println("Стулья готовятся быть.");
        }else if (rand <=Main.threethirds){
            System.out.println("Стулья готовятся.");
        } else {
            System.out.println("Стулья.");
        }
    }
    private static final int legAmount=4;
    public static void stateFact(){
        System.out.printf("У стула %d ножки", legAmount);
    }
}
