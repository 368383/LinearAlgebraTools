package fundamentalProcess;

public class LaunchClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] debugMatrix = { { 1, 3, 5 }, { 1, 0, 4 }, { 1, 4, 4 } };
		Matrix alpha = new Matrix(debugMatrix);
		alpha.printMatrix(alpha.findInverse(debugMatrix));
		
	}

}
