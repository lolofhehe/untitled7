package lab3;

public class FlagsNLamps extends Decoration{
    int shape;
    FlagsNLamps(){
        double rand = Math.random();
        if (rand<=lab3.Main.third){
            shape = 1;
        } else if(rand <= Main.twothirds){
            shape = 2;
        }else if (rand <=Main.threethirds){
            shape = 3;
        } else {
            shape = 4;
        }
    }
}
