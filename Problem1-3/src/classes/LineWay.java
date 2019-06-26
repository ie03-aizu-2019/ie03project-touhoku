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
    	for (var i =0;i<distance.size();i++) {
    		for (var n=0;n<distance.size();n++) {
    			if (i!=n && Double.compare(distance.get(i), distance.get(n))==0) {
    				points.remove(n);
    				crossPoints.remove(n);
    				distance.remove(n);
    			}
    		}
    	}
    }
}