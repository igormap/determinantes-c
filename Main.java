class Main {

  public static void main(String[] args) {
    Matriz mat1;
    int det;
    long inicio, fim, resultado;

    mat1 = new Matriz(3, 3);
    mat1.inicializaRandomico(9);
    mat1.imprime();

	System.out.println("\n--------------------------\n");

	// Metodo 1: Sem otimização
	inicio = System.currentTimeMillis();
    det = mat1.determinanteBasico();
    fim = System.currentTimeMillis();
	resultado = fim - inicio;
	System.out.println("Metodo Basico:");
	System.out.println("Det: " + det + " | Tempo (ms): " + resultado + " | Tempo (ns): " + resultado * 1000000);

	// Metodo 2: Menor cofator
	inicio = System.currentTimeMillis();
    det = mat1.determinanteMenorCofator();
    fim = System.currentTimeMillis();
	resultado = fim - inicio;
	System.out.println("\nMetodo Menor Cofator:");
	System.out.println("Det: " + det + " | Tempo (ms): " + resultado + " | Tempo (ns): " + resultado * 1000000);

	// Metodo 3: Triangularização
	inicio = System.currentTimeMillis();
    det = mat1.determinanteTriangularizado();
    fim = System.currentTimeMillis();
	resultado = fim - inicio;
	System.out.println("\nMetodo Triangularizado:");
	System.out.println("Det: " + det + " | Tempo (ms): " + resultado + " | Tempo (ns): " + resultado * 1000000);

	System.out.println();
  }
}
