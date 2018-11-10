package units;

import java.util.ArrayList;

import drawing.Frame;
import global.Constants;
import units_sub.Directional;
import units_sub.Location;
import units_sub.Move;

public class Player 
{
	private Location loc;
	private ArrayList<Move> lastMoves;
	private int index;
	
	public Player(Location loc, int index)
	{
		this.index = index;
		this.loc = loc;
		this.lastMoves = new ArrayList<Move>(Constants.PlayerOptions.numLastMoves);
	}
	
	public void addMove(Move move){
		if(lastMoves.size() < Constants.PlayerOptions.numLastMoves)
		{
			lastMoves.add(move);
		}else
		{
			lastMoves.remove(lastMoves.size()-1);
			lastMoves.add(move);
		}
	}
	
	public int move(int moveNo, CellGrid cellGrid, Directional dir, Frame frame)
	{
		// Check if move is out of bounds
		Move move = new Move(loc, dir);
		Location newLoc = move.locAfter();
		if(newLoc.isOutOfBounds(cellGrid.rows, cellGrid.columns))
		{
			return -1;
		}
		
		// Check if player is in the way
		if(cellGrid.getCell(newLoc).hasPlayer())
		{
			return -1;
		}
		
		// Check if a block is in the way
		if(cellGrid.getCell(newLoc).hasBlock())
		{
			Block blockToBeMoved = cellGrid.getCell(newLoc).getBlock();
			// Try to move the block
			if (blockToBeMoved.move(moveNo, this, cellGrid, dir, frame) == 0)
			{
				movePlayersPushingOnBlock(dir, blockToBeMoved, cellGrid, frame);
				return 0;
			}else
			{
				return -1;
			}
		}
		
		// Nothing is in the way, redraw and update player position
		movePlayer(cellGrid, frame, newLoc, dir);
		return 0;
	}
	
	public void movePlayersPushingOnBlock(Directional dir, Block blockToBeMoved, CellGrid cellGrid, Frame frame){
		// Redraw all the players pushing on the block in the correct plane
		Location currentPlayerLoc;
		
		// Base case: move a length one block
		// Get the player location
		int[] oppDir = dir.getOppositeMoveValue();
		currentPlayerLoc = new Location(blockToBeMoved.loc.x() + 2*oppDir[0], blockToBeMoved.loc.y() + 2*oppDir[1]);
		// Get the player
		Player playerToMove = cellGrid.getCell(currentPlayerLoc).getPlayer();
		if(null == playerToMove){
			System.out.println("Null player, exiting");
			System.exit(-1);
		}
		
		for(int i=0; i<blockToBeMoved.length(); i++){
			// If the block is length one, then we'll never get to the next iteration, i.e. we'll never add to the x value (> 0)
			// If the block is length 2+, then we can assume that we're moving the block upwards or downwards, in which case
			// the players moving the block will always be directly below and to the right 0 or more spaces
			
			// Get the player, move the player
			playerToMove = cellGrid.getCell(currentPlayerLoc.x() + i, currentPlayerLoc.y()).getPlayer();
			int newPlayerLocX = playerToMove.loc.x() + dir.moveValue[0];
			int newPlayerLocY = playerToMove.loc.y() + dir.moveValue[1];
			
			playerToMove.movePlayer(cellGrid, frame, new Location(newPlayerLocX, newPlayerLocY), dir);
		}
	}
	
	public void movePlayer(CellGrid cellGrid, Frame frame, Location newLoc, Directional dir){
		//FrameGraphics.redrawPlayer(frame, loc, newLoc);
		if(Constants.RunningOptions.printMoves) 
			System.out.println("P" + this.index() + " moving " + dir.name());
		Frame.FrameGraphics.redrawPlayer(frame, loc, newLoc);
		cellGrid.updatePlayerLoc(this, loc, newLoc);
		loc.setX(newLoc.x()); loc.setY(newLoc.y());
	}
	
	public Location loc(){
		return loc;
	}

	public int index(){
		return index;
	}
}
