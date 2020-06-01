package fundamentalProcess;

import java.util.ArrayList;

public class EquationSolver {
	private static String equation;
	private static ArrayList<String> groupings = new ArrayList<String>();
	private static ArrayList<String> uniqueLetters = new ArrayList<String>();

	public EquationSolver(String input) {
		// TODO Auto-generated constructor stub
		equation = input;
	}

	public static ArrayList<Double> solveEquation(ArrayList<Double> coefficients) {
		int size = coefficients.size();
		switch (size) {
		case 2:
		case 3:
			return quadratic(coefficients.get(0), coefficients.get(1), coefficients.get(2));
		default:
			return null;
		}
	}

	private static ArrayList<Double> linear(double a, double c) {
		ArrayList<Double> ans = new ArrayList<Double>();
		ans.add(c / a);
		return ans;
	}

	private static ArrayList<Double> quadratic(double a, double b, double c) {
		System.out.println(a + "\t" + b + "\t" + c);
		ArrayList<Double> ans = new ArrayList<Double>();
		double result = ((-1) * b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
		ans.add(result);
		double result2 = ((-1) * b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
		ans.add(result2);
		return ans;
	}

//	public static void main(String args[]) {
//		String alpha = "(8)";
//		System.out.println(Double.valueOf(alpha));
////		equation = "4*(-1)*(-8)";
////		findAnswer();
//	}

	public static ArrayList<Double> findAnswer() {
		getGroupings(equation);
		int numVars = numberOfVars(equation);
		switch (numVars) {
		case 1:
			// System.out.println("Single Var Operation");
			return singleVarEval(equation);
		default:
			return null;
		}
	}

	public static ArrayList<Double> singleVarEval(String equation) {
		ArrayList<Double> solutions = new ArrayList<Double>();
		for (String current : groupings) {

		}
		return solutions;
	}

	private static String simplification(String input) {
		for (String current : uniqueLetters) {
			if (input.contains(current)) {

			}
		}

		ArrayList<Double> values = new ArrayList<Double>();
		double answer = 0;
		int startIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.substring(i, i + 1).equals("*") || input.substring(i, i + 1).equals("/")) {
				endIndex = i + 1;
				String item = input.substring(startIndex, endIndex);
				startIndex = endIndex;
				values.add(Double.valueOf(item));
			}
		}
		return null;
	}

	private static int numberOfVars(String equation) {
		int count = 0;
		for (int i = 0; i < equation.length(); i++) {
			if (equation.substring(i, i + 1).matches("^[a-zA-Z]*$")
					&& !uniqueLetters.contains(equation.substring(i, i + 1))) {
				uniqueLetters.add(equation.substring(i, i + 1));
				count++;
			}
		}
		return count;
	}

	private static void getGroupings(String input) {
		int startIndex = 0;
		int endIndex = 0;
		boolean parse = false;
		int count = 0;
		int countA = 0;
		for (int i = 0; i < input.length(); i++) {
			String equationEval = input.substring(i, i + 1);
			// System.out.println("eval " + equationEval);

			if (equationEval.equals("(")) {
				count++;
				parse = true;
				continue;
			}
			if (equationEval.equals(")")) {
				countA++;
				parse = false;
			}
			if (!parse && ((equationEval.equals("+") || equationEval.equals("-")) || i == input.length() - 1)) {
				// System.out.println("CYCLE " + i);

				endIndex = i;
				if (i == input.length() - 1) {
					// System.out.println("start index "+startIndex+ "\t"+input.length());
					groupings.add(input.substring(startIndex));
					break;
				}
				groupings.add(input.substring(startIndex, endIndex));
				groupings.add(input.substring(endIndex, endIndex + 1));
				startIndex = endIndex + 1;
			}
		}
		// System.out.println("Count 1 and 2: " + count + "\t" + countA);
		System.out.println("GROUPINGS " + groupings);
	}

}
