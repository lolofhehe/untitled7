package lab3;


public class Main {
    public static final double third = 0.33;
    public static final double twothirds = 0.66;
    public static final double threethirds = 0.99;

    public static void main(String args[]) {


        Distributor man = new Distributor();
        Guests guest = new Guests();
        guest.guestsWait();
        Decorator[] DecoratorArray = new Decorator[6];
        Decoration[] DecorationArray = new Decoration[6];
        man.decorDistribute(DecoratorArray, DecorationArray);
        Malishka MalArray[] = new Malishka[10];
        Harp HarpArray[] = new Harp[10];
        try {
            man.distribute(MalArray, HarpArray, DecorationArray);
        } catch (MyArrayException ex) {
            ex.printStackTrace();
        }
        for (Malishka mal: MalArray){
            System.out.println(mal.distributedHarp.hashCode());
        }
        guest.expressSatisfaction();
        guest.rate();
        MalArray[0].rate();
        Space s = new Space();
        s.setCoordiantes();
        s.stateSpace();
        Space.City c = s.new City();
        Adventurer ad = new Adventurer();
        try {
            ad.explore(s);
        } catch (MyFieldException e) {
            e.printStackTrace();
        }

        ad.setExist(c);
        try {
            ad.see(ad.whereCity);
        } catch (NullPointerException nu) {
            throw new MyNullPointer(nu);
        }
        Adventurer.Counter john = new Adventurer.Counter();
        john.announce();
        john.announce();
        ad.hear(HarpArray[0]);
    }
}
