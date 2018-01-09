package assignment5;

public class Point {

	int x, y;

	public Point() {
		this.x = 0;
		this.y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	
	public double distance(Point B) {
		return Math.round(Math.sqrt((B.x - this.x) * (B.x - this.x) + (B.y - this.y) * (B.y - this.y)));
	}// end of distance method

	
	public Point midPoint(Point B) {
		return new Point(Math.round((this.x + B.x) / 2), Math.round((this.y + B.y) / 2));
	}// end of midPoint method

}// end of Point class
