import java.util.*;

import com.qqwing.*;

public class pruebas {

	public static void main(String[] args) {
		QQWing qq = new QQWing();
		qq.generatePuzzle();
		Cromosoma c1 = new Cromosoma(qq);
		Cromosoma c2 = new Cromosoma(qq);
		qq.printPuzzle();
		Poblacion p = new Poblacion(qq, 200);

		System.out.print("\n\n\n\n aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		c1.imprimirCromosoma();
		c2.imprimirCromosoma();
		System.out.println("Cromosomas cruzados: ");
		Cromosoma.cruzarCromosomas(c1, c2);
		c1.imprimirCromosoma();
		c2.imprimirCromosoma();
		System.out.println(c1.fitness);
		System.out.println(c2.fitness);

		/*
		 * Set<Integer> conjunto = new HashSet<>(); conjunto.add(2); conjunto.add(6);
		 * conjunto.add(3);
		 * 
		 * int index = new Random().nextInt(conjunto.size()); int aleat =
		 * conjunto.toArray(new Integer[conjunto.size()])[index];
		 * System.out.println(aleat);
		 */
	}

}
