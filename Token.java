package com.github.fdx;

/* You do not need a separate token (number) for each operator. All of them
 * should be translated to one token (number) which is OPERATOR. See below
 *
 *  Program --> Sequence State.
 * Sequence --> (Statements ).
 * Statements --> Statements Stmt | Stmt
 * Stmt --> NullStatement | Assignment | Conditional | Loop | Block.
 * State -->  (Pairs).
 * Pairs --> Pairs Pair | Pair.
 * Pair --> (Identifier Literal).
 * NullStatement --> (skip).
 * Assignment --> (assign Identifier Expression).
 * Conditional --> (conditional Expression Stmt Stmt).
 * Loop --> (loop Expression Stmt).
 * Block --> (block Statements).
 * Expression --> Identifier | Literal | (Operation Expression Expression).
 * Operation --> + | - | * | / | < | <= | > | >= | = | != | or | and.
 */

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
			this("");
		}
	}
	/*
	public final static byte
		IDENTIFIER =  0,
		LITERAL    =  1,
		ASSIGN     =  2,
		CONDITIONAL=  3,
		LOOP       =  4,
		BLOCK      =  5,
		SKIP       =  6,
		AND        =  7,
		OR         =  8,
		LPAREN     =  9,
		RPAREN     = 10,
		OPERATOR   = 11,
		// Never happen but we need to return some thing in
		// the switch in class Scanner when there is an error
		NOTHING    = 12,
		EOT        = 13;*/
}

