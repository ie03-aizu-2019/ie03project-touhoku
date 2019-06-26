package classes;

import java.util.Vector;

public class LineGroup {
    private Vector<Line> lines;
    private Vector<CrossPoint> crossPoints;

    //walk
    private Vec walk_nextPoint;
    private boolean walk_canGo;
    private Vector<Integer> walk_walkedPoint;
    private Vector<Integer> walk_walkedCrossPoint;

    public LineWay lineWay;

    //result

    public LineGroup(Vector<Line> lines,Vector<CrossPoint> crossPoints) {
        this.lines = lines;
        this.crossPoints = crossPoints;
        walk_walkedPoint = new Vector<Integer>();
        walk_walkedCrossPoint = new Vector<Integer>();
        lineWay = new LineWay();
    }

    public LineGroup(Vector<Line> lines,Vector<CrossPoint> crossPoints,
    		Vec walk_nextPoint, boolean walk_canGo, Vector<Integer> walk_walkedPoint, Vector<Integer> walk_walkedCrossPoint) {
        this.lines = lines;
        this.crossPoints = crossPoints;
        this.walk_nextPoint = walk_nextPoint;
        this.walk_canGo = walk_canGo;
        this.walk_walkedPoint = (Vector<Integer>)walk_walkedPoint.clone();
        this.walk_walkedCrossPoint = (Vector<Integer>)walk_walkedCrossPoint.clone();
        lineWay = new LineWay();
    }

    public LineWay WalkLine(int startPointId,int endPointId,int depth) {
    	if (depth > 3) {
    		return null;
    	}
    	if (walk_nextPoint == null) {
    		walk_walkedPoint.add(startPointId);
    		walk_walkedCrossPoint.add(-1);
    		walk_nextPoint = VecByPointId(startPointId);
    	}else {
    		if (IsPoint(walk_nextPoint)) {
    			walk_walkedPoint.add(IdByPointVec(walk_nextPoint));
    			walk_walkedCrossPoint.add(-1);
    		}else {
    			walk_walkedPoint.add(-1);
    			walk_walkedCrossPoint.add(IdByCrossPointVec(walk_nextPoint));
    		}
    	}

    	if (walk_walkedPoint.size() == 2 && walk_walkedPoint.get(0) == 4 && walk_walkedPoint.get(1) == 5) {
    		System.out.print("");
    	}

    	//check finish point
    	if (walk_nextPoint == VecByPointId(endPointId)) {
    		walk_canGo = true;
    		double dis = 0;
    		Vec point = null;

    		for (var i=0;i<walk_walkedPoint.size(); i++) {
    			if (walk_walkedPoint.get(i) != -1) {
    				var pointVec = VecByPointId(walk_walkedPoint.get(i));
    				if (point != null) {
    					var cal = new Line(point, pointVec);
    					dis += cal.CalDistance();
    				}
    				point = pointVec;
    				//System.out.printf("%d ", walk_walkedPoint.get(i));
    			} else if (walk_walkedCrossPoint.get(i) != -1) {
    				var pointVec = VecByCrossPointId(walk_walkedCrossPoint.get(i));
    				if (point != null) {
    					var cal = new Line(point, pointVec);
    					dis += cal.CalDistance();
    				}
    				point = pointVec;
    				//System.out.printf("C%d ", walk_walkedCrossPoint.get(i));
    			}
    		}

    		//System.out.printf("done\n%f\n",dis);
    		var lineWay = new LineWay();
    		lineWay.points.add(walk_walkedPoint);
    		lineWay.crossPoints.add(walk_walkedCrossPoint);
    		lineWay.distance.add(dis);
    		return lineWay;
    	}

    	for (var i=0;i<lines.size();i++) {
    		if (lines.get(i).P1 == walk_nextPoint) {
    			if (CheckWalked(lines.get(i).Id_P2,-1)) {
    				walk_nextPoint = lines.get(i).P2;
    				WalkLineBranch(startPointId, endPointId,depth+1);
    			}
    		}
    		if (lines.get(i).P2 == walk_nextPoint) {
    			if (CheckWalked(lines.get(i).Id_P1,-1)) {
    				walk_nextPoint = lines.get(i).P1;
    				WalkLineBranch(startPointId, endPointId,depth+1);
    			}
    		}
    	}
    	for (var i=0;i<crossPoints.size();i++) {
    		if (crossPoints.get(i).line1.P1 == walk_nextPoint || crossPoints.get(i).line1.P2 == walk_nextPoint || crossPoints.get(i).line2.P1 == walk_nextPoint || crossPoints.get(i).line2.P2 == walk_nextPoint) {
    			if (CheckWalked(-1,i)) {
    				walk_nextPoint = crossPoints.get(i).CrossingVec;
    				WalkLineBranch(startPointId, endPointId,depth+1);
    			}
    		}



    		if (walk_nextPoint == crossPoints.get(i).CrossingVec) {
    			//to point
    			if (CheckWalked(crossPoints.get(i).line1.Id_P1,-1)) {
    				walk_nextPoint = crossPoints.get(i).line1.P1;
    				WalkLineBranch(startPointId, endPointId,depth+1);
    			}
    			if (CheckWalked(crossPoints.get(i).line1.Id_P2,-1)) {
    				walk_nextPoint = crossPoints.get(i).line1.P2;
    				WalkLineBranch(startPointId, endPointId,depth+1);
    			}
    			if (CheckWalked(crossPoints.get(i).line2.Id_P1,-1)) {
    				walk_nextPoint = crossPoints.get(i).line2.P1;
    				WalkLineBranch(startPointId, endPointId,depth+1);
    				}
    			if (CheckWalked(crossPoints.get(i).line2.Id_P2,-1)) {
    				walk_nextPoint = crossPoints.get(i).line2.P2;
    				WalkLineBranch(startPointId, endPointId,depth+1);
    			}

    			//to cross point


    		}
    	}
    	return lineWay;
    }

