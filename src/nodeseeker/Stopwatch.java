package nodeseeker;

public class Stopwatch {

	private final long time;

	public Stopwatch() {
		this.time = System.currentTimeMillis();
	}

	public void print() {
		System.out.println(System.currentTimeMillis() - time + " ms");
	}
}