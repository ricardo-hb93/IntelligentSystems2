package genes;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DoubleGene;

public class Main {
	public static void main(String[]args){
		DoubleGene genes[] = new DoubleGene[4];
		try {
			int i = 0;
			Configuration conf=new Configuration();
			Scanner sc = new Scanner(new FileReader("C:\\Users\\alumno\\workspace-jee\\BattleRunner\\src\\a.txt"));
			Scanner sc2 = new Scanner(sc.nextLine());

			while (i<4) {
				genes[i] = new DoubleGene(conf);
				genes[i].setAllele(sc2.nextDouble());

				i++;
			}
			Chromosome c = new Chromosome(conf, genes);
			sc2.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			System.out.println("Meh");
		}
		BattleRunner.run();
		System.exit(0);
	}
}
