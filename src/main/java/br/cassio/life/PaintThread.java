package br.cassio.life;

public class PaintThread implements Runnable {
	
	private GridComponent gridComp;
	private long seed;
	private double speed;
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public PaintThread(GridComponent grid) {
		this.gridComp = grid;
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			try {
				gridComp.step();
				Thread.sleep(1000 - (int)(900 * speed));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public void begin() {
		// first fill the points randomly
		gridComp.begin(seed);
	}	
	

}
