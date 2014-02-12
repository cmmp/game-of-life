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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Implementation of Conway's Game of Life
 * 
 * @author Cássio M. M. Pereira <cassiomartini@gmail.com>
 *
 */
public class GameOfLife extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	// Constants:
	private static final int WINDOW_WIDTH = 700;
	private static final int WINDOW_HEIGHT = 500;
	
	private static final int GRID_WIDTH = WINDOW_WIDTH;
	private static final int GRID_HEIGHT = 400;
	
	private static final int HEIGHT_OFFSET = 30;
	
	private static final int BOX_WIDTH = 10;
	private static final int BOX_HEIGHT = 10;
	
	JLabel seedLabel;
	JTextField txtSeedField;
	JLabel speedLabel;
	JTextField txtSpeedField;
	JPanel mainPanel;
	JPanel btnPanel;
	GridComponent gridComponent;
	JButton btnStart;
	JButton btnPause;
	JButton btnResume;
	JButton btnReset;
	JButton btnRules;
	
	PaintThread paintThread;
	Thread controlThread;
	
	public GameOfLife() {
		
		mainPanel = new JPanel(new BorderLayout());
		
		gridComponent = new GridComponent(WINDOW_WIDTH, GRID_WIDTH, GRID_HEIGHT, HEIGHT_OFFSET, BOX_WIDTH, BOX_HEIGHT); 
		
		btnStart = new JButton("Start");
		btnPause = new JButton("Pause");
		btnResume = new JButton("Resume");
		btnReset = new JButton("Reset");
		seedLabel = new JLabel("Seed: ");
		txtSeedField = new JTextField("1234", 4);
		speedLabel = new JLabel("Speed (0-1.0): ");
		txtSpeedField = new JTextField("0.9", 3);
		btnRules = new JButton("Rules");
		
		btnStart.addActionListener(this);
		btnPause.addActionListener(this);
		btnResume.addActionListener(this);
		btnReset.addActionListener(this);
		btnRules.addActionListener(this);
		
		mainPanel.add(gridComponent, BorderLayout.CENTER);
		
		btnPanel = new JPanel(new FlowLayout());
		
		btnPanel.add(seedLabel);
		btnPanel.add(txtSeedField);
		btnPanel.add(speedLabel);
		btnPanel.add(txtSpeedField);
		btnPanel.add(btnStart);
		btnPanel.add(btnPause);
		btnPanel.add(btnResume);
		btnPanel.add(btnReset);
		btnPanel.add(btnRules);
		
		mainPanel.add(btnPanel, BorderLayout.PAGE_END);
		
		add(mainPanel);
		
	}
	
	
    public static void main( String[] args ) {
    	GameOfLife life = new GameOfLife();
    	life.setTitle("Conway's Game of Life -- Implementation by Cássio M. M. Pereira");
    	life.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	life.setLocation(dim.width/2-life.getSize().width/2, dim.height/2-life.getSize().height/2);
    	life.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	life.setVisible(true);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnStart) {
			this.gridComponent.clearCanvas();
			this.paintThread = new PaintThread(gridComponent);
			this.paintThread.setSeed(Long.parseLong(txtSeedField.getText()));
			this.paintThread.setSpeed(Double.parseDouble(txtSpeedField.getText()));
			this.paintThread.begin();
			this.controlThread = new Thread(this.paintThread);
			this.controlThread.start();
		} else if(e.getSource() == this.btnPause) {
			if(this.controlThread.isAlive())
				this.controlThread.interrupt();
		} else if(e.getSource() == this.btnResume) {
			if(this.controlThread.isAlive())
				this.controlThread.interrupt();
			this.controlThread = new Thread(this.paintThread);
			this.controlThread.start();
		} else if(e.getSource() == this.btnReset) {
			if(this.controlThread.isAlive())
				this.controlThread.interrupt();
			this.gridComponent.reset();
			this.gridComponent.clearCanvas();
		} else if(e.getSource() == this.btnRules) {
			JOptionPane.showMessageDialog(this, "The rules are as follows:\n" + 
												"Each cell has 8 neighbors.\n" +
												"A cell is alive if it is a black square.\n" +
												"Any live cell with less than two live neighbors dies by underpopulation.\n" +
												"Any live cell with two or three live neighbors lives on to the next generation.\n" +
												"Any live cell with more than three live neighbors dies by overpopulation.\n" +
												"Any dead cell with exactly three live neighbors becomes a live cell by reproduction."
												);
		}
	}
}
