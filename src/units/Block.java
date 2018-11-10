package units;

import java.awt.Color;
import java.util.Arrays;

import drawing.Frame;
import drawing.Frame.FrameGraphics;
import global.Constants;
import units_sub.Directional;
import units_sub.Location;
import units_sub.Move;

public class Block {
	int index;
	Location loc;
	int length;
	boolean[] moveUpList;
	boolean[] moveDownList;
	int currentMoveUpValue = 0;
	int currentMoveDownValue = 0;

	public Block(Location loc, int length, int index){
		this.index = index;
		this.loc = loc;
		this.length = length;
		this.moveUpList = new boolean[length];
		this.moveDownList = new boolean[length];
	}

	public int move(int moveNo, Player player, CellGrid cellGrid, Directional dir, Frame frame){
		// Check if move is out of bounds
		Move move = new Move(loc, dir);
		if(move.locAfter().isOutOfBounds(cellGrid.rows, cellGrid.columns))
		{
			if(Constants.RunningOptions.printMoves) 
				System.out.println("P" + player.index() + " moving block " + dir.name() + " attempt: out of bounds");
			return -1;
		}

		// Check if player is in the way
		if(cellGrid.getCell(move.locAfter()).hasPlayer())
		{
			if(Constants.RunningOptions.printMoves) 
				System.out.println("P" + player.index() + " moving block " + dir.name() + " attempt: player in the way");
			return -1;
		}

		if(cellGrid.getCell(move.locAfter()).hasBlock())
		{
			if(Constants.RunningOptions.printMoves) 
				System.out.println("P" + player.index() + " moving block " + dir.name() + " attempt: has block");
			return -1;
		}
		
		if(length == 1){
			moveBlock(cellGrid, frame, move.locAfter(), dir);
			return 0;
		}
		
		// Moving a length 2+ block left or right isn't allowed
		if(dir.equals(Directional.LEFT) || dir.equals(Directional.RIGHT)){
			return -1;
		}
		
		// If the move value isn't current
		if(!moveValueIsCurrent(moveNo, dir)){
			if(Constants.RunningOptions.printMoves) 
				System.out.println("Move value isn't current, actual moveValue: " + moveNo + ", stored moveValue: " + getCurrentMoveValue(dir) + ", moveList: " + Arrays.toString(getMoveList(dir)));
			
			// Set all move list values to false, except the current move
			resetMoveList(dir);
			setPlayerMoveListValue(player);
			setMoveDirValue(moveNo, dir);
			// Since the block is length 2+, it can't be moved by one player, so return
			return -1;
		}
		
		// Set the players moveList value
		setPlayerMoveListValue(player);
		
		// Need to check if there are enough players to move the block
		if(enoughPlayersToMoveBlock(dir)){
			// Move the block
			if(Constants.RunningOptions.printMoves) 
				System.out.println("Enough players!");
			moveBlock(cellGrid, frame, move.locAfter(), dir);
			return 0;
		}else{
			if(Constants.RunningOptions.printMoves) 
				System.out.println("Not enough players!");
			//setPlayerMoveListValue(player);
			return -1;
		}
		
		// Nothing is in the way, redraw block and update position
		// moveBlock(cellGrid, frame, move.locAfter(), dir);
		// return 0;
	}
	
	public void moveBlock(CellGrid cellGrid, Frame frame, Location newLoc, Directional dir){
		if(Constants.RunningOptions.printMoves) 
			System.out.println("B" + this.index() + " moving " + dir.name());
		FrameGraphics.redrawBlock(frame, this, loc, newLoc);
		cellGrid.updateBlockLoc(this, loc, newLoc);
		loc.setX(newLoc.x()); loc.setY(newLoc.y());
	}
	
	public boolean moveValueIsCurrent(int moveNo, Directional dir){
		switch(dir){
		case UP:
			if(currentMoveUpValue == moveNo) return true;
			return false;
		case DOWN:
			if(currentMoveDownValue == moveNo) return true;
			return false;
		default:
			System.err.println("Error, invalid directional, exiting [units:Block:moveValueIsCurrent]");
			System.exit(-1);
			return false;
		}
	}
	
	public boolean enoughPlayersToMoveBlock(Directional dir){
		boolean[] moveList = null;
		if(dir.equals(Directional.DOWN)){
			moveList = moveDownList;
		}else if (dir.equals(Directional.UP)){
			moveList = moveUpList;
		}else{
			System.err.println("Error, invalid directional for moving length 2+ block, exiting [units:Block:enoughPlayersToMoveBlock]");
			System.exit(-1);
		}
		
		for(int i=0; i<length; i++){
			if(moveList[i] == false){
				return false;
			}
		}
		return true;
	}
	
	public int getCurrentMoveValue(Directional dir){
		if(dir.equals(Directional.UP)){
			return currentMoveUpValue;
		}else if(dir.equals(Directional.DOWN)){
			return currentMoveDownValue;
		}else{
			System.out.println("Error, invalid directional, exiting [units:Block:getCurrentMoveValue]");
			System.exit(-1);
			return -1;
		}
	}
	
	public void setPlayerMoveListValue(Player player){
		int index = getMoveListIndex(player);
		// If player is above the block moving downwards, modify the moveDownList
		// (remember smaller y values mean visually higher locations)
		if(player.loc().y() < loc.y()){
			moveDownList[index] = true;
		}else{
			// else modify the moveUpList
			moveUpList[index] = true;
		}
	}
	
	public int getMoveListIndex(Player player){
		return loc.x() + (length - 1) - player.loc().x();
	}

	public boolean[] getMoveList(Directional dir){
		switch(dir){
		case UP:
			return moveUpList;
		case DOWN:
			return moveDownList;
		default:
			System.err.println("Error, invalid directional, exiting [units:Block:getMoveList]");
			System.exit(-1);
			return null;
		}
	}
	
	public void resetMoveList(Directional dir){
		boolean[] moveList = null;
		if(dir.equals(Directional.UP)){
			moveList = moveUpList;
		}else if(dir.equals(Directional.DOWN)){
			moveList = moveDownList;
		}else{
			System.out.println("Error, invalid directional, exiting [units:Block:resetMoveList]");
			System.exit(-1);
		}
		for(int i=0; i<length; i++){
			moveList[i] = false;
		}
	}
	
	public void setMoveDirValue(int moveNo, Directional dir){
		if(dir.equals(Directional.UP)){
			currentMoveUpValue = moveNo;
		}else if(dir.equals(Directional.DOWN)){
			currentMoveDownValue = moveNo;
		}else{
			System.out.println("Error, invalid directional, exiting [units:Block:setMoveValues]");
			System.exit(-1);
		}
	}

	public Location loc(){
		return loc;
	}

	public int length(){
		return length;
	}

	public int index(){
		return index;
	}

	public Color getColor(){
		int len = length - 1;
		switch(len % 9){
		case 0:
			return Color.cyan;
		case 1:
			return Color.blue;
		case 2:
			return Color.green;
		case 3:
			return Color.darkGray;
		case 4:
			return Color.lightGray;
		case 5:
			return Color.gray;
		case 6:
			return Color.magenta;
		case 7:
			return Color.orange;
		case 8:
			return Color.pink;
		case 9:
			return Color.yellow;
		}
		return Color.cyan;
	}
}
