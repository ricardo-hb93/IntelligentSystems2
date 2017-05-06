package genes;

import java.io.File;
import java.io.FileNotFoundException;
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
import org.jgap.data.DataTreeBuilder;
import org.jgap.data.IDataCreators;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;
import org.jgap.xml.XMLDocumentBuilder;
import org.jgap.xml.XMLManager;
import org.w3c.dom.Document;

public class MainTrackFire {
	private final static int MAX_ALLOWED_EVOLUTIONS = 10;

	public static void findConfigurationForRobot() throws Exception {
		Configuration conf = new DefaultConfiguration();
		conf.setPreservFittestIndividual(true);
		FitnessFunction myFunc = new RobotFitnessFunction("supersample.SuperTrackFire*");
		conf.setFitnessFunction(myFunc);

		Gene[] sampleGenes = new Gene[4];
		sampleGenes[0] = new DoubleGene(conf, 0.0, 1);	
		sampleGenes[1] = new DoubleGene(conf, 0.0, 4.0);
		sampleGenes[2] = new DoubleGene(conf, 0.0, 200.0);
		sampleGenes[3] = new DoubleGene(conf, 0.0, 1.0);
		
		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(10);

		Genotype population;
		try {
			Document doc = XMLManager.readFile(new File("robocodeJGAP.xml"));
			population = XMLManager.getGenotypeFromDocument(conf, doc);
		} catch (FileNotFoundException e) {
			population = Genotype.randomInitialGenotype(conf);
		}
		population = Genotype.randomInitialGenotype(conf);
		
		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			System.out.print("\nGeneration "+ i +":");
			population.evolve();
			bestSolutionSoFar = population.getFittestChromosome();
			System.out.println("The best solution has a fitness value of " + bestSolutionSoFar.getFitnessValue());
			System.out.println("\nIt contained the following values: ");
			for (int j = 0; j < 4; j++) {
				System.out.println("\n" + bestSolutionSoFar.getGene(j).getAllele() + " ");
			}
			System.out.println("\n");
		}

		DataTreeBuilder builder = DataTreeBuilder.getInstance();
		IDataCreators doc2 = builder.representGenotypeAsDocument(population);

		XMLDocumentBuilder docBuilder = new XMLDocumentBuilder();
		Document xmlDoc = (Document) docBuilder.buildDocument(doc2);
		XMLManager.writeFile(xmlDoc, new File("robocodeJGAP.xml"));

		
		sb.append("The best solution has a fitness value of " + bestSolutionSoFar.getFitnessValue());
		sb.append("\nIt contained the following values: ");
		for (int i = 0; i < 4; i++) {
			sb.append("\n" + bestSolutionSoFar.getGene(i).getAllele() + " ");
		}
		sb.append("\n");
		System.out.println(sb.toString());
		
		try {
		    Files.write(Paths.get("C:\\Users\\SrSut\\Desktop\\ResultsTrack.txt"), sb.toString().getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			findConfigurationForRobot();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
