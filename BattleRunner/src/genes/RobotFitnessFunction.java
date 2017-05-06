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
	
	public RobotFitnessFunction(String robotToImprove){
		this.robotToImprove = robotToImprove;
	}
	

	@Override
	protected double evaluate(IChromosome chromosomes) {
		setValues(chromosomes);
		int val=BattleRunner.run(robotToImprove);
		System.out.print(val+ " ");
		return val;
	}
	
	/*
	 * 
	 * */
	private void setValues(IChromosome chromosomes){
		String filename = "C:\\Users\\SrSut\\workspaceRobocode\\GeneticAlgorithmPractice\\src\\supersample\\tuneFire.txt";
		try {
			PrintWriter pw = new PrintWriter(new File(filename));
			for (int i=0; i<chromosomes.size(); i++){
				pw.append(chromosomes.getGene(i).getAllele().toString());
				pw.append(" ");
			}
			pw.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
