package lab3;

public class Space {
    public void stateSpace(){
        System.out.println("Действие происходит в космосе.");
    }
    protected double xCoordinate = 26;
    protected double yCoordinate = 12;
    protected void setCoordiantes(){
        class Coordinates{
            double xAdd = Math.random();
            double yAdd = Math.random();
            void addCoords(){
                xCoordinate+=xAdd;
                yCoordinate+=yAdd;
            }
        }
        Coordinates c = new Coordinates();
        c.addCoords();
    }
    public double[] getCoordinates(){
        double position[] = new double[2];
        position[0]= xCoordinate;
        position[1]=yCoordinate;
        return position;

    }
    public class City extends Space{
        City(){
            xCoordinate+=12;
            yCoordinate+=35;
            setCoordiantes();
        }
    }
}