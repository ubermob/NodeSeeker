package nodeseeker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.RunnableFuture;

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

	public static void test(List<String> list) throws InterruptedException {
		/*int size = list.size() / 3;
		Thread t1 = new Thread(new Seeker(list, 0, size));
		t1.start();
		Thread t2 = new Thread(new Seeker(list, size, list.size()));
		t2.start();
		t1.join();
		t2.join();*/
	}

	public static void test2(List<String> list) throws InterruptedException {
		/*int[] i = four(list.size());
		Thread t1 = new Thread(new Seeker(list, 0, i[0]));
		Thread t2 = new Thread(new Seeker(list, i[0], i[1]));
		Thread t3 = new Thread(new Seeker(list, i[1], i[2]));
		Thread t4 = new Thread(new Seeker(list, i[2], list.size()));
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t1.join();
		t2.join();
		t3.join();
		t4.join();*/
	}

	public static void test3(List<Node> list) throws InterruptedException {
		int[] i = four(list.size());
		Thread t1 = new Thread(new Seeker(list, 0, i[0]));
		Thread t2 = new Thread(new Seeker(list, i[0], i[1]));
		Thread t3 = new Thread(new Seeker(list, i[1], i[2]));
		Thread t4 = new Thread(new Seeker(list, i[2], list.size()));
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t1.join();
		t2.join();
		t3.join();
		t4.join();
	}

	public static int[] four(int i) {
		double k = 1 - 0.707;
		int a, b, c;
		b = (int) Math.floor(i * k);
		a = b / 2;
		c = (int) (Math.floor((i - b) * k) + b);
		/*System.out.println(a);
		System.out.println(b);
		System.out.println(c);*/
		return new int[]{a, b, c};
	}

	private static void match(List<String> list, int a, int b) {
		for (int i = a; i < b; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				try {
					String[] first = {list.get(i)};
					String[] second = {list.get(j)};
					first = first[0].split("\t");
					second = second[0].split("\t");
					if (first[4].charAt(0) == second[4].charAt(0)) {
						if (match(first, second)) {
							System.out.println("Match: " + first[0] + "-" + second[0]);
						}
					}
				} catch (IndexOutOfBoundsException ignored) {
				}
			}
		}
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