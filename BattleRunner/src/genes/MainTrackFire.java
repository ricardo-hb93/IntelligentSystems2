package genes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;


public class MainTrackFire {
	private final static int MAX_ALLOWED_EVOLUTIONS = 10;
	private final static int POPULATION = 10;

	/**
	 * Main method for training the SuperTrackFire robot
	 * 
	 **/
	public static void main(String[] args) {
		try {
			findConfigurationForRobot();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * finds the fittest configuration for a SuperTrackFire robot using GA
	 * 
	 **/
	public static void findConfigurationForRobot() throws Exception {
		Configuration conf = new DefaultConfiguration();
		conf.setPreservFittestIndividual(true);
		FitnessFunction myFunc = new RobotFitnessFunction("supersample.SuperTrackFire*");
		conf.setFitnessFunction(myFunc);

		Gene[] sampleGenes = new Gene[4];
		setGenes(conf, sampleGenes);

		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(POPULATION);

		Genotype population;
		population = Genotype.randomInitialGenotype(conf);

		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			System.out.print("\nGeneration " + i + ":");
			population.evolve();
			bestSolutionSoFar = population.getFittestChromosome();
			System.out.println("The best solution has a fitness value of " + bestSolutionSoFar.getFitnessValue());
			for (int j = 0; j < 4; j++) {
				System.out.print(bestSolutionSoFar.getGene(j).getAllele() + ", ");
			}
		}

		sb.append("The best solution has a fitness value of " + bestSolutionSoFar.getFitnessValue());
		sb.append("\nIt contained the following values: ");
		for (int i = 0; i < 4; i++) {
			sb.append("\n" + bestSolutionSoFar.getGene(i).getAllele() + " ");
		}
		sb.append("\n");
		System.out.println(sb.toString());

		try {
			Files.write(Paths.get("C:\\Users\\SrSut\\Desktop\\ResultsTrack.txt"), sb.toString().getBytes(),
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void setGenes(Configuration conf, Gene[] sampleGenes) throws InvalidConfigurationException {
		sampleGenes[0] = new DoubleGene(conf, 0.0, 2.0);
		sampleGenes[1] = new DoubleGene(conf, 0.0, 8.0);
		sampleGenes[2] = new DoubleGene(conf, 0.0, 1000.0);
		sampleGenes[3] = new DoubleGene(conf, 0.5, 1.0);
	}
}
