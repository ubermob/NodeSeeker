package nodeseeker;

import java.util.List;

public class RunnableSeeker implements Runnable {

	private final List<Node> list;
	private final int from, to;

	public RunnableSeeker(List<Node> list, int from, int to) {
		this.list = list;
		this.from = from;
		this.to = to;
	}

	@Override
	public void run() {
		for (int i = from; i < to; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				try {
					Node first = list.get(i);
					Node second = list.get(j);
					if (match(first, second))
						System.out.println(String.format(
								NodeSeeker.properties.getProperty("match_output"), first.getId(), second.getId()));
				} catch (IndexOutOfBoundsException ignored) {
				}
			}
		}
	}

	private boolean match(Node a, Node b) {
		return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
	}
}