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

public class NodeSeeker {

	protected static Properties properties;
	private static byte nodeAccuracy;
	protected static int multiplier;
	protected static List<Node> list;

	static {
		properties = new Properties();
		try (InputStream stream = NodeSeeker.class.getResourceAsStream("/Properties.txt")) {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		nodeAccuracy = Byte.parseByte(getProperty("node_accuracy"));
		multiplier = 1;
		for (byte i = 0; i < nodeAccuracy; i++) {
			multiplier = multiplier * 10;
		}
		ExcelReader.noDataRows = Integer.parseInt(getProperty("no_data_rows"));
		ExcelReader.idCellNumber = Integer.parseInt(getProperty("id_cell_number"));
		ExcelReader.xCellNumber = Integer.parseInt(getProperty("x_cell_number"));
		ExcelReader.yCellNumber = Integer.parseInt(getProperty("y_cell_number"));
		ExcelReader.zCellNumber = Integer.parseInt(getProperty("z_cell_number"));
	}

	public static void consumeFile(String path) throws IOException {
		if (Files.exists(Path.of(path))) {
			list = ExcelReader.parse(path);
		}
	}

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

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static void setNodeAccuracy(int nodeAccuracy) {
		if (nodeAccuracy >= 0) {
			NodeSeeker.nodeAccuracy = (byte) nodeAccuracy;
		}
	}

	public static byte getNodeAccuracy() {
		return nodeAccuracy;
	}

	public static int getListSize() {
		return list.size();
	}
}