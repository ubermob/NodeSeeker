package nodeseeker.node;

import nodeseeker.NodeSeeker;
import nodeseeker.NodeSeekerProperties;

/**
 * Describes a node in 3D space.
 *
 * @author Andrey Korneychuk on 17-Sep-21
 * @version 1.1
 */
public class Node {

	private final int id, x, y, z;
	private final NodeSeekerProperties properties;

	/**
	 * Multiplier must be set in {@link NodeSeeker} before this exemplar will be create.
	 * Default value of multiplier you can see in properties file.
	 *
	 * @param id         id of node
	 * @param x          x coordinate
	 * @param y          y coordinate
	 * @param z          z coordinate
	 * @param multiplier decimal view of node coordinates accuracy
	 * @param properties {@link NodeSeekerProperties} loaded properties
	 */
	public Node(int id, double x, double y, double z, int multiplier, NodeSeekerProperties properties) {
		this.id = id;
		this.x = (int) Math.round(x * multiplier);
		this.y = (int) Math.round(y * multiplier);
		this.z = (int) Math.round(z * multiplier);
		this.properties = properties;
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
	 * @return hash of node. It is a sum of 3 multiplied coordinates
	 */
	public int getHash() {
		return x + y + z;
	}

	/**
	 * @return formatted string
	 */
	@Override
	public String toString() {
		return String.format(properties.getProperty("node_to_string"), id, x, y, z);
	}
}