package classes;

import java.util.Vector;

public class LineWay {
	public Vector<Vector<Integer>> points;
	public Vector<Vector<Integer>> crossPoints;
	public Vector<Double> distance;

	public LineWay () {
		this.points = new Vector<Vector<Integer>>();
		this.crossPoints = new Vector<Vector<Integer>>();
		this.distance = new Vector<Double>();
	}




    public void DeleteDouble () {
    	for (var i=0; i<points.size();i++) {
    		for (var n=0;n<points.size();n++) {
    			if (i!=n && points.get(i).size() == points.get(n).size()) {
    				var same = true;
    				for (var k=0;k<points.get(i).size();k++) {
    					if (points.get(i).get(k) == points.get(n).get(k) && crossPoints.get(i).get(k) == crossPoints.get(n).get(k)) {

    					}else {
    						same = false;
    						break;
    					}
    				}
    				if (same) {
    					distance.set(n, -1.0);
    				}
    			}
    		}
    	}

    	System.out.printf("%s", points.size());
    }
    public void Sort () {
    	for (var i=0;i < distance.size()-1;i++) {
    		for (var j=distance.size()-1;j>i;j--) {
    			if (distance.get(j) < distance.get(j-1)) {
    				var tPoint = new Vector<Integer>(points.get(j));
    				var tCrossPoint = new Vector<Integer>(crossPoints.get(j));
    				var tDis = distance.get(j);
    				points.set(j, points.get(j-1));
    				crossPoints.set(j, crossPoints.get(j-1));
    				distance.set(j, distance.get(j-1));
    				points.set(j-1, tPoint);
    				crossPoints.set(j-1, tCrossPoint);
    				distance.set(j-1, tDis);
    			}
    		}
    	}
    	while (true) {
    		if (distance.get(0) < 0) {
    			distance.remove(0);
    			points.remove(0);
    			crossPoints.remove(0);
    		}else {
    			break;
    		}
    	}
    }
}