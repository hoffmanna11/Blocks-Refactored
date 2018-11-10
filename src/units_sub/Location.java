package units_sub;

public class Location {
	int x, y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isOutOfBounds(int rows, int columns){
		if( (x < 0) ||
				(x >= columns) ||
				(y < 0) ||
				(y >= rows)
				){
			return true;
		}
		return false;
	}
	
	public int x() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int y() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString(){
		return "(" + x + "," + y + ")";
	}
}
