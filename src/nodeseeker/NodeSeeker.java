package nodeseeker;

import nodeseeker.abaqusinput.AbaqusInputReader;
import nodeseeker.excel.ExcelParams;
import nodeseeker.excel.ExcelReader;
import nodeseeker.listener.ConsoleListener;
import nodeseeker.listener.NodeSeekerListener;
import nodeseeker.node.Node;
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
 * Managing this utility.
 *
 * @author Andrey Korneychuk on 17-Sep-21
 * @version 1.1
 */
public class NodeSeeker implements NodeSeekerProperties, NodeSeekerNotify {

	private Properties properties;
	private byte nodeAccuracy;
	private int multiplier;
	private ExcelParams excelParams;
	private List<Node> list;
	private final List<NodeSeekerListener> listeners;

	/**
	 * Basic constructor. Realize and setup class for use.<br>
	 * Read default {@link Properties},
	 * add 1 {@link ConsoleListener} to {@link NodeSeekerListener} list.
	 */
	public NodeSeeker() {
		properties = new Properties();
		try (InputStream stream = NodeSeeker.class.getResourceAsStream("/Node_seeker_properties.txt")) {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		readProperties();
		listeners = new ArrayList<>(2);
		listeners.add(new ConsoleListener());
	}

	/**
	 * Consume .xls or .inp file
	 *
	 * @param path path to .xls or .inp file
	 * @throws IOException if file do not exist or unsupported extension or IO trouble
	 */
	public void consumeFile(String path) throws IOException {
		if (Files.exists(Path.of(path))) {
			if (path.endsWith(getProperty("excel_extension"))) {
				list = ExcelReader.parse(path, excelParams, multiplier, this);
			} else if (path.endsWith(getProperty("abaqus_input_extension"))) {
				list = AbaqusInputReader.parse(path, multiplier, this);
			} else {
				// Unsupported extension
				throw new UnsupportedExtension(String.format(getProperty("unsupported_extension"), path));
			}
		} else {
			// File do not exist
			throw new IOException(String.format(getProperty("file_do_not_exist"), path));
		}
	}

	/**
	 * Start calculation.
	 * If you want load user {@link Properties} for this utility,
	 * use {@code setProperty();} method before invoke this.
	 * If you want change node accuracy, use {@code setNodeAccuracy();}
	 * method before invoke this.
	 * If you want add more {@link NodeSeekerListener}, use {@code addListener();}
	 * method before invoke this.
	 *
	 * @throws InterruptedException if thread is waiting, sleeping, or otherwise occupied,
	 * and the thread is interrupted, either before or during the activity
	 */
	public void start() throws InterruptedException {
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
		notify(s2.getPrettyString());
		Stopwatch s3 = new Stopwatch();
		s3.appendBefore(getProperty("matched_in") + " ");
		for (var v : hashMap.entrySet()) {
			List<Node> list = v.getValue();
			if (list.size() > 1) {
				Thread thread = new Thread(new RunnableSeeker(
						list,
						0,
						list.size(),
						this,
						this
				));
				thread.start();
				thread.join();
			}
		}
		notify(s3.getPrettyString());
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
	@Override
	public String getProperty(String key) throws NullPointerException {
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
	public void setNodeAccuracy(int nodeAccuracy) {
		if (nodeAccuracy >= 0) {
			this.nodeAccuracy = (byte) nodeAccuracy;
		}
	}

	/**
	 * @return current node accuracy
	 */
	public byte getNodeAccuracy() {
		return nodeAccuracy;
	}

	/**
	 * @return list of nodes size
	 */
	public int getListSize() {
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
	public void setProperties(Properties properties) throws NullPointerException {
		this.properties = properties;
		readProperties();
	}

	/**
	 * Add {@link NodeSeekerListener} listener.
	 *
	 * @param listener {@link NodeSeekerListener}
	 */
	public void addListener(NodeSeekerListener listener) {
		listeners.add(listener);
	}

	/**
	 * Notify all {@link NodeSeekerListener} listeners.
	 *
	 * @param message very important message
	 */
	@Override
	public void notify(String message) {
		for (var v : listeners) {
			v.update(message);
		}
	}

	/**
	 * Read basic properties.
	 *
	 * @throws NullPointerException if properties contains null values
	 */
	private void readProperties() throws NullPointerException {
		nodeAccuracy = Byte.parseByte(getProperty("node_accuracy"));
		multiplier = 1;
		for (byte i = 0; i < nodeAccuracy; i++) {
			multiplier = multiplier * 10;
		}
		excelParams = new ExcelParams(
				Integer.parseInt(getProperty("no_data_rows")),
				Integer.parseInt(getProperty("id_cell_number")),
				Integer.parseInt(getProperty("x_cell_number")),
				Integer.parseInt(getProperty("y_cell_number")),
				Integer.parseInt(getProperty("z_cell_number"))
		);
	}
}