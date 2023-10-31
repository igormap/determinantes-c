import java.lang.Math;
import java.util.Random;

class Matriz {

  private int[][] mat;
  private int tamLinha;
  private int tamColuna;

  Matriz() {
    mat = new int[6][6];
    this.setTamanhoLinha(6);
    this.setTamanhoColuna(6);
  }

  Matriz(int numLinhas, int numColunas) {
    mat = new int[numLinhas][numColunas];
    this.setTamanhoLinha(numLinhas);
    this.setTamanhoColuna(numColunas);
  }

  public int getValor(int indiceI, int indiceJ) {
    return mat[indiceI][indiceJ];
  }

  public void setValor(int indiceI, int indiceJ, int novoValor) {
    mat[indiceI][indiceJ] = novoValor;
  }

  public int getTamanhoLinha() {
    return tamLinha;
  }

  public int getTamanhoColuna() {
    return tamColuna;
  }

  private void setTamanhoLinha(int novoValor) {
    tamLinha = novoValor;
  }

  private void setTamanhoColuna(int novoValor) {
    tamColuna = novoValor;
  }

  public void imprime() {
    int conti, contj;
    for (conti = 0; conti < this.getTamanhoLinha(); conti++) {
      System.out.println();
      for (contj = 0; contj < this.getTamanhoColuna(); contj++) {
        System.out.print(this.getValor(conti, contj) + " ");
      }
    }
    System.out.println();
  }

  public void inicializaRandomico(int max_rand) {
    int conti, contj, novoValor;
    Random gerador = new Random();
    for (conti = 0; conti < this.getTamanhoLinha(); conti++) {
      for (contj = 0; contj < this.getTamanhoColuna(); contj++) {
        novoValor = gerador.nextInt(max_rand);
        this.setValor(conti, contj, novoValor);
      }
    }
  }

  // caso matriz nao quadrada, retorna -1
  public int retorneOrdem() {
    int numL, numC, ordem;

    numL = this.getTamanhoLinha();
    numC = this.getTamanhoColuna();
    ordem = -1;
    if (numL == numC) {
      ordem = numL;
    }

    return ordem;
  }

  private int detOrdem1(Matriz mat) {
    return mat.getValor(0, 0);
  }

  private int detOrdem2(Matriz mat) {
    int diagonalP, diagonalI;

    diagonalP = mat.getValor(0, 0) * mat.getValor(1, 1);
    diagonalI = mat.getValor(1, 0) * mat.getValor(0, 1);

    return (diagonalP - diagonalI);
  }

  private int calculaSinal(int indiceL, int indiceC) {
    int sinal;

    sinal = -1;

    if (((indiceL + indiceC) % 2) == 0) {
      sinal = 1;
    }

    return sinal;
  }

  public void copiaMatrizMaiorParaMenor(
    Matriz maior,
    Matriz menor,
    int isqn,
    int jsqn
  ) {
    int contAi, contAj, contBi, contBj, temp, numL, numC;
    numL = menor.getTamanhoLinha();
    numC = menor.getTamanhoColuna();

    contAi = 0;
    for (contBi = 0; contBi < numL; contBi++) {
      if (contAi == isqn) {
        contAi++;
      }
      contAj = 0;
      for (contBj = 0; contBj < numC; contBj++) {
        if (contAj == jsqn) {
          contAj++;
        }
        temp = maior.getValor(contAi, contAj);
        menor.setValor(contBi, contBj, temp);
        contAj++;
      }
      contAi++;
    }
  }

  private int detOrdemNBasico(Matriz mat) {
    int sinal, cofator, detTemp, resposta, contC, numL, numC;
    Matriz matmenor;
    numL = this.getTamanhoLinha();
    numC = this.getTamanhoColuna();

    resposta = 0;
    for (contC = 0; contC < mat.getTamanhoColuna(); contC++) {
      cofator = mat.getValor(0, contC);
      sinal = this.calculaSinal(0, contC);
      matmenor = new Matriz(numL - 1, numC - 1);
      this.copiaMatrizMaiorParaMenor(mat, matmenor, 0, contC);
      detTemp = matmenor.determinanteBasico();
      resposta = resposta + (cofator * sinal * detTemp);
    }
    return (resposta);
  }

