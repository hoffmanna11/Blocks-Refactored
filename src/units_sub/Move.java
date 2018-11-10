package units_sub;

public class Move {
	Location locBefore, locAfter;
	Directional dir;
	public Move(Location locBefore, Directional dir) {
		this.locBefore = locBefore;
		this.dir = dir;
		this.locAfter = new Location(locBefore.x() + dir.moveValue[0], locBefore.y() + dir.moveValue[1]);
	}

	public Location locBefore(){
		return locBefore;
	}
	
	public Location locAfter(){
		return locAfter;
	}
	
	public Directional dir(){
		return dir;
	}
}
