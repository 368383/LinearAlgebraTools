package fundamentalProcess;

import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {
	public static double[][] debugMatrix = { { 1, 3, 5, 0 }, { 1, 0, 4, 20 }, { 1, 4, 4, 3 }, { 1, 3, 5, 2 } };
	public double[][] reduced;
	public double[][] mainMatrix;
	public double[][] transpose;

	public double row;
	public double col;

//	public static void main(String args[]) {

//		printMatrix(debugMatrix);
//		findDeterminant(debugMatrix);
//	}

	public Matrix(int row, int col) {

		this.row = row;
		this.col = col;
		mainMatrix = new double[row][col];
		System.out.println("INSERT FOR DIM " + row + " x " + col);
		readInput();
	}

	public Matrix(double[][] matrix) {
		mainMatrix = matrix;
		row = mainMatrix.length;
		col = mainMatrix[0].length;
	}

	private void readInput() {
		Scanner sc = new Scanner(System.in);
		int input;
		for (int row = 0; row < mainMatrix.length; row++) {
			System.out.println("INPUT ROW " + row + " VALUES");
			for (int col = 0; col < mainMatrix[row].length; col++) {
				mainMatrix[row][col] = sc.nextInt();
			}
		}
		sc.close();
	}

	public double[][] getReducedMatrix() {
		return reduced;
	}

	public double[][] getTranspose() {
		return transpose;
	}

	public double[][] getMainMatrix() {
		return debugMatrix;
	}

	public void printMainMatrix() {

		System.out.println("Main Matrix Values");
		for (int row = 0; row < mainMatrix.length; row++) {
			System.out.print("[ ");
			for (int col = 0; col < mainMatrix[row].length; col++) {
				System.out.print(mainMatrix[row][col] + " ");
			}
			System.out.print("]");
			System.out.println();
		}
	}

	public void printReducedMatrix() {
		System.out.println("Reduced Matrix Values");
		for (int row = 0; row < reduced.length; row++) {
			System.out.print("[ ");
			for (int col = 0; col < reduced[row].length; col++) {
				System.out.print(reduced[row][col] + " ");
			}
			System.out.print("]");
			System.out.println();
		}
	}

	public void printMatrix(double[][] inputMatrix) {

		for (int row = 0; row < inputMatrix.length; row++) {

			System.out.print("[ ");
			for (int col = 0; col < inputMatrix[row].length; col++) {
				System.out.print(inputMatrix[row][col] + " ");
			}
			System.out.print("]");
			System.out.println();
		}
	}

	public void printMatrix(double[] inputMatrix) {

		System.out.print("[ ");
		for (int col = 0; col < inputMatrix.length; col++) {
			System.out.print(inputMatrix[col] + " ");
		}
		System.out.print("]");
		System.out.println();

	}

	public void transpose(double[][] inputMatrix) {
		transpose = new double[inputMatrix[0].length][inputMatrix.length];
		for (int row = 0; row < inputMatrix.length; row++) {
			for (int col = 0; col < inputMatrix[0].length; col++) {
				transpose[col][row] = inputMatrix[row][col];
			}
		}
	}

	public double[][] findInverse(double[][] inputMatrix) {
		if (inputMatrix.length != inputMatrix[0].length) {
			System.out.println("Dimensions are not suitable to be inverted");
		}
		int pivOffSet = 0;
		double[][] reductionMatrix = new double[inputMatrix.length][inputMatrix[0].length * 2];
		for (int row = 0; row < inputMatrix.length; row++) {
			for (int col = 0; col < inputMatrix[0].length; col++) {
				reductionMatrix[row][col] = inputMatrix[row][col];
			}
			reductionMatrix[row][inputMatrix[0].length + pivOffSet] = 1;
			pivOffSet++;
		}
		// System.out.println("Set of Matrix");

		// printMatrix(reductionMatrix);
		double[][] alpha = rowReducedForm(reductionMatrix);
		// printMatrix(alpha);
		// System.out.println("alpha length/2 " + alpha.length);
		for (int row = 0; row < alpha.length; row++) {
			for (int col = alpha[0].length / 2; col < alpha[0].length; col++) {
				// System.out.println(alpha[row][col] + " Coordinates (" + row + "," + col +
				// ")");
				inputMatrix[row][col - alpha[0].length / 2] = alpha[row][col];
			}
		}
		return inputMatrix;
	}

	public double[][] rowEchelonForm(double[][] inputMatrix) {

		for (int baseColPos = 0; baseColPos < inputMatrix[0].length; baseColPos++) {
			int baseRowPos = baseColPos;
			// ROW REDUCTION PORTION

			if (inputMatrix.length < inputMatrix[0].length && inputMatrix.length - 1 < baseColPos) {
				break;
			}
			boolean modify = false;
			inputMatrix[baseRowPos] = scaleToPivot(inputMatrix[baseRowPos], baseColPos);
			for (int row = baseRowPos + 1; row < inputMatrix.length; row++) {

				for (int modifyCol = baseColPos; modifyCol < inputMatrix[0].length; modifyCol++) {
					if (inputMatrix[baseRowPos][modifyCol] == 0) {
						baseColPos++;
						if (baseColPos >= inputMatrix[0].length) {
							baseColPos--;
							break;
						}

						modify = true;
					}
					break;
				}
				if (modify) {
					inputMatrix[baseRowPos] = scaleToPivot(inputMatrix[baseRowPos], baseColPos);
					modify = false;
				}
				double scaleFactor = (double) inputMatrix[row][baseColPos] / (inputMatrix[baseRowPos][baseColPos]);
				if (Double.isNaN(scaleFactor) || Double.isInfinite(scaleFactor)) {

					continue;
				}

				for (int col = 0; col < inputMatrix[0].length; col++) {

					inputMatrix[row][col] = inputMatrix[row][col] - inputMatrix[baseRowPos][col] * scaleFactor;
				}

			}

			// FORMAT PORTION
			// System.out.println("NEW CYCLE ________________");
			// printMatrix(inputMatrix);
			for (int col = baseColPos + 1; col < inputMatrix[0].length; col++) {
				int indexZero = 0;
				int indexNonZero = 0;
				for (int row = baseRowPos + 1; row < inputMatrix.length; row++) {
					// System.out.println("ROW and COL " + row + "\t" + col);
					if (inputMatrix[row][col] > -0.000000001 && inputMatrix[row][col] < 0.000000001) {
						indexZero = row;
					} else {
						indexNonZero = row;
					}
					if ((indexZero != 0 && indexNonZero != 0) && indexZero != inputMatrix.length - 1) {
						inputMatrix = swapRows(indexZero, indexNonZero, inputMatrix);
					}
				}
			}
		}
		return inputMatrix;
	}

	public double[][] rowReducedForm(double[][] inputMatrix) {
		inputMatrix = rowEchelonForm(inputMatrix);
//		System.out.println("ROW ECHELON FORM");
//		printMatrix(inputMatrix);
		int baseCol = inputMatrix[0].length - 1;
		int baseRow = 0;
		int baseColIndex = baseCol;
		for (int pivSearchRow = inputMatrix.length - 1; pivSearchRow >= 1; pivSearchRow--) {
			for (int pivSearchCol = 0; pivSearchCol < inputMatrix[0].length; pivSearchCol++) {
				if (inputMatrix[pivSearchRow][pivSearchCol] == 1) {
					baseCol = pivSearchCol;
					baseRow = pivSearchRow;
					baseColIndex = baseCol - 1;
					break;
				}
			}
//			System.out.println("base row " + baseRow + "\t base col " + baseCol);
			for (int row = baseRow; row >= 1; row--) {
				double scaleFactor = -1 * inputMatrix[row - 1][baseCol] / inputMatrix[baseRow][baseCol];
//				System.out.println("Scale Factor " + scaleFactor + " baseRow and baseCol " + baseRow + " " + baseCol
//						+ "\t value: " + inputMatrix[baseRow][baseCol] + "\trow " + (row - 1) + " " + baseCol + "\t"
//						+ inputMatrix[row - 1][baseCol]);
				for (int col = 0; col < inputMatrix[0].length; col++) {
					inputMatrix[row - 1][col] = inputMatrix[baseRow][col] * scaleFactor + inputMatrix[row - 1][col];
				}
			}
//			System.out.println("CYCLE NEW");
//			printMatrix(inputMatrix);

		}
		return inputMatrix;

	}

	public double getDeterminant(double[][] inputMatrix) {
		if (inputMatrix[0].length != inputMatrix.length) {
			System.out.println("INVALID DIM");
			return 0;
		}
		ArrayList<Double> coFactors = new ArrayList<Double>();
		for (int row = 0; row < inputMatrix.length; row++) {
			for (int col = 0; col < inputMatrix[0].length; col++) {
				coFactors.add(inputMatrix[row][col]);
			}
		}
		return coFactorDeterminant(coFactors);
	}

	private double coFactorDeterminant(ArrayList<Double> list) {
		int matrixSize = (int) Math.sqrt(list.size());
		// System.out.println("matrix size " + matrixSize);
		if (matrixSize == 2) {
			// System.out.println("list values " + list);
			return list.get(0) * list.get(3) - list.get(1) * list.get(2);
		}
		double dit = 0;
		for (int baseIndex = 0; baseIndex < matrixSize; baseIndex++) {
			ArrayList<Double> remainFact = new ArrayList<Double>();

			double piv = list.get(baseIndex);
			// System.out.println("PIVOTS " + piv + " MATRIX SIZE " + matrixSize +
			// "\tbaseIndex " + baseIndex);

			double multVal = piv;
			if (baseIndex % 2 != 0) {
				multVal = multVal * -1;
			}
			// System.out.println("index begin " + baseIndex * matrixSize);
			// remainFact.clear();
			for (int index = (int) (matrixSize); index < list.size(); index++) {
				// System.out.println(index);

				if ((index - baseIndex) % matrixSize != 0) {
					remainFact.add(list.get(index));
				}
			}
			// System.out.println("added values" + remainFact);
			double combo = multVal * coFactorDeterminant(remainFact);
			// System.out.println(combo);
			dit = dit + combo;

		}
		return dit;
	}

	private String findDeterminantString(String[][] debugMatrix) {
		if (debugMatrix.length != debugMatrix[0].length) {
			System.out.println("Invalid dimensions for given operation. Must be Square");
		}
		ArrayList<String> coFactors = new ArrayList<String>();
		for (int row = 0; row < debugMatrix.length; row++) {
			for (int col = 0; col < debugMatrix[0].length; col++) {
				coFactors.add((debugMatrix[row][col]));
			}
		}
		return coFactorDeterminantString(coFactors);
	}

	public void printAnswers(ArrayList<Double> alpha) {
		System.out.println(alpha);
	}

	public ArrayList<Double> findEigenVectors(double[][] inputMatrix) {
		ArrayList<double[][]> vectors = new ArrayList<double[][]>();
//		double[][] testVector = new double[inputMatrix.length][1];
//		for (int i = 0; i < testVector.length; i++) {
//			testVector[i][0] = i + 1;
//		}
		double[][] testVector = { { 1 }, { 0 } };
		vectors.add(testVector);
		vectors.add(matrixMultiplication(inputMatrix, testVector));
		// (matrixMultiplication(inputMatrix, testVector));
		for (int i = 0; i < inputMatrix.length - 1; i++) {
			double[][] finalMatrix = inputMatrix.clone();

			for (int k = 0; k < i + 1; k++) {
				finalMatrix = matrixMultiplication(finalMatrix, inputMatrix);
			}
			vectors.add(matrixMultiplication(finalMatrix, testVector));
			// printMatrix(matrixMultiplication(finalMatrix, testVector));
		}
		double[][] eigenMatrix = new double[inputMatrix.length][inputMatrix.length + 2];
		int eigenCol = 0;
		for (double[][] current : vectors) {
			for (int row = 0; row < current.length; row++) {
				eigenMatrix[row][eigenCol] = current[row][0];
			}
			eigenCol++;
		}
		// printMatrix(eigenMatrix);
		// System.out.println("FINAL");
		eigenMatrix = rowReducedForm(eigenMatrix);
		ArrayList<double[][]> matricies = solveReductionMatrix(eigenMatrix);
		ArrayList<Double> listOfCoefficients = new ArrayList<Double>();
		listOfCoefficients.add((double) 1);

		for (double[][] current : matricies) {
			for (int i = current.length - 2; i >= 0; i--) {
				listOfCoefficients.add(current[i][0]);
			}
		}
		System.out.println(listOfCoefficients);
		ArrayList<Double> answers = EquationSolver.solveEquation(listOfCoefficients);
		return answers;
	}

	public void solveEquationMatrix(double[][] matrix) {
		System.out.println(
				"NOTE, IF MATRIX IS ORIGINAL, THEN THERE ARE NO FREE VARS AND THUS ALL VARS HAVE ANS. OTHERWISE, FREE VARS EXIST AND THE LAST ELEMENT IN THE MATRICIES REPRESENT THE FREE VAR");
		ArrayList<double[][]> ans = solveReductionMatrix(matrix);
		for (double[][] currentMatrix : ans) {
			printMatrix(currentMatrix);
		}
	}

	private ArrayList<double[][]> solveReductionMatrix(double[][] matrix) {
		// determine pivot indexes
		ArrayList<Integer> listOfPivots = new ArrayList<Integer>();
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[0].length; col++) {
				if (matrix[row][col] == 1) {

					listOfPivots.add(col);
					break;
				}
			}
		}
		ArrayList<Integer> listOfPotentialPivots = new ArrayList<Integer>();
		for (int i = 0; i < matrix[0].length - 1; i++) {
			listOfPotentialPivots.add(i);
		}
		for (Integer currentPivot : listOfPivots) {
			if (listOfPotentialPivots.contains(currentPivot)) {
				listOfPotentialPivots.remove(currentPivot);
			}
		}
		ArrayList<double[][]> listOfFreeMatrix = new ArrayList<double[][]>();

		if (listOfPotentialPivots.size() == 0) {
			listOfFreeMatrix.add(matrix);
			return listOfFreeMatrix;
		}
		// System.out.println("HEI");
		for (Integer currentPiv : listOfPotentialPivots) {
			// System.out.println("PIVOT "+currentPiv);
			double[][] freeVarMatrix = new double[matrix.length + 1][1];
			for (int row = 0; row < freeVarMatrix.length - 1; row++) {
				freeVarMatrix[row][0] = -1 * matrix[row][currentPiv];
			}
			freeVarMatrix[matrix.length][0] = currentPiv;
			listOfFreeMatrix.add(freeVarMatrix);
		}
		return listOfFreeMatrix;

	}

	public double[][] matrixMultiplication(double[][] first, double[][] second) {
		if (first[0].length != second.length) {
			System.out.println("INVALID DIM");
		}
		// printMatrix(first);
		// printMatrix(second);
		double[][] result = new double[first.length][second[0].length];
		for (int row = 0; row < result.length; row++) {
			int resultCol = 0;
			/// System.out.println("CYCLE " + row);
			for (int secCol = 0; secCol < second[0].length; secCol++) {
				double plug = 0;
				for (int col = 0; col < result[0].length; col++) {
					plug = plug + first[row][col] * second[col][secCol];
				}
				result[row][resultCol] = plug;
				resultCol++;
			}
		}
		return result;

	}

	public String getCharacteristicEquation(double[][] inputMatrix) {
		String[][] stringMatrix = new String[inputMatrix.length][inputMatrix[0].length];
		for (int row = 0; row < inputMatrix.length; row++) {
			for (int col = 0; col < inputMatrix[0].length; col++) {
				if (row == col) {
					stringMatrix[row][col] = "(" + Double.toString(inputMatrix[row][col]) + "-x)";
				} else {
					stringMatrix[row][col] = Double.toString(inputMatrix[row][col]);
				}
			}
		}
		return findDeterminantString(stringMatrix);
	}

	private String coFactorDeterminantString(ArrayList<String> list) {
		int matrixSize = (int) Math.sqrt(list.size());
		// System.out.println("matrix size " + matrixSize);
		if (matrixSize == 2) {
			// System.out.println("list values " + list);
			return list.get(0) + "*" + list.get(3) + "-" + list.get(1) + "*" + list.get(2);
		}
		String dit = "0";
		for (int baseIndex = 0; baseIndex < matrixSize; baseIndex++) {
			ArrayList<String> remainFact = new ArrayList<String>();

			String piv = list.get(baseIndex);
			// System.out.println("PIVOTS " + piv + " MATRIX SIZE " + matrixSize +
			// "\tbaseIndex " + baseIndex);

			String multVal = (piv);
			if (baseIndex % 2 != 0) {
				multVal = "(-1)*" + multVal;
			}
			// System.out.println("index begin " + baseIndex * matrixSize);
			// remainFact.clear();
			for (int index = (int) (matrixSize); index < list.size(); index++) {
				// System.out.println(index);

				if ((index - baseIndex) % matrixSize != 0) {
					remainFact.add(list.get(index));
				}
			}
			// System.out.println("added values" + remainFact);
			String combo = (multVal) + "*" + coFactorDeterminantString(remainFact);
			// System.out.println(combo);
			dit = dit + "+" + combo;

		}
		return dit;
	}

	public double[][] swapRows(int row1, int row2, double[][] inputMatrix) {
		// System.out.println("ROWS TO SWAP " + row1 + "\t" + row2);
		double[] holder = inputMatrix[row2];
		inputMatrix[row2] = inputMatrix[row1];
		inputMatrix[row1] = holder;
		return inputMatrix;
	}

	private void findAndScaleRow(double[] input) {
		double scaleValue = 1;
		for (int i = 0; i < input.length; i++) {
			if (input[i] != 0.0) {
				scaleValue = input[i];
				System.out.println("SCALE VALUE FOR MANUAL SCALING " + scaleValue);
			}
		}
		for (int i = 0; i < input.length; i++) {
			input[i] = 1 / scaleValue * input[i];
		}

	}

	private double[] scaleToPivot(double[] input, int beginIndex) {

		if (input[beginIndex] == 0) {
			// System.out.println("UNABLE TO SCALE FACTOR");
		} else {
			double scaleFactor = 1 / (input[beginIndex]);
			for (int i = beginIndex; i < input.length; i++) {
				input[i] = scaleFactor * input[i];
			}
		}
		return input;
	}

	private boolean isPivot(double input) {
		return input == 1;
	}
}
