package lab3;

public class Flower extends Decoration{
    int spec;
    Flower(){
        double rand = Math.random();
        if (rand<=Main.third){
            spec = 1;
        } else if(rand <= Main.twothirds){
            spec = 2;
        }else{
            spec = 3;
        }
    }
}
