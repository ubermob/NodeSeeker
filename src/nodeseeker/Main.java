package nodeseeker;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input path to file:");
		NodeSeeker.file(scanner.nextLine());
		System.out.println("Done");
		scanner.close();
	}
}