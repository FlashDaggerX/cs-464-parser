package com.github.fdx;

public class Parser {
	private Token currentToken;
	private Lexer lexer;

	public void parse() {
		lexer = new Lexer(SourceFile.openFile());
		currentToken = lexer.scan();
		parseProgram();
		if (currentToken.kind != Token.Kind.EOT)
			new Error("Syntax error: Redundant characters at the end of program.",
					currentToken.line);
	}

	private void accept() {
		currentToken = lexer.scan();
	}

	/* Was `acceptIt` */
	private void expect(Token.Kind expectedKind) {
		if (currentToken.kind == expectedKind)
			currentToken = lexer.scan();
		else
			new Error("Syntax error: " +
					currentToken.toString() +
					" is not expected.",
					currentToken.line);
	}

	// Program" --> "("Sequence State")".
	private void parseProgram() {
	}

	// Sequence --> "("Statements")".
	private void parseSequence() {
	}

	// Statements --> Stmt*
	private void parseStatements() {
	}

	// Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
	private void parseStmt() {
	}

	// State --> "("Pairs")".
	private void parseState() {
	}

	// Pairs --> Pair*.
	private void parsePairs() {
	}

	// Pair --> "("Identifier Literal")".
	private void parsePair() {
	}

	// NullStatement --> skip.
	private void parseNullStatement() {
	}

	// Assignment --> "assign" Identifier Expression.
	private void parseAssignment() {
	}

	// Conditional --> "conditional" Expression Stmt Stmt.
	private void parseConditional() {
	}

	// Loop --> "loop" Expression Stmt.
	private void parseLoop() {
	}

	// Block --> "block" Statements.
	private void parseBlock() {
	}

	// Expression --> Identifier | Literal | "("Operation Expression Expression")".
	private void parseExpression() {
	}

	// Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" |
	// "or" | "and".
	private void parseOperation() {
	}
}
