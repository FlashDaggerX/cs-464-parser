package com.github.fdx;

import java.io.*;

import com.github.fdx.Token.Kind;

public class Lexer {
	private static final char NULL = '\u0000';

	private char currentChar;
	private Kind currentKind;
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
		loop: while (true) {
			switch (currentChar) {
				case '\n':
					line++;
				case ' ':
				case '\r':
				case '\t':
					advance();
					break;
				default:
					break loop;
			}
		}
	}

	public Token scan() {
		currentSpelling = new StringBuffer("");
		skipWhitespace();
		currentKind = scanToken();
		return new Token(currentKind, currentSpelling.toString(), line);
	}

	private Kind scanToken() {
		switch (currentChar) {
			// End of file
			case NULL:
				return Kind.EOT;
			case '(':
				consume();
				return Kind.LPAREN;
			case ')':
				consume();
				return Kind.RPAREN;
			case '+':
			case '-':
			case '*':
			case '/':
			case '=':
				consume();
				return Kind.OPERATOR;
			// All of these characters are followed by an equals,
			// so check for it on all these cases.
			case '<':
			case '>': {
				consume();
				switch (currentChar) {
					case '=':
						consume();
						return Kind.OPERATOR;
					default:
						return Kind.OPERATOR;
				}
			}
			// '!' is not a valid character by itself. Return EOT if it is (by itself).
			case '!': {
				consume();
				switch (currentChar) {
					case '=':
						consume();
						return Kind.OPERATOR;
					default:
						return Kind.EOT;
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
					return Kind.LITERAL;
				}

				// And then check to see if any of them match a keyword.
				if (parseStr) {
					switch (currentSpelling.toString()) {
						case "skip":
							return Kind.SKIP;
						case "assign":
							return Kind.ASSIGN;
						case "conditional":
							return Kind.CONDITIONAL;
						case "loop":
							return Kind.LOOP;
						case "block":
							return Kind.BLOCK;
						case "or":
							return Kind.OR;
						case "and":
							return Kind.AND;
						default:
							return Kind.IDENTIFIER;
					}
				}

				// We got here because of crappy mistakes.
				return Kind.EOT;
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
