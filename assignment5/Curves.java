package assignment5;

//****************************************
// I have implemented the bonus part
//****************************************

import java.awt.BasicStroke;

// FractalGrammars.java

// Copied from Section 8.3 of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Curves extends Frame {

	int height = 1080;
	int width = height * 16 / 9;

	public static void main(String[] args) {
		if (args.length == 0)
			System.out.println("Use filename as program argument.");
		else
			new Curves(args[0]);
	}

	Curves(String fileName) {
		super("Click left or right mouse button to change the level");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(width, height);
		setBackground(Color.BLACK);
		this.setLocationRelativeTo(null);
		add("Center", new JeremysAmazingSubClassForAssignment5(fileName));
		setVisible(true);
	}
}


//**********************************************************
//                        Sub Class
//**********************************************************
class JeremysAmazingSubClassForAssignment5 extends Canvas {
	String fileName, axiom, strF, strf, strX, strY;
	int maxX, maxY, level = 1;
	double xLast, yLast, dir, rotation, dirStart, fxStart, fyStart, lengthFract, reductFact, PI = Math.PI, oldDir = 0.0;
	Graphics g;
	Graphics2D g2d;
	ArrayList<Point> points = new ArrayList<>();
	int startingPointOfAngle = 0;
	int theta = 0;
	int xOffset = 0;
	int yOffset = 0;
	int leafWidth = 0, leafHeight = 0, leafXOffset = 0, leafYOffset = 0;

	void error(String str) {
		System.out.println(str);
		System.exit(1);
	}

	JeremysAmazingSubClassForAssignment5(String fileName) {

		final File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("file does not exists");
			System.exit(2);
		}

