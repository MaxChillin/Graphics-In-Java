package finalProject.PongBrickBreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Ball extends Ellipse2D {
	private int x, y, radius, radiusScaler = 1, bonusTextTimer = 0;

	private float velocity, speedScaler, slowMotionDecayTimer, speedUpDecayTimer, bigBallDecayTimer;
	private boolean speedUp = false, slowMotion = false, multiBall = false, bigBall = false, bonus = false;
	private boolean up = false, down = false, right = false, left = false;
	Color color;

	public Ball(int x, int y, int r) {
		this.x = x - r;
		this.y = y - (r << 1);
		radius = r;
		radiusScaler = 2;
		color = Color.RED;
		velocity = 1.0f;
		speedScaler = 1.0f;
	}

	public Ball(Ball ball) {
		x = ball.x - ball.radius;
		y = ball.y;
		radius = ball.radius;
		radiusScaler = ball.radiusScaler;
		color = Color.CYAN;
		velocity = 1.0f;
		speedScaler = 1.2f;
		up = ball.isUp();
		down = ball.isDown();
		if(ball.isLeft() == false && ball.isRight() == false) {
			left = true;
		}else {
			right = !ball.isRight();
			left = !ball.isLeft();
		}
	}

	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillOval(this.x - radius * (radiusScaler>>1), this.y - radius * (radiusScaler>>1), radius * radiusScaler, radius * radiusScaler);
	}

	// this is the method for controlling the movement of the ball.
	public void update() {
		if(bonus) {
			slowMotionDecayTimer -= 0.1f;
			speedUpDecayTimer -= 0.1f;
			bigBallDecayTimer -= 0.1f;

			if(slowMotionDecayTimer <= 0) {
				speedScaler = 1.0f;
			}

			if(speedUpDecayTimer <= 0) {
				speedScaler = 1.0f;
			}

			if(bigBallDecayTimer <= 0) {
				radiusScaler = 2;
			}
		}

		if (slowMotion) {
			System.out.println("Slow Motion");
			slowMotion = false;
			speedScaler = 0.5f;
			slowMotionDecayTimer = 30.0f;
			bonus = true;
		}

		if (speedUp) {
			System.out.println("Speed Boost");
			speedUp = false;
			speedScaler = 2.0f;
			speedUpDecayTimer = 30.0f;
			bonus = true;
		}

		if (bigBall) {
			System.out.println("Bigball");
			bigBall = false;
			radiusScaler = 4;
			bigBallDecayTimer = 30.0f;
			bonus = true;
		} 
		
		if (multiBall) {
			System.out.println("Multiball");
			multiBall = false;
		}

		if (left) {
			x -= velocity * speedScaler;
		}

		if (right) {
			x += velocity * speedScaler;
		}

		if (up) {
			y -= velocity * speedScaler;
		}

		if (down) {
			y += velocity * speedScaler;
		}

	}// end of update method

	public void setAllBooleansToFalse() {
		speedUp = slowMotion = multiBall = bigBall = bonus = false;
		up = down = right = left = false;

	}

	public boolean isMultiBall() {
		return multiBall;
	}

	public void setMultiBall(boolean multiBall) {
		this.multiBall = multiBall;
	}

	public int getBonusTextTimer() {
		return bonusTextTimer;
	}
	
	public void setBonusTextTimer(int bonusTextTimer) {
		this.bonusTextTimer = bonusTextTimer;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getScaler() {
		return radiusScaler;
	}

	public void setScaler(int scaler) {
		this.radiusScaler = scaler;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isSpeedUp() {
		return speedUp;
	}

	public void setSpeedUp(boolean speedUp) {
		this.speedUp = speedUp;
		this.slowMotion = false;
	}

	public boolean isSlowMotion() {
		return slowMotion;
	}

	public void setSlowMotion(boolean slowDown) {
		this.slowMotion = slowDown;
		this.speedUp = false;
	}

	public boolean isBigBall() {
		return bigBall;
	}

	public void setBigBall(boolean bigBall) {
		this.bigBall = bigBall;
	}

	@Override
	public Rectangle2D getBounds2D() {
		return null;
	}

	@Override
	public double getHeight() {
		return radius << 1;
	}

	@Override
	public double getWidth() {
		return radius << 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void setFrame(double x, double y, double w, double h) {
	}

}// end of the ball class