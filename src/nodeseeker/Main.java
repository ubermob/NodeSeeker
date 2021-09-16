package nodeseeker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Stopwatch s1 = new Stopwatch();
		//List<Node> list = Excel.parse("I:\\NodeSeeker\\Дружинниковская 17.xls");
		List<Node> list = Excel.parse("I:\\NodeSeeker\\nodes.xls");
		System.out.println("Excel read");
		s1.print();
		System.out.println("List size:");
		System.out.println(list.size());
		Stopwatch s2 = new Stopwatch();
		HashMap<Integer, List<Node>> hashMap = new HashMap<>();
		for (var v : list) {
			var hash = v.getHash();
			List<Node> localList;
			if (hashMap.containsKey(hash)){
				localList = hashMap.get(hash);
				localList.add(v);
			} else {
				localList = new ArrayList<>();
				localList.add(v);
				hashMap.put(hash, localList);
			}
		}
		System.out.println("Sorted");
		s2.print();
		Stopwatch s3 = new Stopwatch();
		for (var v : hashMap.entrySet()) {
			Integer key = v.getKey();
			List<Node> value = v.getValue();
			if (value.size() > 1) {
				Thread thread = new Thread(new Seeker(value, 0, value.size()));
				thread.start();
				thread.join();
			}
		}
		s3.print();
	}

	public static void main2(String[] args) throws IOException, InterruptedException {
		Scanner scanner = new Scanner(System.in);
		//System.out.println("Input path to file:");
		List<String> strings = Files.readAllLines(Paths.get("F:\\FILES\\nodeseeker\\fp.txt")); // scanner.nextLine
		System.out.println("List<String> size: " + strings.size());
		List<Node> list = new ArrayList<>(strings.size());
		Stopwatch s1 = new Stopwatch();
		for (var v : strings) {
			//list.add(strings.get(i));
			String[] s = v.split("\t");
			list.add(new Node(
					Integer.parseInt(s[0]),
					Double.parseDouble(s[1].replace(",", ".")),
					Double.parseDouble(s[2].replace(",", ".")),
					Double.parseDouble(s[3].replace(",", "."))
			));
		}
		System.out.print("List<Node> build in ");
		s1.print();
		//list = strings;
		System.out.println("List<Node> size: " + list.size());
		s1 = new Stopwatch();
		HashMap<Node, Integer> count = new HashMap<>();
		for (var v : list) {
			Iterator<Map.Entry<Node, Integer>> iterator = count.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Node, Integer> next = iterator.next();
				Node key = next.getKey();
				if (key.getX()==v.getX() && key.getY()==v.getY() && key.getZ()==v.getZ()) {
					count.put(key, count.get(key) + 1);
					break;
				}
			}
			count.put(v, 1);
		}
		Iterator<Map.Entry<Node, Integer>> iterator = count.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Node, Integer> next = iterator.next();
			if (next.getValue() > 1) {
				System.out.println("Match " + next.getKey().getId() + " time " + next.getValue());
			}
		}
		//NodeSeeker.test3(list);
		//NodeSeeker.file(scanner.nextLine());
		System.out.print("Sought in ");
		s1.print();
		System.out.println("Done");
		scanner.close();
	}
}