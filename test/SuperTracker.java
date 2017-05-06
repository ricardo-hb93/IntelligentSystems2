package test;

import robocode.*;
import java.awt.*;
import java.util.Random;

/**
 * SuperTracker - a Super Sample Robot by CrazyBassoonist based on the robot
 * Tracker by Mathew Nelson and maintained by Flemming N. Larsen
 * <p/>
 * Locks onto a robot, moves close, fires when close.
 */
public class SuperTracker extends AdvancedRobot {
	int moveDirection = 1;// which way to move
	Random numberGenerator = new Random(0);
	private double[] params = { 5.907976137818505, 0.9630546789065678, 0.9789899727896388, 6.8069035003427745,
			-0.48852244735184613 };

	/**
	 * run: Tracker's main run function
	 */

	public void run() {
		setAdjustRadarForRobotTurn(true);
		if (params[4] > 0) {
			setAllColors(Color.YELLOW);
		} else {
			setAllColors(Color.GREEN);
		}
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
