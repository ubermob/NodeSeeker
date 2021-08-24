package nodeseeker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NodeSeeker {
	/**
	 * Consume path to file
	 *
	 * @param path {@code String} path to file
	 * @throws {@link IOException}
	 */
	public static void file(String path) throws IOException {
		List<String> strings = Files.readAllLines(Paths.get(path));
		match(strings);
	}

	private static void match(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				try {
					String[] first = {list.get(i)};
					String[] second = {list.get(j)};
					first = first[0].split("\t");
					second = second[0].split("\t");
					if (match(first, second)) {
						System.out.println("Match: " + first[0] + "-" + second[0]);
					}
				} catch (IndexOutOfBoundsException ignored) {
				}
			}
		}
	}

	private static boolean match(String[] first, String[] second) {
		return first[1].equals(second[1]) && first[2].equals(second[2]) && first[3].equals(second[3]);
	}
}