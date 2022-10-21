package com.github.fdx;

import java.io.*;

public class PScan {
	private char currentChar;
	private Token.Kind currentKind;
	private StringBuffer currentSpelling;
	private BufferedReader inFile;
	private static int line = 1;

	public PScan(BufferedReader inFile) {
		this.inFile = inFile;

		try {
			int i = this.inFile.read();
			if (i == -1) // end of file
				currentChar = '\u0000';
			else
				currentChar = (char) i;
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void takeIt() {
		currentSpelling.append(currentChar);

		try {
			int i = inFile.read();
			if (i == -1) // end of file
				currentChar = '\u0000';
			else
				currentChar = (char) i;
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void discard() {
		try {
			int i = inFile.read();
			if (i == -1) // end of file
				currentChar = '\u0000';
			else
				currentChar = (char) i;
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private Token.Kind scanToken() {
		switch (currentChar) {
			// Your switch
		}

		return Token.Kind.NOTHING; // After you replace this switch with your, remove this line.
	}

	private void scanSeparator() {
		switch (currentChar) {
			case ' ':
			case '\n':
			case '\r':
			case '\t':
				if (currentChar == '\n')
					line++;
				discard();
		}
	}

	public Token scan() {
		currentSpelling = new StringBuffer("");
		while (currentChar == ' ' || currentChar == '\n' || currentChar == '\r')
			scanSeparator();
		currentKind = scanToken();
		return new Token(currentKind, line);
	}

	private boolean isGraphic(char c) {
		return c == '\t' || (' ' <= c && c <= '~');
	}

	private boolean isDigit(char c) {
		return '0' <= c && c <= '9';
	}

	private boolean isLetter(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
	}
}
