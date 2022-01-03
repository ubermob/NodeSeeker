package nodeseeker;

import java.io.IOException;

/**
 * @author Andrey Korneychuk on 03-Jan-22
 * @version 1.0
 */
public class UnsupportedExtension extends IOException {

	public UnsupportedExtension() {
	}

	public UnsupportedExtension(String message) {
		super(message);
	}
}