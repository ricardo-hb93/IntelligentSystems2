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

public class Main {
	private final static int MAX_ALLOWED_EVOLUTIONS = 10;
	private final static int POPULATION = 10;

	/**
	 * Main method for training the SuperTracker robot
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
	 * Sets the genes for the different attributes
	 * 
	 **/
	private static void setGenes(Gene[] sampleGenes, Configuration conf) throws InvalidConfigurationException {
		sampleGenes[0] = new DoubleGene(conf, 0.0, 300.0); // Radar distance
		sampleGenes[1] = new DoubleGene(conf, 0.1, 1.0); // Speed change
															// probability
		sampleGenes[2] = new DoubleGene(conf, 0.1, 1.0); // Speed range
		sampleGenes[3] = new DoubleGene(conf, 0.1, 8.0); // Minimum speed
		sampleGenes[4] = new DoubleGene(conf, -1.0, 1.0); // Color!
	}

	/**
	 * finds the fittest configuration for a SuperTracker robot using GA
	 * 
	 **/
	@SuppressWarnings("deprecation")
	public static void findConfigurationForRobot() throws Exception {
		// Variables declaration and initialization
		int green = 0;
		int yellow = 0;
		Configuration conf = new DefaultConfiguration();
		FitnessFunction myFunc = new RobotFitnessFunction("supersample.SuperTracker*");
		Gene[] sampleGenes = new Gene[5];

		conf.setPreservFittestIndividual(true);
		conf.setFitnessFunction(myFunc);
		setGenes(sampleGenes, conf);

		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);

		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(POPULATION);

		Genotype population;
		population = Genotype.randomInitialGenotype(conf);

		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		StringBuilder sb = new StringBuilder();
		IChromosome popChromosomes[];
		double colorDouble;

		// Evolution

		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			System.out.print("Generation " + i + ":");
			population.evolve();
			bestSolutionSoFar = population.getFittestChromosome();
			System.out.println("The best solution has a fitness value of " + bestSolutionSoFar.getFitnessValue());
			popChromosomes = population.getChromosomes();
			for (int j = 0; j < popChromosomes.length; j++) {
				colorDouble = (double) popChromosomes[j].getGene(4).getAllele();
				if (colorDouble > 0)
					green++;
				else
					yellow++;
			}
		}

		// Saves the information about the fittest robot in a stringbuilder and
		// prints it
		sbCreator(green, yellow, bestSolutionSoFar, sb);

		// Saves the stringbuilder in an auxiliary file
		try {
			Files.write(Paths.get("C:\\Users\\SrSut\\Desktop\\Results.txt"), sb.toString().getBytes(),
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Auxiliary method. Saves the information about the fittest robot in a
	 * stringbuilder and prints it
	 **/
	private static void sbCreator(int green, int yellow, IChromosome bestSolutionSoFar, StringBuilder sb) {
		sb.append("The best solution has a fitness value of " + bestSolutionSoFar.getFitnessValue());
		sb.append("\nIt contained the following values: ");
		for (int i = 0; i < 5; i++) {
			sb.append("\n" + bestSolutionSoFar.getGene(i).getAllele() + " ");
		}
		sb.append("\n Green: " + green + ", Yellow: " + yellow);
		System.out.println(sb.toString());
	}
}
