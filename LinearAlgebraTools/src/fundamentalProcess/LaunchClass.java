package fundamentalProcess;

import java.util.ArrayList;

public class LaunchClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] debugMatrix = { { 8, -21 }, { 2, -5 } };
		Matrix alpha = new Matrix(debugMatrix);
		alpha.printMainMatrix();
		// double result = alpha.getDeterminant(debugMatrix);
		// System.out.println(result);
		// System.out.println(alpha.getCharacteristicEquation(debugMatrix));

		alpha.printAnswers(alpha.findEigenVectors(debugMatrix));

	}

}
