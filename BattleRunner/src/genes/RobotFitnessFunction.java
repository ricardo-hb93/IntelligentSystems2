package genes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

@SuppressWarnings("serial")
public class RobotFitnessFunction extends FitnessFunction {
	// TODO: The constructor may need some kind of variable. In knapsack it is volume
	public static final double MAX_BOUND = 1000000000.0d;
	
	public RobotFitnessFunction(){
		//TODO: Constructor
	}
	

	@Override
	protected double evaluate(IChromosome chromosomes) {
		setValues(chromosomes);
		return BattleRunner.run();
	}
	
	private void setValues(IChromosome chromosomes){
		String filename = "Path";
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