  public String getFilaMaisZeros() {
    int cont, contLn, contCol;
    int indiceMaisZerosColuna = 0;
    int indiceMaisZerosLinha = 0;
    int contMaisZerosColuna = 0;
    int contMaisZerosLinha = 0;
    String filaMaisZeros;

    // Descobrir COLUNA com mais zeros
    contCol = 0;
    while (contCol < this.getTamanhoColuna()) {
      cont = 0;
      for (contLn = 0; contLn < this.getTamanhoLinha(); contLn++) {
        if (this.getValor(contLn, contCol) == 0) {
          cont++;
        }
      }

      if (cont > contMaisZerosColuna) {
        contMaisZerosColuna = cont;
        indiceMaisZerosColuna = contCol;
      }

      contCol++;
    }

    // Descobrir LINHA com mais zeros
    contLn = 0;
    while (contLn < this.getTamanhoLinha()) {
      cont = 0;
      for (contCol = 0; contCol < this.getTamanhoColuna(); contCol++) {
        if (this.getValor(contLn, contCol) == 0) {
          cont++;
        }
      }

      if (cont > contMaisZerosLinha) {
        contMaisZerosLinha = cont;
        indiceMaisZerosLinha = contLn;
      }

      contLn++;
    }

    // Descobrir FILA com mais zeros
    if (contMaisZerosLinha > contMaisZerosColuna) {
      filaMaisZeros = "l" + indiceMaisZerosLinha;
    } else {
      filaMaisZeros = "c" + indiceMaisZerosColuna;
    }

    return filaMaisZeros;
  }

