package genes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

@SuppressWarnings("serial")
public class RobotFitnessFunction extends FitnessFunction {
	String robotToImprove;
	public static final double MAX_BOUND = 1000000000.0d;

	/**
	 * RobotFitnessFunction constructor
	 * 
	 * @return a new RobotFitnessFunction given a String with the location of
	 *         the robot
	 * 
	 */
	public RobotFitnessFunction(String robotToImprove) {
		this.robotToImprove = robotToImprove;
	}

	/**
	 * Objective function that tells us how "fit" is the robot
	 * 
	 * @return a double. The higher the value, the fitter the robot
	 * 
	 **/
	@Override
	protected double evaluate(IChromosome chromosomes) {
		setValues(chromosomes, robotToImprove);
		int val = BattleRunner.run(robotToImprove);
		System.out.print(val + " ");
		return val;
	}

	/**
	 * 
	 * Void function that changes the values in a text file. When the robots are
	 * created, they read their attributes from that file
	 * 
	 */
	private void setValues(IChromosome chromosomes, String robotToImprove) {
		String filename = "C:\\Users\\SrSut\\workspaceRobocode\\GeneticAlgorithmPractice\\src\\supersample\\tuneFire.txt";
		if (robotToImprove.equals("supersample.SuperTracker*")) {
			filename = "C:\\Users\\SrSut\\workspaceRobocode\\GeneticAlgorithmPractice\\src\\supersample\\tune.txt";
		}
		try {
			PrintWriter pw = new PrintWriter(new File(filename));
			for (int i = 0; i < chromosomes.size(); i++) {
				pw.append(chromosomes.getGene(i).getAllele().toString());
				pw.append(" ");
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
