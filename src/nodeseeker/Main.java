package nodeseeker;

import utools.stopwatch.Stopwatch;

import java.io.IOException;
import java.util.Scanner;

import static nodeseeker.NodeSeeker.getProperty;

/**
 * @author Andrey Korneychuk on 17-Sep-21
 * @version 1.0
 */
public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner scanner = new Scanner(System.in);
		System.out.println(getProperty("path_to"));
		String path = scanner.nextLine();
		if (!path.endsWith(getProperty("excel_extension"))) {
			path += getProperty("excel_extension");
		}
		Stopwatch s1 = new Stopwatch();
		Stopwatch totalTime = new Stopwatch();
		totalTime.appendBefore("Total elapsed time: ");
		s1.appendBefore("Excel read in ");
		NodeSeeker.consumeFile(path);
		s1.print();
		System.out.println(String.format(getProperty("list_size"), NodeSeeker.getListSize()));
		NodeSeeker.start();
		totalTime.print();
		System.out.println(getProperty("done"));
	}
}