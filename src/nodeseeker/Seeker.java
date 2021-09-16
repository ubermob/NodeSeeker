package nodeseeker;

import java.util.List;

public class Seeker implements Runnable {

	private final List<Node> list;
	private final int a, b;

	public Seeker(List<Node> list, int a, int b) {
		this.list = list;
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		for (int i = a; i < b; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				try {
					/*String[] first = {list.get(i)};
					String[] second = {list.get(j)};
					first = first[0].split("\t");
					second = second[0].split("\t");
					if (match(first, second)) {
						System.out.println("Match: " + first[0] + "-" + second[0]);
					}*/
					Node first = list.get(i);
					Node second = list.get(j);
					if (m2(first, second)) {
						System.out.println("Match " + first.getId() + "-" + second.getId());
					}
				} catch (IndexOutOfBoundsException ignored) {
				}
			}
		}
	}

	private boolean m2(Node a, Node b) {
		return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
	}

	private boolean match(String[] first, String[] second) {
		return first[1].equals(second[1]) && first[2].equals(second[2]) && first[3].equals(second[3]);
	}
}