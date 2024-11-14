import com.qqwing.*;

public class PruebaAlgoritmoGenetico {

	public static void main(String[] args) {
		QQWing sudoku = new QQWing();
		sudoku.generatePuzzle();
		AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(10000, sudoku, 500);
	}

}
