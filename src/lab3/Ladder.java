package lab3;

public class Ladder extends Decoration{
    private int stepAmount = Integer.parseInt(String.valueOf(Math.random()).substring(3,5));
    public void announceSteps(){
        System.out.printf("У лестницы %f ступеньки.", stepAmount);
    }
}
