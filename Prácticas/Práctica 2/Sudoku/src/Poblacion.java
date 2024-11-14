import java.util.*;
import com.qqwing.*;

public class Poblacion {
	public List<Cromosoma> individuos;
	public int tamano;

	public Poblacion(QQWing qq, int tamano) { // donde tamano es el tamaño de la población, queremos unos 200
		individuos = new ArrayList<>();
		for (int i = 0; i < tamano; i++) {
			Cromosoma cromosoma = new Cromosoma(qq);
			individuos.add(cromosoma);
		}
		this.tamano = tamano;
		//this.imprimirPoblacion();
		this.ordenarPorFitness();
		//this.imprimirPoblacion();

		System.out.println("FITNESS MEDIO: " + this.fitnessMedio());
	}

	public void ordenarPorFitness() {
		for(int i = 0; i < tamano; i++) {
			individuos.get(i).recalcularFitness();
		}
		Collections.sort(individuos);
	}

	public void imprimirPoblacion() {
		for (Cromosoma c : individuos) {
			System.out.print(c.fitness + "-");
		}
		System.out.println("\n\n");
	}

	public double fitnessMedio() {
		double s = 0;
		for (int i = 0; i < this.tamano; ++i) {
			s += individuos.get(i).fitness;
		}
		return s / this.tamano;
	}

}
