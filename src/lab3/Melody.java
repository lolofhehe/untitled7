package lab3;

public enum Melody {
    HAPPY("Декоратор сыграл уморительную мелодию.", 100),
    SAD("Декоратор испортил всем настроение, но сыграл хорошо.",200),
    FUNKY("DECORATOR BASS DOLBIT",300);
    String getPlayed;
    int num;
    Melody(String getPlayed, int num){
        this.getPlayed=getPlayed;
        this.num=num;
    }

}
