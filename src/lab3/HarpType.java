package lab3;

public enum HarpType {
    SMALL(100, "SMALL"),
    MEDIUM(200, "MEDIUM"),
    LARGE(300,"LARGE"),
    EXTRA_LARGE(400, "EXTRA_LARGE");
    HarpType(int num, String size){
        this.num=num;
        this.name =size;
    }
    int num;
    String name;

    public static HarpType matchByName(String s){
        HarpType theOne = null;
        HarpType[] compArray = HarpType.values();
        for (HarpType h: compArray){
            if (h.toString().equals(s)){
                theOne = h;
                break;
            }
        }
        return theOne;
    }
}

