import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.qqwing.*;

public class AlgoritmoGenetico {
	public Poblacion poblacion;
	final double porcentajeFijos = 0.2;
	final double porcentajeCruzar = 0.25;
	final int numGeneraciones;
	double[][] opcional;
	public Cromosoma s;

	public AlgoritmoGenetico(int numGeneraciones, QQWing qq, int tamanoPoblacion) {
		this.numGeneraciones = numGeneraciones;
		s = null;
		poblacion = new Poblacion(qq, tamanoPoblacion);
		opcional = new double[numGeneraciones][2];
		procesar();
		imprimirSolucion();

	}

	private void procesar() {
		int i = 0;
		while (i < numGeneraciones) {
			poblacion.ordenarPorFitness();
			opcional[i][0] = poblacion.fitnessMedio();
			opcional[i][1] = poblacion.individuos.get(0).fitness;
			poblacion.imprimirPoblacion();
			int indice1 = 0;
			int indice2 = 0;
			if (i < (numGeneraciones * 0.65)) {
				// Exploracion
				indice1 = (int) ((int) poblacion.tamano * porcentajeFijos);
				indice2 = (int) ((int) (poblacion.tamano - indice1) * porcentajeCruzar) + indice1;

			} else {
				// Explotacion
				indice1 = (int) ((int) poblacion.tamano * (porcentajeFijos * 0.1));
				indice2 = (int) ((int) (poblacion.tamano - indice1) * (porcentajeCruzar) + indice1);
			}

			for (int j = indice1; j < indice2 - 1; j += 2) {
				Cromosoma.cruzarProbabilidad(poblacion.individuos.get(j), poblacion.individuos.get(j + 1));
			}
			for (int k = indice2; k < indice2 + (int) (indice2 / 2) - 1; k++) {
				Cromosoma.mutar(poblacion.individuos.get(k));
			}
			for (int p = (int) indice2 + (indice2 / 2); p < poblacion.tamano; p++) {
				Cromosoma.mutar(poblacion.individuos.get(p));
				Cromosoma.mutar(poblacion.individuos.get(p));
				Cromosoma.mutar(poblacion.individuos.get(p));
				Cromosoma.mutar(poblacion.individuos.get(p));
				Cromosoma.mutar(poblacion.individuos.get(p));
			}
			System.out.println("Generaci�n n�mero " + i + ": FITNESS MEDIO: " + poblacion.fitnessMedio() + "\n");

			if (poblacion.individuos.get(0).fitness == 162) {
				s = poblacion.individuos.get(0);
				break;
			}

			i++;
		}
	}

	public void imprimirOpcional1() {
		for (int i = 0; i < numGeneraciones; i++) {
			System.out.print("[FM: " + opcional[i][0] + " , MF: " + opcional[i][1]);
			System.out.println(" ");
		}
	}

	public void imprimirFitnessMedio() {
		try {
			FileWriter fileWriter = new FileWriter("fitness_medio.txt");
			PrintWriter printWriter = new PrintWriter(fileWriter);

			for (int i = 0; i < numGeneraciones; i++) {
				int fitnessMedio = (int) (opcional[i][0] * 1000);
				printWriter.print(fitnessMedio);
				printWriter.println(" ");
			}

			printWriter.close();
			System.out.println("El archivo se ha guardado exitosamente.");
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error al guardar el archivo.");
			e.printStackTrace();
		}
	}

	public void imprimirMejorFitness() {
		try {
			FileWriter fileWriter = new FileWriter("mejor_fitness.txt");
			PrintWriter printWriter = new PrintWriter(fileWriter);

			for (int i = 0; i < numGeneraciones; i++) {
				int fitness = (int) (opcional[i][1] * 1000);
				printWriter.print(fitness);
				printWriter.println(" ");
			}

			printWriter.close();
			System.out.println("El archivo se ha guardado exitosamente.");
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error al guardar el archivo.");
			e.printStackTrace();
		}
	}

	private void imprimirSolucion() {
		if (s != null) {
			System.out.println("Se ha encontrado la siguiente soluci�n: ");
			s.imprimirCromosoma();
		} else {
			System.out.println("No se ha encontrado ninguna soluci�n. El mejor fitness obtenido ha sido: "
					+ poblacion.individuos.get(0).fitness);
		}
	}
}
