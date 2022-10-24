package com.github.fdx;

public class Token {
	public Kind kind;
	public String lexeme;
	public int line;

	public Token(Token.Kind kind, String lexeme, int line) {
		this.kind = kind;
		this.lexeme = lexeme;
		this.line = line;
	}

	@Override
	public String toString() {
		return String.format("kind: (%2d) %-12s\tlexeme: %-8s\tline: %d",
				kind.ordinal(), kind.toString(), lexeme, line);
	}

	public boolean isKind(Kind expected) {
		return this.kind == expected;
	}

	public enum Kind {
		IDENTIFIER,
		LITERAL,
		ASSIGN,
		CONDITIONAL,
		LOOP,
		BLOCK,
		SKIP,
		AND,
		OR,
		LPAREN,
		RPAREN,
		OPERATOR,
		/** The error token */
		NOTHING,
		EOT;
	}
}
