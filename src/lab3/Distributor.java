package lab3;

public class Distributor {
    public void decorDistribute(Decorator[] decar, Decoration[] krasotarr){
        for (int k=0; k<6; k++){
            decar[k]= new Decorator();
            switch (k){
                case 0:
                    krasotarr[k] = new Floor();
                    break;
                case 1:
                    krasotarr[k] = new Ladder();
                    break;
                case 2:
                    krasotarr[k] = new Chair();
                    break;
                case 3:
                    krasotarr[k] = new Flower();
                    break;
                case 4:
                    krasotarr[k] = new Tree();
                    break;
                case 5:
                    krasotarr[k] = new FlagsNLamps();
                    break;
            }
            decar[k].decorate(decar[k],krasotarr[k]);
            decar[k].play(new Fiddle());
        }
    }
    public static int playCount;
    public void distribute(Malishka[] MalArray, Harp HarpArray[], Decoration[] dec) {
        for (int i = 0; i < MalArray.length; i++) {
            MalArray[i] = new Malishka();
            HarpArray[i] = new Harp();
            MalArray[i].distributedHarp = HarpArray[i];
            if (MalArray[i].getisQualified()) {
                if (i < 0.4*MalArray.length) {
                    MalArray[i].distributedHarp.type = HarpType.SMALL;
                    MalArray[i].isPlaying = true;
                    playCount++;
                    MalArray[i].play(MalArray[i].distributedHarp);
                } else if (i < 0.7*MalArray.length) {
                    MalArray[i].distributedHarp.type = HarpType.MEDIUM;
                    if (dec[2].count > 0) {
                        MalArray[i].isPlaying = true;
                        playCount++;
                        MalArray[i].play(MalArray[i].distributedHarp);
                        dec[2].count--;
                    } else {System.out.println("Нет стула, нет малышки.");}
                } else if (i < MalArray.length-1) {
                    MalArray[i].distributedHarp.type = HarpType.LARGE;
                    if ( dec[0].count > 0) {
                        MalArray[i].isPlaying = true;
                        playCount++;
                        MalArray[i].play(MalArray[i].distributedHarp);
                        dec[0].count--;
                    } else {System.out.println("Малышка не сыграла из-за отстутствия пола.");}
                } else {
                    MalArray[i].distributedHarp.type = HarpType.EXTRA_LARGE;
                    if ( dec[1].count > 0) {
                        MalArray[i].isPlaying = true;
                        playCount++;
                        MalArray[i].play(MalArray[i].distributedHarp);
                        dec[1].count--;
                    } else{System.out.println("Лестницы нет.");}
                }
            } else {System.out.println("Малышка не получила степень игры на арфе.");}
        }
    }

}
