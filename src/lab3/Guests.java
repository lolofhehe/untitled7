package lab3;

public class Guests extends Human implements Critical{
    Guests(){
        SetStatsRandom();
    }
    private int hashElem = Integer.parseInt(String.valueOf(Math.random()).substring(2,7));
    public void guestsWait(){
        if(getisQualified()){
            System.out.println("Все гости с качественным нетерпением ждали бала.");
        } else {
            System.out.println("Никто не ждал бала, но вдруг...");
        }
    }
    public void expressSatisfaction(){
        if (Distributor.playCount > 6 & Decorator.decorCount > 1){
            System.out.println("И музыка, и оформление всем понравились.");
        } else if(Distributor.playCount > 6 & Decorator.decorCount <= 1){
            System.out.println("Музыка ничего, но оформление подкачало.");
        } else if (Distributor.playCount <= 6 & Decorator.decorCount > 1){
            System.out.println("Красиво в наушниках.");
        } else{
            System.out.println("Пришли люди на бал, а там армяне в нарды играют.");
        }
    }
    public void rate(){
        System.out.printf("Критик оценил представление на %d из 10 малышек.\n", Distributor.playCount);
    }
    public int hashcode(){
        return hashElem;
    }
    public String  toString(){
        return "Гости";
    }
    public boolean equals(Guests gue){
        return this.getisQualified()==gue.getisQualified();
    }
}
