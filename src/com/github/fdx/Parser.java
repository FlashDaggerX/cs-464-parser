package com.github.fdx;

import com.github.fdx.Token.Kind;

public class Parser {
	private Token currentToken;
	private Lexer lexer;

	public void parse() {
		lexer = new Lexer(SourceFile.openFile());
		consume();
		parseProgram();
		if (currentToken.kind != Kind.EOT)
			new Error("Syntax error: Redundant characters at the end of program.",
					currentToken.line);
	}

	/* Was `accept */
	private void consume() {
		currentToken = lexer.scan();
	}

	/* Was `acceptIt` */
	private void expect(Kind expectedKind) {
		if (currentToken.isKind(expectedKind)) {
			System.out.println(currentToken);
			consume();
		} else {
			new Error("Syntax error: " +
					currentToken.toString() +
					" is not expected.",
					currentToken.line);
		}
	}

	// Program --> "("Sequence State")"
	private void parseProgram() {
		expect(Kind.LPAREN);
		parseSequence();
		parseState();
		expect(Kind.RPAREN);
	}

	// Sequence --> "(" Statements ")"
	private void parseSequence() {
		expect(Kind.LPAREN);
		parseStatements();
		expect(Kind.RPAREN);

	}

	// Statements --> Statements Stmt | e
	private void parseStatements() {
		parseStmt();
		if (!currentToken.isKind(Kind.RPAREN)) {
			parseStatements();
		}
	}

	// Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block} ")"
	private void parseStmt() {
		expect(Kind.LPAREN);
		switch (currentToken.kind) {
			case SKIP:
				parseNullStatement();
				break;
			case ASSIGN:
				parseAssignment();
				break;
			case CONDITIONAL:
				parseConditional();
				break;
			case LOOP:
				parseLoop();
				break;
			case BLOCK:
				parseBlock();
				break;
			default:
		}
		expect(Kind.RPAREN);
	}

	// State --> "(" Pairs ")"
	private void parseState() {
		expect(Kind.LPAREN);
		parsePairs();
		expect(Kind.RPAREN);
	}

	// Pairs --> Pairs Pair | e
	private void parsePairs() {
		parsePair();
		if (!currentToken.isKind(Kind.RPAREN)) {
			parsePairs();
		}
	}

	// Pair --> "(" Identifier Literal ")"
	private void parsePair() {
		expect(Kind.LPAREN);
		expect(Kind.IDENTIFIER);
		expect(Kind.LITERAL);
		expect(Kind.RPAREN);
	}

	// NullStatement --> "skip"
	private void parseNullStatement() {
		expect(Kind.SKIP);
	}

	// Assignment --> "assign" Identifier Expression
	private void parseAssignment() {
		expect(Kind.ASSIGN);
		expect(Kind.IDENTIFIER);
		parseExpression();
	}

	// Conditional --> "conditional" Expression Stmt Stmt
	private void parseConditional() {
		expect(Kind.CONDITIONAL);
		parseExpression();
		parseStmt();
		parseStmt();
	}

	// Loop --> "loop" Expression Stmt
	private void parseLoop() {
		expect(Kind.LOOP);
		parseExpression();
		parseStmt();
	}

	// Block --> "block" Statements
	private void parseBlock() {
		expect(Kind.BLOCK);
		parseStatements();
	}

	// Expression --> Identifier | Literal | "(" Operation Expression Expression ")"
	private void parseExpression() {
		switch (currentToken.kind) {
			case IDENTIFIER:
				expect(Kind.IDENTIFIER);
				break;
			case LITERAL:
				expect(Kind.LITERAL);
				break;
			default:
				expect(Kind.LPAREN);
				parseOperation();
				parseExpression();
				parseExpression();
				expect(Kind.RPAREN);
		}
	}

	// Operation --> "+" | "-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" |
	// "or" | "and"
	private void parseOperation() {
		switch (currentToken.kind) {
			case OR:
				expect(Kind.OR);
			case AND:
				expect(Kind.AND);
			default:
				expect(Kind.OPERATOR);
		}
	}
}
