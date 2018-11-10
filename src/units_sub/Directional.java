package units_sub;

import java.util.Random;

public enum Directional {
	UP("u"), DOWN("d"), LEFT("l"), RIGHT("r");
	
	public String stringToDraw;
	public int[] moveValue = new int[2];
	
	Directional(String stringToDraw){
		this.stringToDraw = stringToDraw;;
		switch(stringToDraw){
		case "u":
			this.moveValue[0] = 0; this.moveValue[1] = -1;
			break;
		case "d":
			this.moveValue[0] = 0; this.moveValue[1] = 1;
			break;
		case "l":
			this.moveValue[0] = -1; this.moveValue[1] = 0;
			break;
		case "r":
			this.moveValue[0] = 1; this.moveValue[1] = 0;
			break;
		}
	}
	
	public static Directional getRandomDirectional(){
		Random r = new Random();
		int index = r.nextInt(4);
		switch(index){
		case 0:
			return UP;
		case 1:
			return DOWN;
		case 2:
			return LEFT;
		case 3:
			return RIGHT;
		}
		return null;
	}
	
	public int[] getOppositeMoveValue(){
		int[] opposite = new int[2];
		opposite[0] = -1 * moveValue[0];
		opposite[1] = -1 * moveValue[1];
		return opposite;
	}
}
