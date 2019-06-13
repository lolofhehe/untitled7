package lab3;

public class MyFieldException extends NoSuchFieldException{
    MyFieldException(NoSuchFieldException no){
        super();
        initCause(no);
        System.out.println("Путешественник не разобрался и перестал писать.");
    }
}
