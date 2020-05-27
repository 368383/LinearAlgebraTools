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
			System.out.println("Dimension are not suitable to be inverted");
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
		//System.out.println("Set of Matrix");

		//printMatrix(reductionMatrix);
		double[][] alpha = rowReducedForm(reductionMatrix);
		//printMatrix(alpha);
		// System.out.println("alpha length/2 " + alpha.length);
		for (int row = 0; row < alpha.length; row++) {
			for (int col = alpha[0].length / 2; col < alpha[0].length; col++) {
			//	System.out.println(alpha[row][col] + " Coordinates (" + row + "," + col + ")");
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

	public void findDeterminant(double[][] debugMatrix) {

		ArrayList<Double> coFactors = new ArrayList<Double>();
		for (int row = 0; row < debugMatrix.length; row++) {
			for (int col = 0; col < debugMatrix[0].length; col++) {
				coFactors.add(debugMatrix[row][col]);
			}
		}
		double result = coFactorDeterminant(coFactors);
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