		/*
		 * This try block creates a scanner object that will go through our file line by
		 * line.
		 */
		try (Scanner scanner = new Scanner(file)) {
			/*
			 * THis while loop will check to see if there's a line in the source file
			 * available for scanning. As long as the text file isn't finished being read,
			 * this while loop will progress.
			 */
			while (scanner.hasNextLine()) {
				axiom = scanner.nextLine(); 		// "X"
				strF = scanner.nextLine();			// "F"
				strf = scanner.nextLine();			// ""
				strX = scanner.nextLine();			// "X+YF+"
				strY = scanner.nextLine();			// "-FX-Y"
				rotation = scanner.nextDouble();	// 09
				dirStart = scanner.nextDouble();	// 0
				fxStart = scanner.nextDouble();		// 0.5
				fyStart = scanner.nextDouble();		// 0.5
				lengthFract = scanner.nextDouble();	// 0.6
				reductFact = scanner.nextDouble();	// 0.6

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if ((evt.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
					level--; // Right mouse button decreases level
					points.clear();
					if (level < 1)
						level = 1;
				} else {
					points.clear();
					level++; // Left mouse button increases level
				}
				repaint();
			}
		});

	}

	int iX(double x) {
		return (int) Math.round(x);
	}

	int iY(double y) {
		return (int) Math.round(maxY - y);
	}

	void drawTo(Graphics g, ArrayList<Point> list) {
		g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(3));
		int radius = iX(list.get(0).distance(list.get(1)));

		for(int i = 0; i < list.size()-1; i++) {
			g2d.setColor(new Color(0, 255, 255));
			startingPointOfAngle = 0;
			theta = 0;
			xOffset = 0;
			yOffset = 0;

			if(i < list.size() - 2) {
				if(list.get(i).x == list.get(i+1).x) {
					if(list.get(i).y > list.get(i+2).y) {
						if(list.get(i+1).x > list.get(i+2).x) {
							startingPointOfAngle = 0;
							theta = 90;
							xOffset = -radius;
							yOffset = -radius>>1;
							leafWidth = radius>>3;
							leafHeight = radius>>2;
							leafXOffset = -radius>>4;
							leafYOffset = 0;
						}else if(list.get(i+1).x < list.get(i+2).x) {
							startingPointOfAngle = 90;
							theta = 90;
							xOffset = 0;
							yOffset = -radius>>1;
							leafWidth = radius>>3;
							leafHeight = radius>>2;
							leafXOffset = -radius>>4;
							leafYOffset = 0;
						}// end of if/else
					}else if(list.get(i).y < list.get(i+2).y) {
						if(list.get(i+1).x > list.get(i+2).x) {
							startingPointOfAngle = 0;
							theta = -90;
							xOffset = -radius;
							yOffset = -radius>>1;
							leafWidth = radius>>3;
							leafHeight = radius>>2;
							leafXOffset = -radius>>4;
							leafYOffset = 0;
						}else if(list.get(i+1).x < list.get(i+2).x) {
							startingPointOfAngle = -90;
							theta = -90;
							xOffset = 0;
							yOffset = -radius>>1;
							leafWidth = radius>>2;
							leafHeight = radius>>3;
							leafXOffset = 0;
							leafYOffset = 0;
						}// end of if/else
					}// end of if/else
				}// end of if(list.get(i).x == list.get(i+1).x)

				if(list.get(i).y == list.get(i+1).y) {
					if(list.get(i).x > list.get(i+2).x) {
						if(list.get(i+1).y > list.get(i+2).y) {
							startingPointOfAngle = -90;
							theta = -90;
							xOffset = -radius>>1;
							yOffset = -radius;
							leafWidth = radius>>2;
							leafHeight = radius>>3;
							leafXOffset = radius;
							leafXOffset = 0;
							leafYOffset = -radius>>4;
						}else if(list.get(i+1).y < list.get(i+2).y) {
							startingPointOfAngle = 90;
							theta = 90;
							xOffset = -radius>>1;
							yOffset = 0;
							leafWidth = radius>>2;
							leafHeight = radius>>3;
							leafXOffset = 0;
							leafYOffset = -radius>>4;
						}// end of if/else
					}else if(list.get(i).x < list.get(i+2).x) {
						if(list.get(i+1).y > list.get(i+2).y) {
							startingPointOfAngle = 0;
							theta = -90;
							xOffset = -radius>>1;
							yOffset = -radius;
							leafWidth = radius>>2;
							leafHeight = radius>>3;
							leafXOffset = 0;
							leafYOffset = -radius>>4;
						}else if(list.get(i+1).y < list.get(i+2).y) {
							startingPointOfAngle = 0;
							theta = 90;
							xOffset = -radius>>1;
							yOffset = 0;
							leafWidth = radius>>3;
							leafHeight = radius>>2;
							leafXOffset = 0;
							leafYOffset = 0;
						}// end of if/else
					}// end of if/else
				}// end of if(list.get(i).y == list.get(i+1).y)
				
				g2d.drawArc(list.get(i).midPoint(list.get(i+1)).x+xOffset, list.get(i).midPoint(list.get(i+1)).y+yOffset, radius, radius, startingPointOfAngle, theta);
				g2d.setColor(new Color(0, 255, 0));
				g2d.fillOval(list.get(i).midPoint(list.get(i+1)).x+leafXOffset, list.get(i).midPoint(list.get(i+1)).y+leafYOffset, leafWidth, leafHeight);
			}// end of if(i < list.size() - 2)
			if(level == 1) {
			g2d.drawLine(list.get(i).x, list.get(i).y, list.get(i+1).x, list.get(i+1).y);
			}

		}// end of for loop

	}// end of drawTo method

	void moveTo(Graphics g, double x, double y) {
		xLast = x;
		yLast = y;
	}

	public void paint(Graphics g) {
		Dimension d = getSize();
		maxX = d.width - 1;
		maxY = d.height - 1;
		xLast = fxStart * maxX;
		yLast = fyStart * maxY;
		points.add(new Point(iX(xLast), iY(yLast)));
		dir = dirStart; // Initial direction in degrees
		turtleGraphics(g, axiom, level, lengthFract * maxY);
		drawTo(g, points);
	}

	public void turtleGraphics(Graphics g, String instruction, int depth, double len) {
		double xMark = 0.0, yMark = 0.0, dirMark = 0.0;

		for (int i = 0; i < instruction.length(); i++) {

			char ch = instruction.charAt(i);
			switch (ch) {
			case 'F': // Step forward and draw
				// Start: (xLast, yLast), direction: dir, steplength: len
				if (depth == 0) {

					double rad = Math.PI / 180.0 * (dir);
					double dx = len * Math.cos(rad);
					double dy = len * Math.sin(rad);
					points.add(new Point(iX(xLast + dx), iY(yLast + dy)));
					xLast += dx;
					yLast += dy;

				} else
					turtleGraphics(g, strF, depth - 1, reductFact * len);
				break;
			case 'f': // Step forward without drawing
				// Start: (xLast, yLast), direction: dir, steplength: len
				if (depth == 0) {
					double rad = Math.PI / 180 * dir; // Degrees -> radians
					double dx = len * Math.cos(rad);
					double dy = len * Math.sin(rad);
					moveTo(g, xLast + dx, yLast + dy);
				} else
					turtleGraphics(g, strf, depth - 1, reductFact * len);
				break;
			case 'X':
				if (depth > 0)
					turtleGraphics(g, strX, depth - 1, reductFact * len);
				break;
			case 'Y':
				if (depth > 0)
					turtleGraphics(g, strY, depth - 1, reductFact * len);
				break;
			case '+': // Turn right
				oldDir = dir;
				dir -= rotation;
				break;
			case '-': // Turn left
				oldDir = dir;
				dir += rotation;
				break;
			case '[': // Save position and direction
				xMark = xLast;
				yMark = yLast;
				dirMark = dir;
				break;
			case ']': // Back to saved position and direction
				xLast = xMark;
				yLast = yMark;
				dir = dirMark;
				break;
			}// end of switch
		}// end of for loop
	}// end of turtleGraphics
}// end of JeremysAmazingSubClassForAssignment5 class
