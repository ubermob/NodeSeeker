package nodeseeker;

public class Node {

	private final int id;
	private final int x, y, z;

	public Node(int id, double x, double y, double z) {
		this.id = id;
		this.x = (int) Math.round(x * 1000);
		this.y = (int) Math.round(y * 1000);
		this.z = (int) Math.round(z * 1000);
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}