package com.github.fdx;

public class Token {
	public Kind kind;
	public int line;

	public Token(Token.Kind kind, int line) {
		this.kind = kind;
		this.line = line;
	}

	public enum Kind {
		IDENTIFIER("<identifier>"),
		LITERAL("<literal>"),
		ASSIGN("assign"),
		CONDITIONAL("conditional"),
		LOOP("loop"),
		BLOCK("block"),
		SKIP("skip"),
		AND("and"),
		OR("or"),
		LPAREN,
		RPAREN,
		OPERATOR,
		NOTHING,
		EOT;

		private String spelling;

		Kind(String spelling) {
			this.spelling = spelling;
		}

		Kind() {
			this("<>");
		}

		@Override
		public String toString() {
			return this.spelling;
		}
	}
}
