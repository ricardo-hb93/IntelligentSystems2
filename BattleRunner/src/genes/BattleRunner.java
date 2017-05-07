package genes;

import robocode.control.*;
import robocode.control.events.*;

public class BattleRunner {
	static int result = 0;

	/**
	 * Creates a battle with deterministic conditions
	 * 
	 * @return the final score of the robot that is being improved
	 **/
	public static int run(String robotToImprove) {
		RobocodeEngine.setLogMessagesEnabled(false);
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:\\Robocode"));
		engine.addBattleListener(new BattleObserver());
		engine.setVisible(false);

		long inactivityTime = 10000000;
		double gunCoolingRate = 0.2;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		int numRounds = 5;
		String robotsToAdd = "supersample.SuperRamFire*,";
		robotsToAdd = robotsToAdd.concat(robotToImprove);

		BattlefieldSpecification battlefield = new BattlefieldSpecification(832, 832);
		RobotSpecification[] selectedRobots = new RobotSpecification[2];
		RobotSetup[] initialSetups = new RobotSetup[2];
		selectedRobots = engine.getLocalRepository(robotsToAdd);
		initialSetups[0] = new RobotSetup(832.0 * 0.75, 832.0 * 0.25, 0.0);
		initialSetups[1] = new RobotSetup(832.0 * 0.25, 832.0 * 0.75, 0.0);
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numRounds, inactivityTime, gunCoolingRate,
				sentryBorderSize, hideEnemyNames, selectedRobots, initialSetups);
		engine.runBattle(battleSpec, true);
		engine.close();
		result = BattleObserver.res;
		return result;
	}

}

/**
 * Updates the result value when the battle ends
 * 
 **/
class BattleObserver extends BattleAdaptor {
	public static int res;

	public void onBattleCompleted(BattleCompletedEvent e) {
		res = e.getIndexedResults()[1].getScore();
	}
}
