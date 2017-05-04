package test;

import robocode.control.*;
import robocode.control.events.*;

public class TestRobotsMain {
	public static void main(String[] args) {
		RobocodeEngine.setLogMessagesEnabled(false);
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:\\Robocode"));
		engine.addBattleListener(new BattleObserver());
		engine.setVisible(true);

		long inactivityTime = 10000000;
		double gunCoolingRate = 0.2;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		int numRounds = 5;
		String robotsToAdd = "supersample.SuperRamFire*, supersample.SuperTracker*";

		BattlefieldSpecification battlefield = new BattlefieldSpecification(832, 832);
		RobotSpecification[] selectedRobots = new RobotSpecification[2];
		RobotSetup[] initialSetups = new RobotSetup[2];
		selectedRobots = engine.getLocalRepository(robotsToAdd);
		initialSetups[0] = new RobotSetup(832.0*0.75, 832.0*0.25, 0.0);
		initialSetups[1] = new RobotSetup(832.0*0.25, 832.0*0.75, 0.0);
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numRounds, inactivityTime, gunCoolingRate, sentryBorderSize, hideEnemyNames, selectedRobots, initialSetups);
		engine.runBattle(battleSpec, true);
		engine.close();
		System.exit(0);
	}
}

class BattleObserver extends BattleAdaptor {
	public void onBattleCompleted(BattleCompletedEvent e) {
		System.out.println("-- Battle has completed --");

		System.out.println("Battle results:");
		System.out.println(e.getIndexedResults()[1].getScore() - e.getIndexedResults()[0].getScore());
		for (robocode.BattleResults result : e.getSortedResults()) {
			System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
		}
	}

	public void onBattleMessage(BattleMessageEvent e) {
		System.out.println("Msg> " + e.getMessage());
	}

	public void onBattleError(BattleErrorEvent e) {
		System.out.println("Err> " + e.getError());
	}
}
