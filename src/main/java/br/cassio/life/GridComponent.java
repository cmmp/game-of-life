package br.cassio.life;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

public class GridComponent extends JComponent {
	
	private static final long serialVersionUID = 1L;
	private int gridWidth, gridHeight, heightOffset;
	private int boxWidth, boxHeight;
	private int windowWidth;
	private Random random;
	boolean[][] grid;
	
	public GridComponent(int windowWidth, int gridWidth, int gridHeight, int heightOffset,
			int boxWidth, int boxHeight) {
		this.windowWidth = windowWidth;
		this.gridHeight = gridHeight;
		this.gridWidth = gridWidth;
		this.heightOffset = heightOffset;
		this.boxHeight = boxHeight;
		this.boxWidth = boxWidth;
		this.random = new Random();
		this.grid = new boolean[gridHeight / boxHeight][gridWidth / boxWidth];
	}
	
	public void reset() {
		for(int i = 1; i < grid[0].length - 1; i++)  // columns
			for(int j = 1; j < grid.length - 1; j++)  // rows
				grid[j][i] = false;
	}
		
	public void clearCanvas() {
		Graphics g = getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect((this.windowWidth - this.gridWidth) / 2, this.heightOffset, this.gridWidth, this.gridHeight);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((this.windowWidth - this.gridWidth) / 2, this.heightOffset, this.gridWidth, this.gridHeight);
	}

	public void begin(long seed) {		
		random.setSeed(seed);
		for(int i = 1; i < grid[0].length - 1; i++)  // columns
			for(int j = 1; j < grid.length - 1; j++)  // rows
				grid[j][i] = random.nextBoolean();
		paintGrid();
	}
	
	public void paintGrid() {
		clearCanvas();
		Graphics g = getGraphics();
		//System.out.println("grid cells: " + grid.length + " " + grid[0].length);
		g.setColor(Color.BLACK);
		
		for(int i = 1; i < grid[0].length - 1; i++)  // columns
			for(int j = 1; j < grid.length - 1; j++) // rows
				if(grid[j][i])
					g.fillRect(i * boxWidth, heightOffset + j * boxHeight, boxWidth, boxHeight);
	}
	
	private int getNumberOfNeighbors(int i, int j) {
		int count = 0;
		if(grid[i-1][j]) count++; // upper
		if(grid[i-1][j-1]) count++; // upper left
		if(grid[i-1][j+1]) count++; // upper right
		if(grid[i][j-1]) count++; // left
		if(grid[i][j+1]) count++; // right
		if(grid[i+1][j]) count++; // lower
		if(grid[i+1][j-1]) count++; // lower left
		if(grid[i+1][j+1]) count++; // lower right
		return count;
	}

	public void step() {
		boolean[][] newgrid = new boolean[grid.length][grid[0].length];
		int n;
		
		// take one step in the game:
		for(int i = 1; i < grid[0].length - 1; i++)  { // columns
			for(int j = 1; j < grid.length - 1; j++) { // rows
				n = getNumberOfNeighbors(j, i);
				if(grid[j][i] && n < 2)
					newgrid[j][i] = false; // die by under-population
				else if(grid[j][i] && n <= 3)
					newgrid[j][i] = true; // live
				else if(grid[j][i] && n > 3) // die by overcrowding
					newgrid[j][i] = false;
				else if(!grid[j][i] && n == 3) // live by reproduction
					newgrid[j][i] = true;
			}
		}

		this.grid = newgrid;
		
		// paint the screen:
		paintGrid();
	}

}
