package lab3;

public class MyArrayException extends ArrayIndexOutOfBoundsException{
    MyArrayException(int len){
        if (len<10){
            System.out.printf("Не хватает %d малышек.", 10-len);
        } else if (len>10){
            System.out.printf("Выгоните %d малышку.", len-10);
        }
        printStackTrace();
    }
}
