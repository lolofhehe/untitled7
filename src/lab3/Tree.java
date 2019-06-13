package lab3;

public class Tree extends Decoration{
    int breed;
    Tree(){
        double rand = Math.random();
        if (rand<=Main.third){
            breed = 1;
        } else if(rand <= Main.twothirds){
            breed = 2;
        }else{
            breed = 3;
        }
    }
}
