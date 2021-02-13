package fundamentalProcess;

import java.util.ArrayList;

public class LaunchClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] debugMatrix = { { 8, -21, 5 }, { 2, -5, 9 }, { 1, 2, 3 } };
		Matrix alpha = new Matrix(debugMatrix);
		alpha.printMainMatrix();
		// double result = alpha.getDeterminant(debugMatrix);
		// System.out.println(result);
		// System.out.println(alpha.getCharacteristicEquation(debugMatrix));
		System.out.println("REDUCED");
		double[][] output = alpha.rowEchelonForm(debugMatrix).clone();
		double a = alpha.getDeterminant(debugMatrix);
		alpha.printMatrix(output);
		System.out.println("DOUBLE " + a);
		// alpha.printAnswers(alpha.findEigenVectors(debugMatrix));

	}

}
