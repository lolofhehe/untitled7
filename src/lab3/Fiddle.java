package lab3;

public class Fiddle extends Instrument{

    public Fiddle(){
        super.SetTune();
    }
    public int hashCode(){
        return tune.num*456;
    }
    public String toString(){
        return "Скрипка";
    }
    public boolean equals(Fiddle fi){
        return this.tune==fi.tune;
    }
}
