package finalProject.PongBrickBreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Paddle extends Rectangle2D{
	
	private int x, y, width, height;

	private boolean left = false, right = false, leftCollision = false, rightCollision = false;
	float velocity, acceleration, force, mass;
	
	public Paddle(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		velocity = 1.0f;
		acceleration = 0.0f;
		force = 0.0f;
	}// end of constructor


	public double getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public double getCenterX() {
		return this.x + (this.width>>1);
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getRightSide() {
		return this.x + this.width;
	}

	public double getWidth() {
		return width;
	}

	public void setwidth(int width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}
	
	public void setheight(int height) {
		this.height = height;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

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

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getForce() {
		return mass*acceleration;
	}

	public void setForce(float force) {
		this.force = force;
	}
	
	public boolean isLeftCollision() {
		return leftCollision;
	}

	public void setLeftCollision(boolean leftCollision) {
		this.leftCollision = leftCollision;
	}

	public boolean isRightCollision() {
		return rightCollision;
	}

	public void setRightCollision(boolean rightCollision) {
		this.rightCollision = rightCollision;
	}

	public Rectangle2D createIntersection(Rectangle2D r) {
		return null;
	}

	public Rectangle2D createUnion(Rectangle2D r) {
		return null;
	}

	public int outcode(double x, double y) {
		return 0;
	}

	public void setRect(double x, double y, double w, double h) {
		
	}

	public boolean isEmpty() {
		return false;
	}

	public void update(){
		if(this.left && !leftCollision) {
			x-=velocity;
		}
		if(this.right && !rightCollision) {
			x+=velocity;
		}
	}// end of update method
	
	public void draw(Graphics g){
		g.setColor(Color.BLUE);		
		g.fillRoundRect(this.x, this.y, (int) width, (int) height, 35, 35);
	}// end of the draw method
	
}// end of the Paddle class