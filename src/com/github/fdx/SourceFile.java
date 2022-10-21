package com.github.fdx;

import java.io.*;

public class SourceFile {
	public static BufferedReader openFile() {
		String fileName = "";
		BufferedReader inFile = null;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Source file = ");
		System.out.flush();

		try {
			fileName = stdin.readLine();
			inFile = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("The source file " + fileName + " was not found.");
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				stdin.close();
			} catch (IOException e) {
				System.err.println("Couldn't close stdin.");
			}
		}
		return inFile;
	}
}
