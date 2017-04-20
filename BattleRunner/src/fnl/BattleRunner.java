package fnl;

import robocode.control.*;
import robocode.control.events.*;

public class BattleRunner {
	static double INIT_X = 32.0;
	static double INIT_Y = 32.0;

	public static void main(String[] args) {
		RobocodeEngine.setLogMessagesEnabled(false);
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
		engine.addBattleListener(new BattleObserver());
		engine.setVisible(true);

		long inactivityTime = 10000000;
		double gunCoolingRate = 0.2;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		int numRounds = 5;

		BattlefieldSpecification battlefield = new BattlefieldSpecification(832, 832);
		RobotSpecification[] selectedRobots = new RobotSpecification[2];
		RobotSetup[] initialSetups = new RobotSetup[2];
		selectedRobots = engine.getLocalRepository("supersample.SuperRamFire*,supersample.SuperTracker*");
		initialSetups[0] = new RobotSetup(580.0, 580.0, 0.0);
		initialSetups[1] = new RobotSetup(500.0, 500.0, 0.0);
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numRounds, inactivityTime, gunCoolingRate,
				sentryBorderSize, hideEnemyNames, selectedRobots, initialSetups);
		engine.runBattle(battleSpec, true);
		engine.close();

		System.exit(0);
	}
}

class BattleObserver extends BattleAdaptor {

	public void onBattleCompleted(BattleCompletedEvent e) {
		System.out.println("-- Battle has completed --");

		System.out.println("Battle results:");
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
