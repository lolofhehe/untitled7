package lab3;
import java.lang.reflect.Field;

public class Adventurer{
    protected static int citiesSeen = 0;
    Field knownCoordinate;
    public static class Counter{
        private int talkTimes = 0;
        public void announce(){
            talkTimes++;
            System.out.printf("Городов увидено: %d\nРаз объявлено: %d\n", citiesSeen, talkTimes);
        }
    }
    String[] whereCity = new String[2];
    public void setExist(Space.City city){
        if (Math.random()>Main.twothirds){
            whereCity = null;}
        else{
            whereCity[0]= String.valueOf(city.getCoordinates()[0]);
            whereCity[1]=String.valueOf(city.getCoordinates()[1]);
        }
    }
    public void hear(Instrument inst) {
        Musical musical = new Musical() {
            @Override
            public void play(Instrument inst) {
                if (inst instanceof Fiddle) {
                    System.out.println("До путешественников доносятся звуки скрипки.");
                } else if (inst instanceof Harp) {
                    System.out.println("Я слышу арфу.");
                }
            }
        };
        musical.play(inst);
    }
    public void see(String[] s) throws MyNullPointer{
        System.out.println("Город видно там:");
        System.out.println(s[0]+ " " + s[1]);
        citiesSeen++;
    }
    public void explore(Space s) throws MyFieldException{
        try{
            knownCoordinate = s.getClass().getDeclaredField("xCoordinate");}
        catch (NoSuchFieldException no){
            throw new MyFieldException(no);
        } System.out.println("Путешественник получил знания о координате.");
    }
}