    private void WalkLineBranch (int startPointId, int endPointId,int depth) {
    	var branchClass = new LineGroup(this.lines,this.crossPoints,this.walk_nextPoint, this.walk_canGo, this.walk_walkedPoint, this.walk_walkedCrossPoint);
		var branchReturn = branchClass.WalkLine(startPointId, endPointId, depth+1);
		if (branchReturn != null)  {
		for(var n=0;n<branchReturn.points.size();n++) {
			lineWay.points.add(branchReturn.points.get(n));
			lineWay.crossPoints.add(branchReturn.crossPoints.get(n));
			lineWay.distance.add(branchReturn.distance.get(n));
		}
		}
    }

	private boolean CheckWalked (int pointID, int crossPointID) {
    	if (pointID != -1) {
    		for (var i=0;i<walk_walkedPoint.size();i++) {
    			if (walk_walkedPoint.get(i) == pointID) {
    				return false;
    			}
    		}
    	}
    	if (crossPointID != -1) {
    		for (var i=0;i<walk_walkedCrossPoint.size();i++) {
    			if (walk_walkedCrossPoint.get(i) == crossPointID) {
    				return false;
    			}
    		}
    	}
    	return true;
    }

    public Vec VecByPointId (int getId) {
    	for (int i=0;i<lines.size();i++) {
    		if (lines.get(i).Id_P1 == getId) {
    			return lines.get(i).P1;
    		}
    		if (lines.get(i).Id_P2 == getId) {
    			return lines.get(i).P2;
    		}
    	}
    	return null;
    }
    public Vec VecByCrossPointId (int getId) {
    	for (int i=0;i<crossPoints.size();i++) {
    		if (crossPoints.get(i).CrossingVecId == getId) {
    			return crossPoints.get(i).CrossingVec;
    		}
    	}
    	return null;
    }

    public int IdByPointVec (Vec vec) {
    	for (int i=0;i<lines.size();i++) {
    		if (lines.get(i).P1 == vec) {
    			return lines.get(i).Id_P1;
    		}
    		if (lines.get(i).P2 == vec) {
    			return lines.get(i).Id_P2;
    		}
    	}
    	return -1;
    }
    public int IdByCrossPointVec (Vec vec) {
    	for (int i=0;i<crossPoints.size();i++) {
    		if (crossPoints.get(i).CrossingVec == vec) {
    			return crossPoints.get(i).CrossingVecId;
    		}
    	}
    	return -1;
    }

    public boolean IsPoint(Vec getVec) { // or crossPoint
    	for (var i=0; i<lines.size();i++) {
    		if (lines.get(i).P1 == getVec) {
    			return true;
    		}else if (lines.get(i).P2 == getVec) {
    			return true;
    		}
    	}
    	return false;
    }
}