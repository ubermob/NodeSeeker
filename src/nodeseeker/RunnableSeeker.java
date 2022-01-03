package nodeseeker;

import nodeseeker.node.Node;

import java.util.List;

/**
 * Realize node matching.
 *
 * @author Andrey Korneychuk on 17-Sep-21
 * @version 1.1
 */
public class RunnableSeeker implements Runnable {

	private final List<Node> list;
	private final int from, to;
	private final NodeSeekerProperties properties;
	private final NodeSeekerNotify notify;

	/**
	 * Constructor can skip list pieces for multithreading seek.
	 *
	 * @param list       seeking list
	 * @param from       start matching position
	 * @param to         end matching position
	 * @param properties {@link NodeSeekerProperties} loaded properties
	 * @param notify     {@link NodeSeekerNotify} interface for listener pattern
	 */
	public RunnableSeeker(List<Node> list, int from, int to
			, NodeSeekerProperties properties, NodeSeekerNotify notify) {
		this.list = list;
		this.from = from;
		this.to = to;
		this.properties = properties;
		this.notify = notify;
	}

	@Override
	public void run() {
		for (int i = from; i < to; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				try {
					Node first = list.get(i);
					Node second = list.get(j);
					if (isMatch(first, second)) {
						notify.notify(String.format(properties.getProperty("match_output"), first.getId(), second.getId()));
					}
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
	 * @return true - if nodes have same coordinates
	 */
	private boolean isMatch(Node a, Node b) {
		return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
	}
}