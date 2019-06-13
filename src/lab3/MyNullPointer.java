package lab3;

public class MyNullPointer extends NullPointerException {
    MyNullPointer(NullPointerException np){
        super();
        initCause(np);
        System.out.println("Город не нашелся, приключение окончено.");
    }
}

