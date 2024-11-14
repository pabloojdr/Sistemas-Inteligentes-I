import java.io.*;
import java.util.*;

public class AlgoritmoA {
	public Tablero laberinto;
	public Node start;
	public Node goal;
	public Set<Node> closedset;
	public Set<Node> openset;
	public ArrayList<Node> parent;
	public Node current;
	public ArrayList<Node> caminoOp;
	public int tamCam;
	public boolean errores = false;

	public AlgoritmoA() throws FileNotFoundException {
		laberinto = new Tablero();
		start = laberinto.init;
		goal = laberinto.goal;

		closedset = new HashSet<Node>();
		openset = new HashSet<Node>();
		openset.add(start);

		parent = new ArrayList<Node>();
		parent.add(start);

		start.setG(0);
		start.setF(start.getH() + start.getG());

		caminoOp = Algoritmo();
		System.out.println(imprimirCaminoOptimo(caminoOp));
		System.out.println(laberinto.imprimirTableroS());
		imprimirFichero();

	}

	public ArrayList<Node> Algoritmo() {
		while (!openset.isEmpty()) {
			current = menorF(openset);
			if (current.equals(goal)) {
				return reconstructPath(parent, current);
			}
			openset.remove(current);
			closedset.add(current);

			for (Node neighbour : current.getVecinos()) {

				if (!closedset.contains(neighbour)) {
					double tentativa_g = current.getG() + 1;
					if (!openset.contains(neighbour) || tentativa_g < neighbour.getG()) {
						neighbour.setBestPrev(current);
						neighbour.setG(tentativa_g);
						neighbour.setF(neighbour.getG() + neighbour.getH());
						if (neighbour.getTipo() != Tipo.OBS) {
							if (!openset.contains(neighbour)) {
								openset.add(neighbour);
							}
						}
					}
				}
			}
		}

		return new ArrayList<Node>();
	}

	private ArrayList<Node> reconstructPath(ArrayList<Node> p, Node c) {
		p.add(c);
		Node sig = c.bestPrev;
		while (sig != null) {
			p.add(sig);
			sig = sig.bestPrev;
		}
		
		tamCam = p.size();
		return p;
	}

	private Node menorF(Set<Node> open) {
		Iterator<Node> opIt = open.iterator();
		Node nodo = opIt.next();

		while (opIt.hasNext()) {
			Node next = opIt.next();
			if (next.getF() < nodo.getF()) {
				nodo = next;
			}
		}

		return nodo;

	}

	private String imprimirCaminoOptimo(ArrayList<Node> co) {
		String s = "";
		ArrayList<String> res = new ArrayList<>();

		if (co.isEmpty()) {
			s = s + ("No se ha encontrado un camino optimo para el laberinto");
			errores = true;
			laberinto.imprimirTablero();
		} else {
			s = s + ("El camino optimo para llegar desde la posición inicial " + start.toString()
					+ " hasta la posición final " + goal.toString() + " es: ");

			for (int i = co.size() - 1; i > 0; i--) {
				if (i != co.size() - 1) {
					res.add(co.get(i).toString());
				} else {
					res.add(co.get(i).toString());
				}
				if (!co.get(i).equals(start) && !co.get(i).equals(goal)) {
					co.get(i).setTipo(Tipo.OPTIMAL);
				}
			}
			// System.out.println(res.toString());

		}
		return s + res.toString();
	}

	private void imprimirFichero() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter("salida.txt");
		mostrarInfo(pw);
		pw.close();
	}

	private void mostrarInfo(PrintWriter pw) {
		pw.println(imprimirCaminoOptimo(caminoOp));
		pw.println(laberinto.imprimirTableroS());
	}
}