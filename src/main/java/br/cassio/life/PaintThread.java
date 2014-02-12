/*  Copyright (C) 2014  Cássio M. M. Pereira

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
