import java.util.*;
import com.qqwing.*;

public class Cromosoma implements Comparable<Cromosoma> {
	public int[][] cromosoma; // matriz de 9x9 que es el sudoku
	public static int[][] mascara; // matrix 9x9 que es una mascara, donde 1 sera una posicion fija y que por tanto
	// no se podra mover, y 0 una posicion que si se podra mover
	public int fitness; // valor de la funcion fitness

	public Cromosoma(QQWing qq) {
		fitness = 0;
		cromosoma = sudokuamatriz(qq);
		imprimirMatriz(cromosoma);
		mascara = matrizAMascara(cromosoma);
		// imprimirMatriz(mascara);
		cromosoma = generarCromosomaAleatorio();
		// imprimirMatriz(cromosoma);
		mutar(this);
		// imprimirMatriz(cromosoma);
		fitness = calcularFitness(this);
		// System.out.println(fitness);

	}

	public void imprimirCromosoma() {
		imprimirMatriz(cromosoma);
	}

	private static void imprimirMatriz(int[][] m) {
		System.out.print(
				"\n------------------------------------------------------------------------------------------------------\n");
		for (int i = 0; i < m.length; ++i) {
			for (int j = 0; j < m[i].length; j++) {
				System.out.print(" " + m[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	private int[][] sudokuamatriz(QQWing qq) {
		int[] array = qq.getPuzzle();
		int[][] m = new int[9][9];

		int k = 0;
		for (int i = 0; i < m.length; ++i) {
			for (int j = 0; j < m[i].length; j++) {
				m[i][j] = array[k];
				k++;
			}
		}
		return m;
	}

	private int[][] matrizAMascara(int[][] r) {
		int[][] m = new int[9][9];

		for (int i = 0; i < r.length; ++i) {
			for (int j = 0; j < r[i].length; j++) {
				if (r[i][j] == 0) {
					m[i][j] = 0;
				} else {
					m[i][j] = 1;
				}
			}
		}
		return m;
	}

	public int[][] generarCromosomaAleatorio() {
		int[][] aleatorio = new int[9][9];
		Set<Integer> conjunto = new HashSet<>();
		for (int i = 0; i < 9; ++i) { // Por fila
			conjunto = conjuntoFila(cromosoma, i); // conjunto con los elementos de la fila i que NO estan en el sudoku,
													// es decir, que podemos meter
			// System.out.println(conjunto);
			for (int j = 0; j < 9; j++) { // recorremos toda la fila
				if (mascara[i][j] == 0) {
					int indexAleat = new Random().nextInt(conjunto.size());
					int numeroAleat = (int) conjunto.toArray()[indexAleat]; // numero aleatroio del conjunto de valores
																			// disponibles
					conjunto.remove(numeroAleat);
					// System.out.print(numeroAleat);
					aleatorio[i][j] = numeroAleat;

				} else {
					aleatorio[i][j] = cromosoma[i][j];
				}
			}
		}
		return aleatorio;
	}

	public static void mutar(Cromosoma c) {
		for (int i = 0; i < 9; i++) {
			int probabilidad = new Random().nextInt(100);
			if (probabilidad <= 25) { // 50% posibilidades de mutar
				mutacionPorFilas(c.cromosoma, i);
			}
		}

	}

	private static void mutacionPorFilas(int[][] m, int fila) {
		// Generamos dos posiciones aleatorias de la fila
		int pos1 = new Random().nextInt(9);
		int pos2 = new Random().nextInt(9);
		// Nos aseguramos de que sean posiciones diferentes

		while (mascara[fila][pos1] == 1) {
			pos1 = new Random().nextInt(9);
		}
		while (mascara[fila][pos2] == 1 || pos2 == pos1) {
			pos2 = new Random().nextInt(9);
		}

		// Intercambiamos los valores de las posiciones aleatorias
		int aux = m[fila][pos1];
		m[fila][pos1] = m[fila][pos2];
		m[fila][pos2] = aux;
	}

	// cruzar cromosomas: tenemos que cruzar las filas completas, es decir, elegimos
	// una fila aleatoria entre el 1 al 8 (0 no porque entonces realmente no
	// estariamos cruzando, los cromosomas se quedarian iguales)
	// PRECONDICION: Que los cromosomas esten formados sobre el mismo sudoku (tengan
	// la misma mascara con los mismos valores de la matriz)

	public static void cruzarProbabilidad(Cromosoma cromosoma1, Cromosoma cromosoma2) {
		int probabilidad = new Random().nextInt(100);
		if (probabilidad <= 25 ) { // 50% posibilidades de cruzar
			cruzarCromosomas(cromosoma1, cromosoma2);
		}
	}

	public static void cruzarCromosomas(Cromosoma cromosoma1, Cromosoma cromosoma2) {
		int fila = new Random().nextInt(8); // 1 al 8
		for (int i = fila; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// System.out.println(cromosoma1.cromosoma[i][j] + " : "
				// +cromosoma2.cromosoma[i][j]);
				// --> si ponemos arriba que cromosoma sea static
				// no funciona, lo puse porque otra funcion lo pedia, pero parece que al
				// quitarlo va todo bien, asi que seguimos adelante con ello
				int aux = cromosoma1.cromosoma[i][j];
				cromosoma1.cromosoma[i][j] = cromosoma2.cromosoma[i][j];
				cromosoma2.cromosoma[i][j] = aux;
			}
		}
	}

	private Set<Integer> conjuntoFila(int[][] matriz, int i) {
		Set<Integer> conjunto = new HashSet<>();
		for (int k = 1; k <= 9; k++) {
			conjunto.add(k);
		} // conjunto = {1,2,3,4,5,6,7,8,9}

		for (int j = 0; j < 9; j++) {
			conjunto.remove(matriz[i][j]);
		}
		return conjunto;
	}

	public void recalcularFitness() {
		this.fitness = calcularFitness(this);
	}

	public int calcularFitness(Cromosoma c) {
		int fit = 0;
		// COLUMNAS
		for (int j = 0; j < 9; j++) {
			Set<Integer> unicos = new HashSet<>();
			for (int i = 0; i < 9; ++i) {
				unicos.add(c.cromosoma[i][j]);
			}
			fit += unicos.size();
		}

		// BLOQUES 3X3 (Trabajamos en Z3)
		for (int k = 0; k < 9; k += 3) {
			for (int t = 0; t < 9; t += 3) {
				Set<Integer> unicos = new HashSet<>();
				for (int i = k; i < k + 3; i++) {
					for (int j = t; j < t + 3; j++) {
						unicos.add(c.cromosoma[i][j]);
					}
				}
				fit += unicos.size();
			}
		}

		return fit;
	}

	@Override
	public int compareTo(Cromosoma otro) {
		if (this.fitness > otro.fitness) {
			return -1;
		} else if (this.fitness < otro.fitness) {
			return 1;
		} else {
			return 0;
		}
	}
}
