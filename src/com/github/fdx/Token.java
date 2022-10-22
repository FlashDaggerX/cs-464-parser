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

	@Override
	public String toString() {
		return String.format("Token { kind: %s (%d), lexeme: \"%s\", line: %d }",
				kind.toString(), kind.ordinal(), lexeme, line);
	}
}
