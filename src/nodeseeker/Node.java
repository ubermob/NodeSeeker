package nodeseeker;

/**
 * Describes a node in space.
 *
 * @author Andrey Korneychuk on 17-Sep-21
 * @version 1.0
 */
public class Node {

	private final int id, x, y, z;

	/**
	 * Multiplier must be set in {@link NodeSeeker} before this exemplar will be create.
	 * Default value of multiplier you can see in properties file.
	 *
	 * @param id id of node
	 * @param x  x coordinate
	 * @param y  y coordinate
	 * @param z  z coordinate
	 */
	public Node(int id, double x, double y, double z) {
		this.id = id;
		this.x = (int) Math.round(x * NodeSeeker.multiplier);
		this.y = (int) Math.round(y * NodeSeeker.multiplier);
		this.z = (int) Math.round(z * NodeSeeker.multiplier);
	}

	/**
	 * @return id of node
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return x multiplied coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y multiplied coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return z multiplied coordinate
	 */
	public int getZ() {
		return z;
	}

	/**
	 * @return hash of node.
	 * It is a sum of 3 multiplied coordinates
	 */
	public int getHash() {
		return x + y + z;
	}

	@Override
	public String toString() {
		return String.format(NodeSeeker.properties.getProperty("node_to_string"), id, x, y, z);
	}
}