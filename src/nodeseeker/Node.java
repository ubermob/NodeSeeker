package nodeseeker;

public class Node {

	private final int id, x, y, z;

	public Node(int id, double x, double y, double z) {
		this.id = id;
		this.x = (int) Math.round(x * NodeSeeker.multiplier);
		this.y = (int) Math.round(y * NodeSeeker.multiplier);
		this.z = (int) Math.round(z * NodeSeeker.multiplier);
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

	public int getHash() {
		return x + y + z;
	}

	@Override
	public String toString() {
		return String.format(NodeSeeker.properties.getProperty("node_to_string"), id, x, y, z);
	}
}