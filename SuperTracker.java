package supersample;

import robocode.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * SuperTracker - a Super Sample Robot by CrazyBassoonist based on the robot
 * Tracker by Mathew Nelson and maintained by Flemming N. Larsen
 * <p/>
 * Locks onto a robot, moves close, fires when close.
 */
public class SuperTracker extends AdvancedRobot {
	int moveDirection = 1;// which way to move
	private double[] params;  
	/**
	 * run:  Tracker's main run function
	 */
	public void run() {
		params = new double[4];
		tuneBot("your path");
		setAdjustRadarForRobotTurn(true);//keep the radar still while we turn
		setBodyColor(new Color(128, 128, 50));
		setGunColor(new Color(50, 50, 20));
		setRadarColor(new Color(200, 200, 70));
		setScanColor(Color.white);
		setBulletColor(Color.blue);
		setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		turnRadarRightRadians(Double.POSITIVE_INFINITY);//keep turning radar right
		}

	/**
	 * onScannedRobot: Here's the good stuff
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		double absBearing = e.getBearingRadians() + getHeadingRadians();// enemies
																		// absolute
																		// bearing
		double latVel = e.getVelocity() * Math.sin(e.getHeadingRadians() - absBearing);// enemies
																						// later
																						// velocity
		double gunTurnAmt;// amount to turn our gun
		setTurnRadarLeftRadians(getRadarTurnRemainingRadians());// lock on the
																// radar
		if (Math.random() > params[1]) {
			setMaxVelocity((params[2] * Math.random()) + params[3]);// randomly change speed
														// \\ VELOCIDAD + RANGO
														// + MINIMA VELOCIDAD
		}
		if (e.getDistance() > params[0]) {// if distance is greater than 150 \\
									// DISTANCIA
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + latVel / 22);// amount
																													// to
																													// turn
																													// our
																													// gun,
																													// lead
																													// just
																													// a
																													// little
																													// bit
			setTurnGunRightRadians(gunTurnAmt); // turn our gun
			setTurnRightRadians(
					robocode.util.Utils.normalRelativeAngle(absBearing - getHeadingRadians() + latVel / getVelocity()));// drive
																														// towards
																														// the
																														// enemies
																														// predicted
																														// future
																														// location
			setAhead((e.getDistance() - 140) * moveDirection);// move forward
			setFire(3);// fire
		} else {// if we are close enough...
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + latVel / 15);// amount
																													// to
																													// turn
																													// our
																													// gun,
																													// lead
																													// just
																													// a
																													// little
																													// bit
			setTurnGunRightRadians(gunTurnAmt);// turn our gun
			setTurnLeft(-90 - e.getBearing()); // turn perpendicular to the
												// enemy
			setAhead((e.getDistance() - 140) * moveDirection);// move forward
			setFire(3);// fire
		}
	}

	public void onHitWall(HitWallEvent e) {
		moveDirection = -moveDirection;// reverse direction upon hitting a wall
	}

	/**
	 * onWin: Do a victory dance
	 */
	public void onWin(WinEvent e) {
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}

	private void tuneBot(String path) {
		try {
			int i = 0;
			Scanner sc = new Scanner(new FileReader(path));
			Scanner sc2 = new Scanner(sc.nextLine());
			sc2.useDelimiter("<");
			
			while(sc2.hasNext()){
				params[i++] = sc2.nextDouble();
			}		
			
			sc2.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}