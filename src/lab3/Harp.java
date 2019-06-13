package lab3;

public class Harp extends Instrument{

    HarpType type;
    private final int randElem = (int) (System.nanoTime()%1000000);

    public int getRandElem(){
        return randElem;
    }
    public String toString(){
        return "Арфа";
    }
    public boolean equals(Harp harp){
        return this.type==harp.type;
    }
    public void setType(HarpType type) {
        this.type = type;
    }
}
