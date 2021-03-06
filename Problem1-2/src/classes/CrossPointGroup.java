package classes;

import classes.*;

import java.util.Vector;

public class CrossPointGroup {
    public Vector<CrossPoint> cp;

    public CrossPointGroup(Vector<CrossPoint> cp) {
        this.cp = cp;
    }

    public void Sort() {
        for (int i = 0; i < cp.size() - 1; i++) {
            for (int j = cp.size() - 1; j > i; j--) {
                if ((cp.get(j - 1).CrossingVec.x > cp.get(j).CrossingVec.x)
                        || (cp.get(j - 1).CrossingVec.x == cp.get(j).CrossingVec.x
                                && cp.get(j - 1).CrossingVec.y > cp.get(j).CrossingVec.y)) {
                    var tmp = cp.get(j - 1);
                    cp.set(j - 1, cp.get(j));
                    cp.set(j, tmp);
                }
            }
        }
    }
}