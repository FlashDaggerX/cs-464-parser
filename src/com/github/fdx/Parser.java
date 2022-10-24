package com.github.fdx;

import com.github.fdx.Token.Kind;

public class Parser {
	private Token currentToken;
	private Lexer lexer;

	public void parse() {
		lexer = new Lexer(SourceFile.openFile());
		consume();
		parseProgram();
		if (!currentToken.isKind(Kind.EOT))
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
			new Error(String.format("Syntax error: \"%s\" (type %s) was not expected",
					currentToken.lexeme, currentToken.kind), currentToken.line);
		}
	}

	// Program --> "("Sequence State")"
	private void parseProgram() {
		System.out.println("\t[RULE Program]");
		expect(Kind.LPAREN);
		parseSequence();
		parseState();
		expect(Kind.RPAREN);
	}

	// Sequence --> "(" Statements ")"
	private void parseSequence() {
		System.out.println("\t[RULE Sequence]");
		expect(Kind.LPAREN);
		parseStatements();
		expect(Kind.RPAREN);

	}

	// Statements --> Statements Stmt | e
	private void parseStatements() {
		System.out.println("\t[RULE Statements]");
		parseStmt();
		if (!currentToken.isKind(Kind.RPAREN)) {
			parseStatements();
		}
	}

	// Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block} ")"
	private void parseStmt() {
		System.out.println("\t[RULE Stmt]");
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
		System.out.println("\t[RULE State]");
		expect(Kind.LPAREN);
		parsePairs();
		expect(Kind.RPAREN);
	}

	// Pairs --> Pairs Pair | e
	private void parsePairs() {
		System.out.println("\t[RULE Pairs]");
		parsePair();
		if (!currentToken.isKind(Kind.RPAREN)) {
			parsePairs();
		}
	}

	// Pair --> "(" Identifier Literal ")"
	private void parsePair() {
		System.out.println("\t[RULE Pair]");
		expect(Kind.LPAREN);
		expect(Kind.IDENTIFIER);
		expect(Kind.LITERAL);
		expect(Kind.RPAREN);
	}

	// NullStatement --> "skip"
	private void parseNullStatement() {
		System.out.println("\t[RULE NullStatement]");
		expect(Kind.SKIP);
	}

	// Assignment --> "assign" Identifier Expression
	private void parseAssignment() {
		System.out.println("\t[RULE Assignment]");
		expect(Kind.ASSIGN);
		expect(Kind.IDENTIFIER);
		parseExpression();
	}

	// Conditional --> "conditional" Expression Stmt Stmt
	private void parseConditional() {
		System.out.println("\t[RULE Conditional]");
		expect(Kind.CONDITIONAL);
		parseExpression();
		parseStmt();
		parseStmt();
	}

	// Loop --> "loop" Expression Stmt
	private void parseLoop() {
		System.out.println("\t[RULE Loop]");
		expect(Kind.LOOP);
		parseExpression();
		parseStmt();
	}

	// Block --> "block" Statements
	private void parseBlock() {
		System.out.println("\t[RULE Block]");
		expect(Kind.BLOCK);
		parseStatements();
	}

	// Expression --> Identifier | Literal | "(" Operation Expression Expression ")"
	private void parseExpression() {
		System.out.println("\t[RULE Expression]");
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
		System.out.println("\t[RULE Operation]");
		switch (currentToken.kind) {
			case OR:
				expect(Kind.OR);
			case AND:
				expect(Kind.AND);
				break;
			default:
				expect(Kind.OPERATOR);
		}
	}
}
