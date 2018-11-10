package _main;

import drawing.Frame;
import drawing.Frame.FrameGraphics;
import global.Constants;
import global.Global;
import global.Constants.RunningOptions;
import units.Block;
import units.CellGrid;
import units.Player;
import units_sub.Directional;
import units_sub.Location;

public class Instance {
	Frame frame;
	CellGrid cellGrid;
	Player[] players;
	Block[] blocks;
	
	public Instance(){
		frame = new Frame();
		frame.initializeGrid();
		cellGrid = new CellGrid();
		initializePlayers(Constants.UnitOptions.numberOfPlayers, cellGrid);
		initializeBlocks(Constants.UnitOptions.numberOfBlocks, cellGrid);
	}
	
	public Instance(Player[] players, Block[] blocks, int rows, int columns){
		Constants.CellGridOptions.rows = rows;
		Constants.CellGridOptions.columns = rows;
		
		frame = new Frame();
		frame.initializeGrid();
		cellGrid = new CellGrid();
		this.players = players;
		this.blocks = blocks;
		initializePlayers(players, cellGrid);
		initializeBlocks(blocks, cellGrid);
	}
	
	public void run(){
		if(Constants.RunningOptions.printMoves){
			// Print the generated grid state
			cellGrid.printCells(); System.out.println("");
		} 
			
		Global.sleep(1500);
		
		for(int i=0; i<Constants.RunningOptions.cycles; i++){
			if(i % 10000 == 0){
				// Print current cycles run every 10000 cycles
				System.out.println((int)(100 * (double)i / RunningOptions.cycles) + "%: " + i + " / " + RunningOptions.cycles);
			}
			
			if(Constants.RunningOptions.printMoves){
				// Print which move you're on
				System.out.println("Move " + i);
			}
			
			// Move players, passing in which index we're on
			movePlayers(i);
			
			if(Constants.RunningOptions.printMoves){
				// Print out the state of the grid
				cellGrid.printCells(); System.out.print("\n");
			}
		}
	}
	
	public void movePlayers(int currentIndex){
		for(int i=0; i<players.length; i++){
			players[i].move(currentIndex, cellGrid, Directional.getRandomDirectional(), frame);
			Global.sleep(Constants.RunningOptions.delay);
		}
	}
	
	public void initializePlayers(int numPlayers, CellGrid cellGrid){
		players = new Player[numPlayers];
		for(int i=0; i<numPlayers; i++){
			players[i] = new Player(cellGrid.getRandomPlayerLoc(),i+1);
			cellGrid.getCell(players[i].loc()).setPlayer(players[i]);
			FrameGraphics.drawPlayer(frame, players[i].loc(), false);
		}
	}
	
	public void initializePlayers(Player[] players, CellGrid cellGrid){
		for(int i=0; i<players.length; i++){
			cellGrid.getCell(players[i].loc()).setPlayer(players[i]);
			FrameGraphics.drawPlayer(frame, players[i].loc(), false);
		}
	}
	
	public void initializeBlocks(int numBlocks, CellGrid cellGrid){
		blocks = new Block[numBlocks];
		for(int i=0; i<numBlocks; i++){
			int length = Constants.UnitOptions.minBlockLength + (int)Math.round(Math.random() * (Constants.UnitOptions.maxBlockLength - Constants.UnitOptions.minBlockLength));
			blocks[i] = new Block(cellGrid.getRandomBlockLoc(length), length, i+1);
			for(int j=0; j<length; j++){
				cellGrid.getCell(new Location(blocks[i].loc().x()+j,blocks[i].loc().y())).setBlock(blocks[i]);
			}
			FrameGraphics.drawBlock(frame, blocks[i], false);
		}
	}
	
	public void initializeBlocks(Block[] blocks, CellGrid cellGrid){
		for(int i=0; i<blocks.length; i++){
			int length = blocks[i].length();
			for(int j=0; j<length; j++){
				cellGrid.getCell(new Location(blocks[i].loc().x()+j,blocks[i].loc().y())).setBlock(blocks[i]);
			}
			FrameGraphics.drawBlock(frame, blocks[i], false);
		}
	}
	
	public CellGrid cellGrid(){
		return cellGrid;
	}
	
	public Frame frame(){
		return frame;
	}
}
