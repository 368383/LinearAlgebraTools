package fundamentalProcess;

public class LaunchClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Matrix alpha = new Matrix(2, 2);
		alpha.printMainMatrix();
		alpha.rowEchelonForm();
		alpha.rowReducedForm();
		alpha.printReducedMatrix();
		System.out.println();
	}

}
