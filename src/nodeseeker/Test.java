package nodeseeker;

public class Test {
	public static void main(String[] args) {
		double d = Double.parseDouble("1,06E-14".replace(",", "."));
		long round = Math.round(d);
		System.out.println(round);
	}
}