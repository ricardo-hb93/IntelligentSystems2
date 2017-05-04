package genes;

import robocode.control.*;
import robocode.control.events.*;

public class BattleRunner {
	static int result = 0;
	public static int run() {
		RobocodeEngine.setLogMessagesEnabled(false);
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
		engine.addBattleListener(new BattleObserver());
		engine.setVisible(false);

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
		result = BattleObserver.res;
	//	System.out.println(result);
		return result;
	}

//	public static void setResults(int r) {
//		result = r;
//	}
}

class BattleObserver extends BattleAdaptor {
	public static int res;
	public void onBattleCompleted(BattleCompletedEvent e) {
		//BattleRunner.setResults(e.getSortedResults()[0].getScore() - e.getSortedResults()[1].getScore());
		for(int i=0; i<e.getIndexedResults().length; i++){
//			System.out.println(e.getIndexedResults()[i].getScore());
		}
		res = e.getSortedResults()[0].getScore() - e.getSortedResults()[1].getScore();
	}
}
