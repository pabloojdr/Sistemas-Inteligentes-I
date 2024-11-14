import com.qqwing.QQWing;

public class Opcional {

	public static void main(String[] args) {
		QQWing sudoku = new QQWing();
		sudoku.generatePuzzle();
		AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(1000, sudoku, 250);
		algoritmo.imprimirFitnessMedio();
		System.out.println("-----------------------------------------------------");
		algoritmo.imprimirMejorFitness();
		
	}

}
