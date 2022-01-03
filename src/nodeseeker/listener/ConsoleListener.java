package nodeseeker.listener;

/**
 * Console listener.
 *
 * @author Andrey Korneychuk on 25-Dec-21
 * @version 1.0
 */
public class ConsoleListener implements NodeSeekerListener {

	@Override
	public void update(String message) {
		System.out.println(message);
	}
}