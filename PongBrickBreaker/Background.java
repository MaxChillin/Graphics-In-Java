package finalProject.PongBrickBreaker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Background extends Canvas {

	private BufferedImage img;
	BufferStrategy bs;
	Graphics g;
	private int[] pixels;
	private int x, y;
	private boolean left = false, right = false;

	public Background() {

		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	public void update() {
//		System.out.println("Background updating...");
		if(left) {
			x-=1;
		}
		
		if(right) {
			x+=1;
		}
	}// end of the update method

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void render() {
//		System.out.println("Rendering...");
		bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		draw();

		g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH + 10, HEIGHT + 10, null);
		g.dispose();
		bs.show();
	}// end of render method

	public void draw() {
//		System.out.println("Drawing...");
		int xOffset = 0, yOffset = 0;
		for (int y = 0; y < HEIGHT; y++) {

			double ceiling = (y - HEIGHT / 2.0) / HEIGHT;
			int roomHeight = 12;

			if (ceiling < 0) {
				ceiling = -ceiling;
			}

			double z = roomHeight / ceiling;

			if (z > 150) {
				z = 0;
			}

			for (int x = 0; x < WIDTH; x++) {
				double depth = (x - WIDTH / 2.0) / HEIGHT;
				depth *= z;
				double xx = depth + x;
				double yy = z;
				int xPix = (int) (xx);
				int yPix = (int) (yy);
				pixels[x + y * WIDTH] = ((xPix & 15) << 4) | ((yPix & 7) << 4) << 16;

			} // end of x for loop

		} // end of y for loop

		for (int y = 0; y < HEIGHT; y++) {

			int yPix = y + yOffset;

			if (yPix < 0 || yPix >= HEIGHT) {
				continue;
			}

			for (int x = 0; x < WIDTH; x++) {

				int xPix = x + xOffset;

				if (xPix < 0 || xPix >= WIDTH) {
					continue;
				}

				int alpha = pixels[x + y * WIDTH];

				if (alpha > 0) {
					pixels[xPix + yPix * WIDTH] = alpha;
				}
			} // end of x for loop
		} // end of y for loop
	}// end of draw method

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	// public void floor(Movement movement) {
	//
	// for (int y = 0; y < HEIGHT; y++) {
	//
	// double ceiling = (y - HEIGHT / 2.0) / HEIGHT;
	// int roomHeight = 12;
	//
	// if (ceiling < 0) {
	// ceiling = -ceiling;
	// }
	//
	// double z = roomHeight / ceiling;
	//
	// if (z > 150) {
	// z = 0;
	// }
	//
	// for (int x = 0; x < WIDTH; x++) {
	// double depth = (x - WIDTH / 2.0) / HEIGHT;
	// depth *= z;
	// double xx = depth + movement.x;
	// double yy = z + movement.z;
	// int xPix = (int) (xx);
	// int yPix = (int) (yy);
	// pixels[x + y * WIDTH] = ((xPix & 15) << 4) | ((yPix & 7) << 4) << 16;
	//
	// }// end of x for loop
	//
	// }// end of y for loop
	//
	// }// end of floor method

}// end of the Background class
