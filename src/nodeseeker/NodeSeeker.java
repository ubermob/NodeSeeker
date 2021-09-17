package nodeseeker;

import utools.stopwatch.Stopwatch;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Manage this utility.
 *
 * @author Andrey Kornechuk on 17-Sep-21
 * @version 1.0
 */
public class NodeSeeker {

	protected static Properties properties;
	private static byte nodeAccuracy;
	protected static int multiplier, noDataRows, idCellNumber, xCellNumber, yCellNumber, zCellNumber;
	protected static List<Node> list;

	static {
		properties = new Properties();
		try (InputStream stream = NodeSeeker.class.getResourceAsStream("/Properties.txt")) {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		readProperties();
	}

	/**
	 * Consume .xls file
	 *
	 * @param path path to .xls file
	 * @throws IOException
	 */
	public static void consumeFile(String path) throws IOException {
		if (Files.exists(Path.of(path))) {
			list = ExcelReader.parse(path);
		}
	}

	/**
	 * Start of utility.
	 * If you want load user {@link Properties} for this utility,
	 * use {@code setProperty();} method before invoke this.
	 * If you want change node accuracy, use {@code setNodeAccuracy();}
	 * method before invoke this.
	 *
	 * @throws InterruptedException
	 */
	public static void start() throws InterruptedException {
		Stopwatch s2 = new Stopwatch();
		s2.appendBefore(getProperty("sorted_in") + " ");
		HashMap<Integer, List<Node>> hashMap = new HashMap<>();
		for (var v : list) {
			int hash = v.getHash();
			List<Node> localList;
			if (hashMap.containsKey(hash)) {
				localList = hashMap.get(hash);
				localList.add(v);
			} else {
				localList = new ArrayList<>();
				localList.add(v);
				hashMap.put(hash, localList);
			}
		}
		s2.print();
		Stopwatch s3 = new Stopwatch();
		s3.appendBefore(getProperty("matched_in") + " ");
		for (var v : hashMap.entrySet()) {
			List<Node> value = v.getValue();
			if (value.size() > 1) {
				Thread thread = new Thread(new RunnableSeeker(value, 0, value.size()));
				thread.start();
				thread.join();
			}
		}
		s3.print();
	}

	/**
	 * Get property from NodeSeeker properties.
	 * This properties can be replaced, but must
	 * contain designed all keys.
	 *
	 * @param key the property key
	 * @return value
	 * @throws NullPointerException if property do not contain designed key
	 */
	public static String getProperty(String key) throws NullPointerException {
		String value = properties.getProperty(key);
		if (value == null) {
			throw new NullPointerException("NodeSeeker properties have null");
		}
		return value;
	}

	/**
	 * Set node accuracy. Can not be negative.
	 * Invoke this method before {@code start();}.
	 * Default value you can see in properties.
	 *
	 * @param nodeAccuracy node accuracy
	 */
	public static void setNodeAccuracy(int nodeAccuracy) {
		if (nodeAccuracy >= 0) {
			NodeSeeker.nodeAccuracy = (byte) nodeAccuracy;
		}
	}

	/**
	 * @return current node accuracy
	 */
	public static byte getNodeAccuracy() {
		return nodeAccuracy;
	}

	/**
	 * @return list of nodes size
	 */
	public static int getListSize() {
		return list.size();
	}

	/**
	 * Set and read user {@link Properties}. Properties must contain all designed keys.
	 * If this condition do not met - throw {@link NullPointerException}.
	 * Set properties before invoke {@code start();} method.
	 *
	 * @param properties user properties
	 * @throws NullPointerException if properties contains null values
	 */
	public static void setProperties(Properties properties) throws NullPointerException {
		NodeSeeker.properties = properties;
		readProperties();
	}

	/**
	 * Read basic properties.
	 *
	 * @throws NullPointerException if properties contains null values
	 */
	private static void readProperties() throws NullPointerException {
		nodeAccuracy = Byte.parseByte(getProperty("node_accuracy"));
		multiplier = 1;
		for (byte i = 0; i < nodeAccuracy; i++) {
			multiplier = multiplier * 10;
		}
		noDataRows = Integer.parseInt(getProperty("no_data_rows"));
		idCellNumber = Integer.parseInt(getProperty("id_cell_number"));
		xCellNumber = Integer.parseInt(getProperty("x_cell_number"));
		yCellNumber = Integer.parseInt(getProperty("y_cell_number"));
		zCellNumber = Integer.parseInt(getProperty("z_cell_number"));
	}
}