  private int detOrdemNMenorCofator(Matriz mat) {
    int sinal, cofator, detTemp, resposta, contC, contL, numL, numC;
    Matriz matmenor;

    numL = this.getTamanhoLinha();
    numC = this.getTamanhoColuna();

    boolean maisZerosNaLinha =
      String.valueOf(this.getFilaMaisZeros().charAt(0)) == "l";

    // verifica se uma linha ou coluna tem mais zeros
    if (maisZerosNaLinha) {
      int linha = Character.getNumericValue(this.getFilaMaisZeros().charAt(1));
      resposta = 0;
      for (contC = 0; contC < mat.getTamanhoColuna(); contC++) {
        cofator = mat.getValor(linha, contC);
        sinal = this.calculaSinal(linha, contC);
        matmenor = new Matriz(numL - 1, numC - 1);
        this.copiaMatrizMaiorParaMenor(mat, matmenor, linha, contC);
        if (cofator != 0) {
          detTemp = matmenor.determinanteMenorCofator();
        } else {
          detTemp = 0;
        }
        resposta = resposta + (cofator * sinal * detTemp);
      }
      // coluna com  mais zeros
    } else {
      int coluna = Character.getNumericValue(this.getFilaMaisZeros().charAt(1));
      resposta = 0;
      for (contL = 0; contL < mat.getTamanhoLinha(); contL++) {
        cofator = mat.getValor(contL, coluna);
        sinal = this.calculaSinal(contL, coluna);
        matmenor = new Matriz(numL - 1, numC - 1);
        this.copiaMatrizMaiorParaMenor(mat, matmenor, contL, coluna);
        if (cofator != 0) {
          detTemp = matmenor.determinanteMenorCofator();
        } else {
          detTemp = 0;
        }
        resposta = resposta + (cofator * sinal * detTemp);
      }
    }

    return (resposta);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // MÉTODOS PRINCIPAIS

  public int determinanteBasico() {
    int ordem, det;

    ordem = this.retorneOrdem();
    det = 0;

    if (ordem > 0) {
      switch (ordem) {
        case 1:
          det = this.detOrdem1(this);
          break;
        case 2:
          det = this.detOrdem2(this);
          break;
        default:
          det = this.detOrdemNBasico(this);
          break;
      }
    } else {
      System.out.println("Matriz nao eh quadrada!! retornando 0");
    }

    return det;
  }

  public int determinanteMenorCofator() {
    int ordem, det;

    ordem = this.retorneOrdem();
    det = 0;

    if (ordem > 0) {
      switch (ordem) {
        case 1:
          det = this.detOrdem1(this);
          break;
        case 2:
          det = this.detOrdem2(this);
          break;
        default:
          det = this.detOrdemNMenorCofator(this);
          break;
      }
    } else {
      System.out.println("Matriz nao eh quadrada!! retornando 0");
    }

    return det;
  }

  public int determinanteTriangularizado() {
    int ordem, det;

    ordem = this.retorneOrdem();
    det = 0;

    if (ordem > 0) {
      switch (ordem) {
        case 1:
          det = this.detOrdem1(this);
          break;
        case 2:
          det = this.detOrdem2(this);
          break;
        default:
          det = this.determinantePorTriangularizacao();
          break;
      }
    } else {
      System.out.println("Matriz nao eh quadrada!! retornando 0");
    }

    return det;
  }

  public int determinantePorTriangularizacao() {
    /* Criamos uma cópia da matriz em double, pois, após multiplicar os elementos 
	pelos valores, afim de zerar linas, o valor do elemento será um número não inteiro.*/
    double[][] matrizDouble = new double[this.getTamanhoLinha()][this.getTamanhoColuna()];

    for (int linhas = 0; linhas < this.getTamanhoLinha(); linhas++) {
      for (int colunas = 0; colunas < this.getTamanhoLinha(); colunas++) {
        matrizDouble[linhas][colunas] = this.getValor(linhas, colunas);
      }
    }

    int ordem = this.retorneOrdem();
    int fatorDoSinal = 1;

    for (
      int linhaReferencia = 0;
      linhaReferencia < ordem - 1;
      linhaReferencia++
    ) {
      double max = Math.abs(matrizDouble[linhaReferencia][linhaReferencia]);
      int maxIndex = linhaReferencia;
      //encontra o maior valor em módulo na coluna, a partir de uma linha, para usá-lo como referência
      for (int coluna = linhaReferencia + 1; coluna < ordem; coluna++) {
        if (max < Math.abs(matrizDouble[coluna][linhaReferencia])) {
          max = Math.abs(matrizDouble[coluna][linhaReferencia]);
          maxIndex = coluna;
        }
      }

      //troca uma linha pela linha com o maior valor absoluto na coluna
      if (maxIndex != linhaReferencia) {
        double aux;
        fatorDoSinal = fatorDoSinal * (-1);
        for (
          int elementoDaLinha = 0;
          elementoDaLinha < ordem;
          elementoDaLinha++
        ) {
          aux = matrizDouble[linhaReferencia][elementoDaLinha];
          matrizDouble[linhaReferencia][elementoDaLinha] =
            matrizDouble[maxIndex][elementoDaLinha];
          matrizDouble[maxIndex][elementoDaLinha] = aux;
        }
      }

      //verifica se é não é uma matriz singular, ou seja, que possui o determinante igual a zero
      if (matrizDouble[linhaReferencia][linhaReferencia] != 0) {
        for (int linha = linhaReferencia + 1; linha < ordem; linha++) {
          // preenche os zeros abaixo da diagonal principal
          double fatorDaMultiplicacao =
            (-1) *
            matrizDouble[linha][linhaReferencia] /
            matrizDouble[linhaReferencia][linhaReferencia];
          matrizDouble[linha][linhaReferencia] = 0;
          // Soma os valores dos elementos abaixo, multiplicados pelo coeficiente que zera os valores
          for (int coluna = linhaReferencia + 1; coluna < ordem; coluna++) {
            matrizDouble[linha][coluna] =
              matrizDouble[linha][coluna] +
              fatorDaMultiplicacao *
              matrizDouble[linhaReferencia][coluna];
          }
        }
      } else {
        return 0;
      }
    }

    //calculo do determinante após a triangularização, utilizando apenas a diagonal principal
    double determinante = 1;
    for (
      int elementoDiagonal = 0;
      elementoDiagonal < ordem;
      elementoDiagonal++
    ) {
      determinante =
        determinante * matrizDouble[elementoDiagonal][elementoDiagonal];
    }
    return (int) Math.round(fatorDoSinal * determinante);
  }
}
