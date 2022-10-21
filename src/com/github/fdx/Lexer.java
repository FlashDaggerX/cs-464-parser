package com.github.fdx;

import java.io.*;

public class Lexer {
	private static final char NULL = '\u0000';

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
				currentChar = NULL;
				inFile.close();
			} else {
				// Set the next char in the file
				currentChar = (char) i;
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/* Was `scanSeparator` */
	private void skipWhitespace() {
		boolean out = false;
		while (!out) {
			switch (currentChar) {
				case '\n':
					line++;
				case ' ':
				case '\r':
				case '\t':
					advance();
				default:
					out = true;
			}
		}
	}

	public Token scan() {
		currentSpelling = new StringBuffer("");
		skipWhitespace();
		currentKind = scanToken();
		return new Token(currentKind, currentSpelling.toString(), line);
	}

	private Token.Kind scanToken() {
		switch (currentChar) {
			case '(':
				consume();
				return Token.Kind.LPAREN;
			case ')':
				consume();
				return Token.Kind.RPAREN;
			case '+':
			case '-':
			case '*':
			case '/':
			case '=':
				consume();
				return Token.Kind.OPERATOR;
			// All of these characters are followed by an equals,
			// so check for it on all these cases.
			case '<':
			case '>':
			case '!': {
				consume();
				switch (currentChar) {
					case '=':
						consume();
						return Token.Kind.OPERATOR;
					default:
						return Token.Kind.EOT;
				}
			}
			// I had some help with this part from this resource (great book, by the way!):
			// https://craftinginterpreters.com/scanning.html#reserved-words-and-identifiers
			default: {
				boolean parseStr = true, parseNum = true;

				// Consume characters until we reach a space. Hopefully.
				while (true) {
					// This check exists because, according to our CFG, numbers don't
					// exist in identifiers. We have to make sure what we're parsing
					// is either a numerical literal or an identifer.
					if (isLetter(currentChar)) {
						parseNum = false;
					} else if (isDigit(currentChar)) {
						parseStr = false;
					} else {
						break;
					}
					consume();
				}

				// First see if the lexeme is a number.
				if (parseNum) {
					return Token.Kind.LITERAL;
				}

				// And then check to see if any of them match a keyword.
				if (parseStr) {
					switch (currentSpelling.toString()) {
						case "skip":
							return Token.Kind.SKIP;
						case "assign":
							return Token.Kind.ASSIGN;
						case "conditional":
							return Token.Kind.CONDITIONAL;
						case "loop":
							return Token.Kind.LOOP;
						case "block":
							return Token.Kind.BLOCK;
						case "or":
							return Token.Kind.OR;
						case "and":
							return Token.Kind.AND;
						default: {
							return Token.Kind.IDENTIFIER;
						}
					}
				}

				// We got here because of crappy mistakes.
				return Token.Kind.EOT;
			}
		}
	}

	/* ? */
	@SuppressWarnings("unused")
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
