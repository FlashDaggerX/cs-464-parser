package com.github.fdx;

import java.io.*;

public class Lexer {
	private char currentChar;
	private Token.Kind currentKind;
	private StringBuffer currentSpelling;
	private BufferedReader inFile;
	private int line = 1;

	public Lexer(BufferedReader inFile) {
		this.inFile = inFile;
		advance();
	}

	/* Was `takeIt` */
	private void consume() {
		currentSpelling.append(currentChar);
		advance();
	}

	/* Was `discard` */
	private void advance() {
		try {
			int i = inFile.read();
			if (i == -1) {
				// end of file
				currentChar = '\u0000';
				inFile.close();
			} else {
				// Set the next char in the file
				currentChar = (char) i;
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private Token.Kind scanToken() {
		switch (currentChar) {
			// Your switch
		}

		return Token.Kind.NOTHING;
	}

	/* Was `scanSeparator` */
	private boolean advanceSeparator() {
		switch (currentChar) {
			case '\n':
				line++;
			case ' ':
			case '\r':
			case '\t':
				advance();
				return true;
		}
		return false;
	}

	public Token scan() {
		currentSpelling = new StringBuffer("");
		// Skip all the whitespace before the next token
		while (advanceSeparator()) {
		}
		currentKind = scanToken();
		return new Token(currentKind, currentSpelling.toString(), line);
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
