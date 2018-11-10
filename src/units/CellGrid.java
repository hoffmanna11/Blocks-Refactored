package units;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import global.Constants;
import units_sub.Location;

public class CellGrid {
	int rows; int columns;
	List<ArrayList<Cell>> cells;
	
	public CellGrid(){
		this.rows = Constants.CellGridOptions.rows;
		this.columns = Constants.CellGridOptions.columns;
		this.cells = new ArrayList<ArrayList<Cell>>(rows);
		for(int i=0; i<columns; i++){
			cells.add(new ArrayList<Cell>());
			for(int j=0; j<rows; j++){
				cells.get(i).add(new Cell(i,j));
			}
		}
	}
	
	public CellGrid(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		this.cells = new ArrayList<ArrayList<Cell>>(rows);
		for(int i=0; i<columns; i++){
			cells.add(new ArrayList<Cell>());
			for(int j=0; j<rows; j++){
				cells.get(i).add(new Cell(i,j));
			}
		}
	}
	
	public Location getRandomPlayerLoc(){
		boolean isValid = false;
		Location loc = new Location(0,0);
		Random rand = new Random();
		do{
			loc.setX(rand.nextInt(columns-1));
			loc.setY(rand.nextInt(rows-1));
			if(getCell(loc).isFree()){
				isValid = true;
			}
		}while(isValid == false);
		return loc;
	}
	
	public Location getRandomBlockLoc(int length){
		boolean isValid = false;
		Location loc = new Location(0,0);
		Random rand = new Random();
		do{
			isValid = true;
			loc.setX(rand.nextInt((columns+1)-length));
			loc.setY(rand.nextInt(rows-1));
			for(int i=0; i<length; i++){
				if(!getCell(new Location(loc.x() + i, loc.y())).isFree()){
					isValid = false;
				}
			}
			
		}while(isValid == false);
		return loc;
	}
	
	public Cell getCell(Location loc){
		return cells.get(loc.x()).get(loc.y());
	}
	
	public Cell getCell(int x, int y){
		return cells.get(x).get(y);
	}
	
	public void updatePlayerLoc(Player player, Location oldLoc, Location newLoc){
		getCell(oldLoc).setPlayer(null);
		getCell(newLoc).setPlayer(player);
	}
	
	public void updateBlockLoc(Block block, Location oldLoc, Location newLoc){
		for(int i=0; i<block.length(); i++){
			getCell(new Location(oldLoc.x() + i, oldLoc.y())).setBlock(null);
		}
		for(int i=0; i<block.length(); i++){
			getCell(new Location(newLoc.x() + i, newLoc.y())).setBlock(block);
		}
	}

	public void printCells(){
		for(int i=0; i<rows; i++){
			for(int j=0; j<columns; j++){
				if(getCell(j,i).hasPlayer()){
					System.out.print("P" + getCell(j,i).getPlayer().index() + " ");
				}else if(getCell(j,i).hasBlock()){
					System.out.print("B" + getCell(j,i).getBlock().index() + " ");
				}else{
					System.out.print("-  ");
				}
			}System.out.print("\n");
		}
	}
}
