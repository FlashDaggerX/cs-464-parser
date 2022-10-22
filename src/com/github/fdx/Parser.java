package com.github.fdx;

public class Parser {
	private Token currentToken;
	private Lexer lexer;

	public void parse() {
		lexer = new Lexer(SourceFile.openFile());
		accept();
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
		if (currentToken.kind == expectedKind) {
			System.out.println(currentToken.toString());
			currentToken = lexer.scan();
		} else {
			new Error("Syntax error: " +
					currentToken.toString() +
					" is not expected.",
					currentToken.line);
		}
	}

	// Program" --> "("Sequence State")".
	private void parseProgram() {
		expect(Token.Kind.LPAREN);
		parseSequence();
		parseState();
		expect(Token.Kind.RPAREN);
	}

	// Sequence --> "("Statements")".
	private void parseSequence() {
		parseStatements();
	}

	// Statements --> Stmt*
	private void parseStatements() {
		parseStmt();
	}

	// Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
	private void parseStmt() {
		expect(Token.Kind.LPAREN);
		parseNullStatement();
		parseAssignment();
		parseConditional();
		parseLoop();
		parseBlock();
		expect(Token.Kind.RPAREN);
	}

	// State --> "("Pairs")".
	private void parseState() {
		expect(Token.Kind.LPAREN);
		parsePairs();
		expect(Token.Kind.RPAREN);
	}

	// Pairs --> Pair*.
	private void parsePairs() {
		parsePair();

	}

	// Pair --> "("Identifier Literal")".
	private void parsePair() {
		expect(Token.Kind.LPAREN);
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.LITERAL);
		expect(Token.Kind.RPAREN);
	}

	// NullStatement --> skip.
	private void parseNullStatement() {
		expect(Token.Kind.SKIP);
	}

	// Assignment --> "assign" Identifier Expression.
	private void parseAssignment() {
		expect(Token.Kind.ASSIGN);
		expect(Token.Kind.IDENTIFIER);
		parseExpression();
	}

	// Conditional --> "conditional" Expression Stmt Stmt.
	private void parseConditional() {
		expect(Token.Kind.CONDITIONAL);
		parseExpression();
		parseStmt();
		parseStmt();
	}

	// Loop --> "loop" Expression Stmt.
	private void parseLoop() {
		expect(Token.Kind.LOOP);
		parseExpression();
		parseStmt();
	}

	// Block --> "block" Statements.
	private void parseBlock() {
		expect(Token.Kind.BLOCK);
		parseStatements();
	}

	// Expression --> Identifier | Literal | "("Operation Expression Expression")".
	private void parseExpression() {
		expect(Token.Kind.IDENTIFIER);
		expect(Token.Kind.LITERAL);
	}

	// Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" |
	// "or" | "and".
	private void parseOperation() {
		expect(Token.Kind.OPERATOR);
	}
}
