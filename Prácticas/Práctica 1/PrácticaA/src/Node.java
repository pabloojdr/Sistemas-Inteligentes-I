import java.util.ArrayList;

public class Node {
	public Tupla pos;
	public Tipo tipo;
	public ArrayList<Node> vecinos;
	public Node bestPrev;
	boolean optimalPath;
	public double f;
	public double g;
	public double h;

	public Node() {
		f = -1;
		g = -1;
		h = -1;
		vecinos = new ArrayList<Node>();
		optimalPath = false;
		bestPrev = null;
	}

	public Tipo setTipo(Tipo t) {
		tipo = t;
		return tipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setPosicion(int x, int y) {
		pos = new Tupla(x, y);
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getH() {
		return h;
	}
	
	public void setH(double he) {
		h = he;
	}
	
	public ArrayList<Node> getVecinos(){
		return vecinos;
	}
	
	public void setVecinos(ArrayList<Node> lista) {
		vecinos = lista;
	}

	public Node getBestPrev() {
		return bestPrev;
	}

	public void setBestPrev(Node bestPrev) {
		this.bestPrev = bestPrev;
	}

	@Override
	public String toString() {
		return pos.toString();
	}
	
}
