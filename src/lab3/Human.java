package lab3;

abstract class Human {
    private boolean isQualified;
    protected void SetStatsRandom(){
        double rand = Math.random();
        isQualified = rand>=0.1 ? true : false;
    }
    public boolean getisQualified(){
        return isQualified;
    }
    public int hashCode(){
        if (this.isQualified==false){
        return 12345;
        } else {
            return 12346;
        }
    }
    public String toString(){
        return "Человек";
    }
    public boolean equals(Human h){
        return this.hashCode()==h.hashCode();
        }
    }

