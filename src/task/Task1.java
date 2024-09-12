package task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task1 {

	static class SalesExecutive {
		String id;
		int sales;
		SalesExecutive left;
		SalesExecutive right;

		public SalesExecutive(String id, int sales) {
			this.id = id;
			this.sales = sales;
			this.left = null;
			this.right = null;
		}
	}

	static class Organization {
		static double totalBonus = 3000000.0; 

		public static List<SalesExecutive> lineWithHighestSales(SalesExecutive root) {

			if (root == null) {
				return new ArrayList<>();
			}

			List<SalesExecutive> leftLine = lineWithHighestSales(root.left);
			List<SalesExecutive> rightLine = lineWithHighestSales(root.right);

			int leftSales = leftLine.stream().mapToInt(exec -> exec.sales).sum();
			int rightSales = rightLine.stream().mapToInt(exec -> exec.sales).sum();

			List<SalesExecutive> bestLine = (leftSales > rightSales) ? leftLine : rightLine;
			bestLine.add(0, root);
			return bestLine;
		}

		public static void distributeBonuses(List<SalesExecutive> line) {

			int totalSales = line.stream().mapToInt(exec -> exec.sales).sum();

			double budgetEfficiencyBonus = totalBonus * 0.25;
			double salesEfficiencyBonus = totalBonus * 0.75;

			Map<SalesExecutive, Integer> subordinatesCount = new HashMap<>();
			for (SalesExecutive exec : line) {
				subordinatesCount.put(exec, getSubordinateCount(exec));
			}

			int totalSubordinates = subordinatesCount.values().stream().mapToInt(Integer::intValue).sum();

			for (SalesExecutive exec : line) {

				int subCount = subordinatesCount.get(exec);
				double budgetBonus = (subCount / (double) totalSubordinates) * budgetEfficiencyBonus;

				double salesBonus = (exec.sales / (double) totalSales) * salesEfficiencyBonus;

				double totalExecBonus = budgetBonus + salesBonus;

				System.out.println(
						"Executive ID: " + exec.id + " | Sales: " + exec.sales + "M | Bonus: " + totalExecBonus);
			}
		}

		public static int getSubordinateCount(SalesExecutive exec) {
			if (exec == null)
				return 0;
			return 1 + getSubordinateCount(exec.left) + getSubordinateCount(exec.right);
		}

		public static void main(String[] args) {

			SalesExecutive se1 = new SalesExecutive("SE1", 10);
			SalesExecutive se2 = new SalesExecutive("SE2", 12);
			SalesExecutive se3 = new SalesExecutive("SE3", 7);
			SalesExecutive se4 = new SalesExecutive("SE4", 4);
			SalesExecutive se5 = new SalesExecutive("SE5", 17);
			SalesExecutive se6 = new SalesExecutive("SE6", 6);
			SalesExecutive se7 = new SalesExecutive("SE7", 3);
			SalesExecutive se8 = new SalesExecutive("SE8", 9);
			SalesExecutive se9 = new SalesExecutive("SE9", 10);
			SalesExecutive se10 = new SalesExecutive("SE10", 11);
			SalesExecutive se11 = new SalesExecutive("SE11", 18);
			SalesExecutive se12 = new SalesExecutive("SE12", 2);
			SalesExecutive se13 = new SalesExecutive("SE13", 13);
			SalesExecutive se14 = new SalesExecutive("SE14", 15);
			SalesExecutive se15 = new SalesExecutive("SE15", 20);
			SalesExecutive se16 = new SalesExecutive("SE16", 8);
			SalesExecutive se17 = new SalesExecutive("SE17", 18);

			se1.left = se2;
			se1.right = se3;
			se2.left = se4;
			se2.right = se5;
			se3.left = se6;
			se3.right = se7;
			se4.right = se8;
			se5.left = se9;
			se5.right = se10;
			se6.left = se11;
			se6.right = se12;
			se7.left = se13;
			se7.right = se14;
			se8.left = se15;
			se9.left = se16;
			se16.right = se17;

			List<SalesExecutive> highestSalesLine = lineWithHighestSales(se1);

			distributeBonuses(highestSalesLine);
		}
	}

}
