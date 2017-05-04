package test;

import robocode.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * SuperTracker - a Super Sample Robot by CrazyBassoonist based on the robot
 * Tracker by Mathew Nelson and maintained by Flemming N. Larsen
 * <p/>
 * Locks onto a robot, moves close, fires when close.
 */
public class SuperTracker extends AdvancedRobot {
	int moveDirection = 1;// which way to move
	Random numberGenerator = new Random(0);
	private double[] params = { 101.7911592526748, 0.6323674372272312, 0.2994203426369497, 5.097183165330748 };

	/**
	 * run: Tracker's main run function
	 */

	public void run() {
		setAdjustRadarForRobotTurn(true);
		setBodyColor(new Color(128, 128, 50));
		setGunColor(new Color(50, 50, 20));
		setRadarColor(new Color(200, 200, 70));
		setScanColor(Color.white);
		setBulletColor(Color.blue);
		setAdjustGunForRobotTurn(true);
		turnRadarRightRadians(Double.POSITIVE_INFINITY);
	}

	/**
	 * onScannedRobot: Here's the good stuff
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		double absBearing = e.getBearingRadians() + getHeadingRadians();
		double latVel = e.getVelocity() * Math.sin(e.getHeadingRadians() - absBearing);
		double gunTurnAmt;
		setTurnRadarLeftRadians(getRadarTurnRemainingRadians());

		if (numberGenerator.nextDouble() > params[1]) {
			setMaxVelocity(((8 - params[3]) * params[2] * numberGenerator.nextDouble()) + params[3]);
		}
		if (e.getDistance() > params[0]) {
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + latVel / 22);
			setTurnGunRightRadians(gunTurnAmt);
			setTurnRightRadians(
					robocode.util.Utils.normalRelativeAngle(absBearing - getHeadingRadians() + latVel / getVelocity()));
			setAhead((e.getDistance() - 140) * moveDirection);
			setFire(3);
		} else {
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + latVel / 15);
			setTurnGunRightRadians(gunTurnAmt);
			setTurnLeft(-90 - e.getBearing());
			setAhead((e.getDistance() - 140) * moveDirection);
			setFire(3);
		}
	}

	public void onHitWall(HitWallEvent e) {
		moveDirection = -moveDirection;
	}

	public void onWin(WinEvent e) {
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}
}
