package model;

public class AreaResultChecker {
    public static boolean getResult(double x, double y, double r) {
            if (x>=0 && y >=0 && y <= 0.5*r - x/2 )
                return true;
            if (x<=0 && y>=0 && x*x + y*y <= r*r)
                return true;
            if (x>=0 && y<=0 && x<=r && y>=-r)
                return true;
            return false;
    }
}
