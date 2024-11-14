import java.util.ArrayList;
import java.util.Random;

public class Tablero {
	final static int FILAS = 60;
	final static int COLUMNAS = 80;
	final static double PORCENTAJE = 0.3;
	Random random = new Random();
	Node[][] board = new Node[FILAS][COLUMNAS];
	boolean error = false;

	public Node init;
	public Node goal;

	public Tablero() {
		inicializar();
		fijarPosiciones();
		fijarObstaculos();
		inicializarHeuristico();
		anadirVecinos();
	}

	public void inicializar() {
		for (int i = 0; i < FILAS; i++) {
			for (int j = 0; j < COLUMNAS; j++) {
				board[i][j] = new Node();
				board[i][j].setPosicion(i, j);
				board[i][j].setTipo(Tipo.NORMAL);
			}
		}
	}

	public void fijarObstaculos() {
		int numObstaculos = (int) Math.round(FILAS * COLUMNAS * PORCENTAJE);
		Random rad = new Random();

		while (numObstaculos > 0) {
			int fila = rad.nextInt(FILAS);
			int columna = rad.nextInt(COLUMNAS);

			if (board[fila][columna].getTipo() != Tipo.OBS && board[fila][columna].getTipo() != Tipo.INICIO
					&& board[fila][columna].getTipo() != Tipo.FINAL) {
				board[fila][columna].setTipo(Tipo.OBS);
				numObstaculos--;
			}
		}
	}

	public void fijarPosiciones() {
		int filaI = random.nextInt(FILAS);
		int columnaI = random.nextInt(COLUMNAS);
		int filaG = random.nextInt(FILAS);
		int columnaG = random.nextInt(COLUMNAS);

		// if (board[filaI][columnaI].getTipo() == Tipo.OBS ||
		// board[filaG][columnaG].getTipo() == Tipo.OBS) {
		// System.out.println("La posición del estado inicial o del estado final no
		// puede coincidir con un obstáculo");
		// error = true;
		// } else if (board[filaI][columnaI].getTipo() == Tipo.NORMAL &&
		// board[filaG][columnaG].getTipo() == Tipo.NORMAL) {
		init = new Node();
		goal = new Node();

		init.setPosicion(filaI, columnaI);
		init.setTipo(Tipo.INICIO);

		goal.setPosicion(filaG, columnaG);
		goal.setTipo(Tipo.FINAL);

		board[filaI][columnaI] = init;
		board[filaG][columnaG] = goal;
		if (filaI == filaG && columnaI == columnaG) {
			fijarPosiciones();
		}
	}

	private void inicializarHeuristico() {
		// vamos a calcular la distancia heuristica desde el nodo de inicio hasta el
		// nodo actual laberinto[i][j]
		// para ello, vamos a usar la distancia de Manthattan
		for (int i = 0; i < FILAS; ++i) {
			for (int j = 0; j < COLUMNAS; ++j) {
				Node actual = board[i][j];
				double dist = Math.abs(actual.pos.getX() - init.pos.getX())
						+ Math.abs(actual.pos.getY() - init.pos.getY());
				board[i][j].setH(dist);
			}
		}
	}

	private void anadirVecinos() {
		// Vamos a añadir los vecinos posibles del nodo en la posicion (i,j) para todo
		// i,j
		// en princippio, vamos a emplear los vecinos de Von Neumman -> 4 vecinos (no
		// usamos las diagonales)
		for (int i = 0; i < FILAS; ++i) {
			for (int j = 0; j < COLUMNAS; ++j) {
				ArrayList<Node> lista = new ArrayList<>();
				if (i > 0 && i < FILAS - 1 && j > 0 && j < COLUMNAS - 1) {
					lista.add(board[i - 1][j]);
					lista.add(board[i + 1][j]);
					lista.add(board[i][j - 1]);
					lista.add(board[i][j + 1]);
				} else if (i == (FILAS - 1) && j == (COLUMNAS - 1)) {
					lista.add(board[i - 1][j]);
					lista.add(board[i][j - 1]);
				} else if (i == (FILAS - 1) && j == 0) {
					lista.add(board[i - 1][j]);
					lista.add(board[i][j + 1]);
				} else if (i == (FILAS - 1)) {
					lista.add(board[i][j - 1]);
					lista.add(board[i][j + 1]);
					lista.add(board[i - 1][j]);
				} else if (i == 0 && j == (COLUMNAS - 1)) {
					lista.add(board[i][j - 1]);
					lista.add(board[i + 1][j]);
				} else if (i == 0 && j == 0) {
					lista.add(board[i][j + 1]);
					lista.add(board[i + 1][j]);
				} else if (i == 0) {
					lista.add(board[i][j - 1]);
					lista.add(board[i][j + 1]);
					lista.add(board[i + 1][j]);
				} else if (i == (FILAS - 1)) {
					lista.add(board[i][j + 1]);
					lista.add(board[i][j - 1]);
					lista.add(board[i - 1][j]);
				} else if (j == 0) {
					lista.add(board[i + 1][j]);
					lista.add(board[i - 1][j]);
					lista.add(board[i][j + 1]);
				} else if (j == (COLUMNAS - 1)) {
					lista.add(board[i][j - 1]);
					lista.add(board[i - 1][j]);
					lista.add(board[i + 1][j]);
				}
				board[i][j].setVecinos(lista);
			}
		}
	}

	public void imprimirTablero() {
		for (int i = 0; i < FILAS; ++i) {
			for (int j = 0; j < COLUMNAS; ++j) {
				Node casilla = board[i][j];
				if (casilla.getTipo() == Tipo.OBS) {
					System.out.print("#");
				} else if (casilla.getTipo() == Tipo.INICIO) {
					System.out.print("I");
				} else if (casilla.getTipo() == Tipo.FINAL) {
					System.out.print("G");
				} else if (casilla.getTipo() == Tipo.OPTIMAL) {
					System.out.print("+");
				} else if (casilla.getTipo() == Tipo.NORMAL) {
					System.out.print(" ");
				} else {
					System.out.print("error");
				}
			}
			System.out.println();
		}
	}

	public String imprimirTableroS() {
		String res = "";
		for (int i = 0; i < FILAS; ++i) {
			for (int j = 0; j < COLUMNAS; ++j) {
				Node casilla = board[i][j];
				if (casilla.getTipo() == Tipo.OBS) {
					res = res + "#";
				} else if (casilla.getTipo() == Tipo.INICIO) {
					res = res + "I";
				} else if (casilla.getTipo() == Tipo.FINAL) {
					res = res + "G";
				} else if (casilla.getTipo() == Tipo.OPTIMAL) {
					res = res + "+";
				} else if (casilla.getTipo() == Tipo.NORMAL) {
					res = res + " ";
				} else {
					res = res + "error";
				}
			}
			res = res + "\n";
		}
		return res;
	}

}