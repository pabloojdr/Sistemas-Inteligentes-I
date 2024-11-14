import java.io.FileNotFoundException;

public class Opcional {

	public static void main(String[] args) throws FileNotFoundException {
		int ITERACIONES = 5000;
		int n = 0;
		int e = 0;
		for (int i = 0; i < ITERACIONES; ++i) {
			AlgoritmoA alg = new AlgoritmoA();
			n += alg.tamCam;
			if(alg.errores) {
				e++;
			}
		}

		System.out.println("Tamaño medio del camino: " + (float) n / (ITERACIONES - e));
		System.out.println("Tamaño medio de errores: " + (double) (e / ITERACIONES)); 
		System.out.println("Errores: " + e);
	}
}
