package nodeseeker;

import java.util.List;

/**
 * Realize node matching.
 *
 * @author Andrey Korneychuk on 17-Sep-21
 * @version 1.0
 */
public class RunnableSeeker implements Runnable {

	private final List<Node> list;
	private final int from, to;

	/**
	 * Constructor can skip list pieces for multithreading seek.
	 *
	 * @param list seeking list
	 * @param from start matching position
	 * @param to   end matching position
	 */
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

	/**
	 * Match two nodes.
	 *
	 * @param a first node
	 * @param b second node
	 * @return true - if nodes are in the same place
	 */
	private boolean match(Node a, Node b) {
		return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
	}
}