package nodeseeker;

import utools.stopwatch.Stopwatch;

import java.io.IOException;
import java.util.Scanner;

/**
 * Sample library realization
 *
 * @author Andrey Korneychuk on 17-Sep-21
 * @version 1.1
 */
public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner scanner = new Scanner(System.in);
		NodeSeeker nodeSeeker = new NodeSeeker();
		System.out.println(nodeSeeker.getProperty("path_to"));
		String path = scanner.nextLine();
		Stopwatch s1 = new Stopwatch();
		Stopwatch totalTime = new Stopwatch();
		s1.appendBefore("File read in ");
		totalTime.appendBefore("Total elapsed time: ");
		nodeSeeker.consumeFile(path);
		s1.print();
		System.out.println(String.format(nodeSeeker.getProperty("list_size"), nodeSeeker.getListSize()));
		nodeSeeker.start();
		totalTime.print();
		System.out.println(nodeSeeker.getProperty("done"));
	}
}