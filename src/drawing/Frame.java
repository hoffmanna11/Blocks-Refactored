package drawing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.WindowConstants;

import global.Constants;
import global.Global;
import units_sub.Location;
import units.Block;
import units_sub.Directional;

@SuppressWarnings("serial")
public class Frame extends Component {
	javax.swing.JFrame frame;
	Graphics g;
	
	//public FrameGraphicsOld drawing;
	
	public Frame(){
		frame = new javax.swing.JFrame();
		//drawing = new FrameGraphicsOld();
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(getFrameWidth(), getFrameHeight());
		frame.setVisible(true);
		g = frame.getGraphics();
	}
	
	public void initializeGrid(){
		Global.sleep(100);
		
		for(int i=0; i<Constants.CellGridOptions.rows; i++){
			for(int j=0; j<Constants.CellGridOptions.columns; j++){
				FrameGraphics.drawSquare(this, new Location(j,i), Color.WHITE);
			}
		}
	}
	
	public int getFrameWidth(){
		int totalBorderWidth = (Constants.CellGridOptions.columns + 1) * Constants.FrameOptions.borderWidth;
		int totalBlocksWidth = (Constants.CellGridOptions.columns) * Constants.FrameOptions.scalingFactor;
		return (2 * Constants.FrameOptions.xWhiteSpaceOffset) + totalBorderWidth + totalBlocksWidth;
	}
	
	public int getFrameHeight(){
		int totalBorderHeight = (Constants.CellGridOptions.rows + 1) * Constants.FrameOptions.borderWidth;
		int totalBlocksHeight = (Constants.CellGridOptions.rows) * Constants.FrameOptions.scalingFactor;
		return (int)(1.5 * Constants.FrameOptions.yWhiteSpaceOffset) + totalBorderHeight + totalBlocksHeight;
	}

	public static class FrameGraphics {
		public static void drawPlayer(Frame frame, Location loc, boolean undraw){
			if(undraw){
				drawSquare(frame, loc, Color.white);
			}else{
				drawSquare(frame, loc, Color.red);
			}
			
		}
		
		public static void drawBlock(Frame frame, Block block, boolean undraw){
			Color color;
			if(undraw){
				color = Color.white;
			}else{
				color = block.getColor();
			}
			for(int i=0; i<block.length(); i++){
				drawSquare(frame, new Location(block.loc().x()+i, block.loc().y()), color);
			}
		}
		public static void drawBlock(Frame frame, Block block, Location loc, boolean undraw){
			Color color;
			if(undraw){
				color = Color.white;
			}else{
				color = block.getColor();
			}
			for(int i=0; i<block.length(); i++){
				drawSquare(frame, new Location(loc.x()+i, loc.y()), color);
			}
		}
		
		public static void drawSquare(Frame frame, Location loc, Color color){
			int xInit = getXLoc(frame, loc.x());
			int yInit = getYLoc(frame, loc.y());
			frame.g.setColor(color);
			
			for(int i=0; i<Constants.FrameOptions.scalingFactor; i++){
				frame.g.drawLine(xInit, yInit+i, xInit + Constants.FrameOptions.scalingFactor - 1, yInit+i);;
			}
		}
		
		public static void redrawPlayer(Frame frame, Location oldLoc, Location newLoc){
			drawPlayer(frame, oldLoc, true);
			drawPlayer(frame, newLoc, false);
		}
		
		public static void redrawBlock(Frame frame, Block block, Location oldLoc, Location newLoc){
			drawBlock(frame, block, oldLoc, true);
			drawBlock(frame, block, newLoc, false);
		}
		
		public static void drawDirectional(Frame frame, Location loc, Directional dir){
			
		}
		
		public static int getXLoc(Frame frame, int column){
			return Constants.FrameOptions.xWhiteSpaceOffset + getXUnitOffset(column, Constants.FrameOptions.scalingFactor) + getXBorderOffset(column, Constants.FrameOptions.borderWidth);
		}
		
		public static int getYLoc(Frame frame, int row){
			return Constants.FrameOptions.yWhiteSpaceOffset + getYUnitOffset(row, Constants.FrameOptions.scalingFactor) + getYBorderOffset(row, Constants.FrameOptions.borderWidth);
		}
		
		public static int getXUnitOffset(int column, int scalingFactor){
			return column * scalingFactor;
		}
		
		public static int getYUnitOffset(int row, int scalingFactor){
			return row * scalingFactor;
		}
		
		public static int getXBorderOffset(int column, int borderWidth){ 
			return (column + 1) * borderWidth;
		}
		
		public static int getYBorderOffset(int row, int borderWidth){
			return (row + 1) * borderWidth;
		}
	}


}
