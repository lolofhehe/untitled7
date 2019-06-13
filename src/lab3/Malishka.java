package lab3;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

public class Malishka extends Human implements Musical, Critical, Comparable<Malishka>, Serializable {
    Malishka(){
        super.SetStatsRandom();
        acquireName();
        height = (int) Math.random() * 1000;
        birthday = new Date();
        coordinates = new double[2];
        for (double c: coordinates){
            c = Math.random() * 100;
        }
    }

    private double[] coordinates;
    private Date birthday;
    private int height;
    transient public Harp distributedHarp;
    private String distributedHarpType;
    private String name;
    public boolean isPlaying;
    private String isPlayingKey;

    public void setDistributedHarp(Harp distributedHarp) {
        this.distributedHarp = distributedHarp;
    }

    public void setPlayingByString(String s) {
        if (s.equals(" true")){
            isPlaying = true;
        } else{
            isPlaying = false;
        }
    }


    public void setDistributedHarpType(String distributedHarpType) {
        this.distributedHarpType = distributedHarpType;
    }


    public void setName(String name) {
        this.name = name;
    }



    public String getDistributedHarpType() {
        return distributedHarpType;
    }

    public String getIsPlaying() {
        return Boolean.toString(isPlaying);
    }


    private int hashElem = Integer.parseInt(String.valueOf(Math.random()).substring(3,6));

    public void play(Instrument inst){
        if (inst instanceof Harp) {
            System.out.println("Малышка играет на "+ distributedHarp.type.name + " арфе.");
        }
    }

    public void rate(){
        System.out.println("Малышки оценивают свою работу на 10 из 10 малышек.");
    }

    @Override
    public int hashCode(){
        int num=0;
        if (this.isPlaying == true) {
            num += 2000;
        } else {
            num += 1;
        }
        if (getisQualified()) {
            num += 10000;
            return num + distributedHarp.hashCode();
        } else { return num + hashElem;}

    }

    public String toString(){
        return "Малышка";
    }

    public String getName() {
        return name;
    }

    private void acquireName() {
        byte[] array = new byte[6];
        new Random().nextBytes(array);
        this.name = new String(array, Charset.forName("UTF-8"));

    }

    public boolean equals(Malishka mal){
        return (this.isPlaying==mal.isPlaying & this.getisQualified()== mal.getisQualified() & this.distributedHarp.type == mal.distributedHarp.type);
    }

    @Override
    public int compareTo(Malishka o) {
        return this.name.compareTo(o.getName());  }

    public String toCSV(){
        return getName() + "," + getDistributedHarpType() + ", " + getIsPlaying();
    }
}

