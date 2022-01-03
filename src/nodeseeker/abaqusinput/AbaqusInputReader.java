package nodeseeker.abaqusinput;

import nodeseeker.NodeSeekerProperties;
import nodeseeker.node.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Convert nodes coordinates from .inp file to list of nodes.
 *
 * @author Andrey Korneychuk on 03-Jan-22
 * @version 1.0
 */
public class AbaqusInputReader {

	/**
	 * Parse .inp file.
	 *
	 * @param path       string with path to .inp file
	 * @param multiplier decimal view of node coordinates accuracy
	 * @param properties {@link NodeSeekerProperties} loaded properties
	 * @return list with nodes
	 * @throws IOException
	 */
	public static List<Node> parse(String path, int multiplier
			, NodeSeekerProperties properties) throws IOException {
		List<Node> list = new ArrayList<>();
		List<String> file = Files.readAllLines(Path.of(path));
		int index = file.indexOf(properties.getProperty("abaqus_node_marker")) + 1;
		while (isANumber(file.get(index))) {
			String[] splitted = file.get(index).split(",");
			list.add(new Node(
					Integer.parseInt(splitted[0]),
					Double.parseDouble(splitted[1].trim()),
					Double.parseDouble(splitted[2].trim()),
					Double.parseDouble(splitted[3].trim()),
					multiplier,
					properties
			));
			index++;
		}
		return list;
	}

	/**
	 * Return true if 0 index of string is a decimal not zero digit (1 - 9).
	 *
	 * @param string current string
	 * @return true if string start by 1-9
	 */
	private static boolean isANumber(String string) {
		char c = string.charAt(0);
		return c == '1' || c == '2' || c == '3' || c == '4' || c == '5'
				|| c == '6' || c == '7' || c == '8' || c == '9';
	}
}