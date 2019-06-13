package lab3;

public abstract class Instrument {
    public Melody tune;
    protected void SetTune(){
        double rand = Math.random();
        if (rand<0.45){
            tune = Melody.HAPPY;
        } else if (rand<0.9){
            tune = Melody.SAD;
        } else {tune = Melody.FUNKY;}
    }
    public int hashcode(){
        return tune.num*21;
    }
    public String toString(){
        return "Некий инструмент";
    }
    public boolean equals(Instrument inst){
        return this.tune==inst.tune;
    }
}
