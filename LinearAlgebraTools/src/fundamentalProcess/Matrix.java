package fundamentalProcess;

import java.util.Scanner;

public class Matrix {
	public static double[][] debugMatrix = { { 1, 3, 5, 1 }, { 1, 0, 4, 67 }, { 1, 4, 4, 6 } };
	public double[][] reduced;
	public double[][] mainMatrix;
	public double row;
	public double col;

//	public static void main(String args[]) {
//
//		row = mainMatrix.length;
//		col = mainMatrix[0].length;
//		rowEchelonForm();
//		//System.out.println("FINAL OUTPUT");
//		//printMatrix(reduced);
//		rowReducedForm();
//
//	}

	public Matrix(int row, int col) {


		this.row = row;
		this.col = col;
		mainMatrix = new double[row][col];
		System.out.println("INSERT FOR DIM "+row+" x "+col);
		readInput();
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

	public void rowEchelonForm() {

		// System.out.println("BEFORE CALCULATIONS");
		reduced = mainMatrix.clone();
		// printMatrix(reduced);

		for (int baseColPos = 0; baseColPos < reduced[0].length; baseColPos++) {
			// System.out.println("CYCLE " + baseColPos + "____________________________");
			int baseRowPos = baseColPos;
			// ROW REDUCTION PORTION

			if (reduced.length < reduced[0].length && reduced.length - 1 < baseColPos) {
				break;
			}
			boolean modify = false;
			reduced[baseRowPos] = scaleToPivot(reduced[baseRowPos], baseColPos);
			for (int row = baseRowPos + 1; row < reduced.length; row++) {

				for (int modifyCol = baseColPos; modifyCol < reduced[0].length; modifyCol++) {
					// System.out.println("MODIFY COL " + row + "\t" + modifyCol);
					if (reduced[baseRowPos][modifyCol] == 0) {
						baseColPos++;
						if (baseColPos >= reduced[0].length) {
							baseColPos--;
							break;
						}

						modify = true;
					}
					break;
				}
				if (modify) {
					reduced[baseRowPos] = scaleToPivot(reduced[baseRowPos], baseColPos);
					modify = false;
				}
				double scaleFactor = (double) reduced[row][baseColPos] / (reduced[baseRowPos][baseColPos]);
				if (Double.isNaN(scaleFactor) || Double.isInfinite(scaleFactor)) {
//					System.out
//							.println("ROW " + row + "\t" + baseColPos + " BASE ROW " + baseRowPos + "\t" + baseColPos);
//					System.out.println("INVALID");
					continue;
				}
				// System.out.println("SCALED FACTOR " + scaleFactor);
				for (int col = 0; col < reduced[0].length; col++) {
					// System.out.println("ROW " + row + "\t" + col + " BASE ROW " + baseRowPos +
					// "\t" + col);
					reduced[row][col] = reduced[row][col] - reduced[baseRowPos][col] * scaleFactor;
				}

			}

			// FORMAT PORTION

			for (int col = baseColPos + 1; col < reduced[0].length; col++) {
				int indexZero = 0;
				int indexNonZero = 0;
				for (int row = baseRowPos + 1; row < reduced.length; row++) {
					if (reduced[row][col] > -0.000000001 && reduced[row][col] < 0.000000001) {
						indexZero = row;
					} else {
						indexNonZero = row;
					}
					if ((indexZero != 0 && indexNonZero != 0) && indexZero != reduced.length - 1) {
						swapRows(indexZero, indexNonZero);
					}
				}
			}
			// printMatrix(reduced);
		}
//		System.out.println("ROW ECHELON FORM");
//		printMatrix(reduced);

	}

	public void rowReducedForm() {

		int baseCol = reduced[0].length - 1;
		int baseRow = 0;
		int baseColIndex = baseCol;
		for (int pivSearchRow = reduced.length - 1; pivSearchRow >= 1; pivSearchRow--) {
			// System.out.println("CYCLE " + (reduced.length - pivSearchRow) +
			// "_________________");
			for (int pivSearchCol = baseColIndex; pivSearchCol >= 0; pivSearchCol--) {
//				System.out.println(
//						pivSearchRow + "\t" + pivSearchCol + "\t test value " + reduced[pivSearchRow][pivSearchCol]);

				if (reduced[pivSearchRow][pivSearchCol] == 1) {
					baseCol = pivSearchCol;
					baseRow = pivSearchRow;
					// System.out.println("PIVOT COORDINATES " + baseCol + "\t " + baseRow);
					baseColIndex = baseCol - 1;
					break;
				}
			}
			for (int row = baseRow; row >= 1; row--) {
				double scaleFactor = -1 * reduced[row - 1][baseCol] / reduced[baseRow][baseCol];
				// System.out.println("row and col " + row + "\t" + baseCol + " Scale Factor " +
				// scaleFactor);
				for (int col = 0; col < reduced[0].length; col++) {
					reduced[row - 1][col] = reduced[baseRow][col] * scaleFactor + reduced[row - 1][col];
				}
			}

		}
//		System.out.println("ROW REDUCED FORM");
//		printMatrix(reduced);

	}

	private void swapRows(int row1, int row2) {
		double[] holder = reduced[row2];
		reduced[row2] = reduced[row1];
		reduced[row1] = holder;
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
