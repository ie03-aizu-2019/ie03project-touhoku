package classes;
import java.util.Vector;

public class RelationPoint {
    public Vec point;
    public Vector<Vec> walkableVec;

    public RelationPoint () {
    	walkableVec = new Vector<Vec>();
    }
}