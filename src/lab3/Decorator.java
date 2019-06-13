package lab3;

public class Decorator extends Human implements Musical{
    private boolean isCreative;
    public static int decorCount;
    @Override
    protected void SetStatsRandom() {
        super.SetStatsRandom();
        double rand2 = Math.random();
        if (rand2 <= 0.2) {
            isCreative = true;

        }
    }
    public void play(Instrument inst){
        if (isCreative==true){
            System.out.println(inst.tune.getPlayed);
        }
    }
    Decorator() {
        SetStatsRandom();
    }

    public void decorate(Decorator guy, Decoration dec) {
        if (guy.getisQualified()) {
            double n = 1.0;
            dec.isDecorated = true;
            dec.count++;
            while (n > 0) {
                dec.count++;
                n = n - Math.random();
            }
            if (dec instanceof Ladder) {
                System.out.print("Декоратор поставил лестницу");
            } else if (dec instanceof Floor) {
                System.out.print("Декоратор материализовал пол");
            } else if (dec instanceof Chair) {
                System.out.print("Есть два стула, и декоратор их поставил");
            } else if (dec instanceof Flower) {
                System.out.print("Декоратор посадил ");
                switch (((Flower) dec).spec) {
                    case 1:
                        System.out.print("ромашки");
                        break;
                    case 2:
                        System.out.print("розы");
                        break;
                    case 3:
                        System.out.print("тюльпаны");
                        break;
                }
                decorCount++;
            } else if (dec instanceof Tree) {
                System.out.print("Декоратор посадил ");
                switch (((Tree) dec).breed) {
                    case 1:
                        System.out.print("березу");
                        break;
                    case 2:
                        System.out.print("елку");
                        break;
                    case 3:
                        System.out.print("осину");
                        break;
                }
                if (dec.count >= 3) {
                    System.out.print(" с качествами елки");
                }
                decorCount++;
            } else if (dec instanceof FlagsNLamps) {
                System.out.print("Декоратор повесил");
                switch (((FlagsNLamps) dec).shape) {
                    case 1:
                        System.out.print(" квадратные флажки и не менее квадратные лампы");
                        break;
                    case 2:
                        System.out.print(" треугольные флажки и светящиеся конусы");
                        break;
                    case 3:
                        System.out.print(" круг и его друг");
                        break;
                    case 4:
                        System.out.print("ся");
                        break;
                }
                decorCount++;
            }
        } else {
            System.out.print("Декоратор ничего не сделал");
        }
        if (guy.isCreative) {
            System.out.print(", но сделал это изящно");
        }
        System.out.print(".\n");
    }

    public int hashCode() {
        int num = 0;
        if (isCreative == true) {
            num += 2000;
        } else{num+=2100;}
        if(getisQualified()){
            num+=20000;
        }else{num+=453;}
            return num;
    }
    public String toString(){
        return "Декоратор";
    }
    public boolean equals(Decorator dec){
        return (this.getisQualified()==dec.getisQualified() & this.isCreative==dec.isCreative);
    }
}
