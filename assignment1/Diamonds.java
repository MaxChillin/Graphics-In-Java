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

public class Diamonds extends Canvas {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private int centerX;
	private int centerY;
	private int squareRadius;
	private int diamondRadius;
	ArrayList<Square> squareList = new ArrayList<>();
	ArrayList<Square> diamondList = new ArrayList<>();
	Graphics gg;
	Graphics2D g;
	Dimension dimension;

	public void paint(Graphics gg) {
		super.paint(gg);
		dimension = getSize();
		centerX = (dimension.width - 1) / 2;
		centerY = (dimension.height - 1) / 2;
		squareRadius = Math.min(dimension.width>>1, dimension.height>>1);
		diamondRadius = (int) ((centerX - squareRadius*Math.cos(Math.PI/4)) - (centerX - squareRadius*Math.cos(3*Math.PI/4)))>>1;
		squareList.clear();
		diamondList.clear();

		for (int i = 0; i < 25; i++) {
			squareList.add(new Square(centerX, centerY, squareRadius));
			squareRadius >>= 1;
		}// end of square for loop
		
		for (int i = 0; i < 25; i++) {
			diamondList.add(new Square(centerX, centerY, diamondRadius, Math.PI / 4));
			diamondRadius >>= 1;
		}// end of diamond for loop

		g = (Graphics2D) gg;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, dimension.width, dimension.height);
		g.setColor(Color.GREEN);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(3));
		
		for (Square square : squareList) {
			g.drawPolygon(square);
		}

		for (Square diamond : diamondList) {
			g.drawPolygon(diamond);
		}

	}// end of paint method

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}// end of getPreferredSize method

	private static void createWindow() {
		Diamonds diamonds = new Diamonds();

		JFrame frame = new JFrame("Diamonds");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(diamonds);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}// end of createWindow method

	public static void main(String[] args) {
		createWindow();
	}// end of main method

}// end of Diamonds class