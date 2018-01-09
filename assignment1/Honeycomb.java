package assignment1;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Honeycomb extends Canvas {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private int radius;
	private int topBuffer;
	private int sideBuffer;
	ArrayList<Hexagon> list = new ArrayList<>();
	Graphics gg;
	Graphics2D g;
	Dimension dimension;

	public void paint(Graphics gg) {
		super.paint(gg);
		dimension = getSize();
		radius = Math.min((dimension.width - 1) / 20, (dimension.height - 1) / 20);
		topBuffer = (int) ((dimension.height) % (radius * Math.sin(Math.PI / 3)));
		sideBuffer = (int) ((dimension.width - 2 * radius) % (2 * (radius * Math.cos(Math.PI / 3) + radius * Math.cos(0))))>>1;
		list.clear();

		for (int centerY = radius + topBuffer, row = 0; centerY < dimension.height - radius - topBuffer; centerY += radius
				* Math.sin(Math.PI / 3), row++) {
			for (int centerX = radius + sideBuffer; centerX < dimension.width - radius; centerX += 2
					* (radius * Math.cos(Math.PI / 3) + radius * Math.cos(0))) {
				if (row % 2 != 0 && centerX == radius + sideBuffer) {
					centerX += (radius * Math.cos(Math.PI / 3) + radius * Math.cos(0));
				}
				list.add(new Hexagon(centerX, centerY, radius));

			} // end of width loop
		} // end of height loop

		g = (Graphics2D) gg;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, dimension.width, dimension.height);
		g.setColor(Color.GREEN);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(3));
		for (Hexagon hexagon : list) {
			g.drawPolygon(hexagon);
		}

	}// end of paintComponent method

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	private static void createWindow() {
		Honeycomb honeycomb = new Honeycomb();

		JFrame frame = new JFrame("Honeycomb");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(honeycomb);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}// end of createWindow method

	public static void main(String[] args) {
		createWindow();
	}// end of main method
	
}// end of Honeycomb class