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
			System.out.println("\t" + currentToken);
			consume();
		} else {
			new Error(String.format("Syntax error: \"%s\" (type %s) was not expected",
					currentToken.lexeme, currentToken.kind), currentToken.line);
		}
	}

	// Program --> "("Sequence State")"
	private void parseProgram() {
		System.out.println("[RULE Program]");
		expect(Kind.LPAREN);
		parseSequence();
		parseState();
		expect(Kind.RPAREN);
		System.out.println("[END RULE Program]");
	}

	// Sequence --> "(" Statements ")"
	private void parseSequence() {
		System.out.println("[RULE Sequence]");
		expect(Kind.LPAREN);
		parseStatements();
		expect(Kind.RPAREN);
		System.out.println("[END RULE Sequence]");
	}

	// Statements --> Statements Stmt | e
	private void parseStatements() {
		System.out.println("[RULE Statements]");
		if (currentToken.isKind(Kind.LPAREN)) {
			parseStmt();
			if (!currentToken.isKind(Kind.RPAREN)) {
				parseStatements();
			}
		}
		System.out.println("[END RULE Statements]");
	}

	// Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block} ")"
	private void parseStmt() {
		System.out.println("[RULE Stmt]");
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
		System.out.println("[END RULE Stmt]");

	}

	// State --> "(" Pairs ")"
	private void parseState() {
		System.out.println("[RULE State]");
		expect(Kind.LPAREN);
		parsePairs();
		expect(Kind.RPAREN);
		System.out.println("[END RULE State]");
	}

	// Pairs --> Pairs Pair | e
	private void parsePairs() {
		System.out.println("[RULE Pairs]");
		if (currentToken.isKind(Kind.LPAREN)) {
			parsePair();
			if (!currentToken.isKind(Kind.RPAREN)) {
				parsePairs();
			}
		}
		System.out.println("[END RULE Pairs]");
	}

	// Pair --> "(" Identifier Literal ")"
	private void parsePair() {
		System.out.println("[RULE Pair]");
		expect(Kind.LPAREN);
		expect(Kind.IDENTIFIER);
		expect(Kind.LITERAL);
		expect(Kind.RPAREN);
		System.out.println("[END RULE Pair]");
	}

	// NullStatement --> "skip"
	private void parseNullStatement() {
		System.out.println("[RULE NullStatement]");
		expect(Kind.SKIP);
		System.out.println("[END RULE NullStatement]");
	}

	// Assignment --> "assign" Identifier Expression
	private void parseAssignment() {
		System.out.println("[RULE Assignment]");
		expect(Kind.ASSIGN);
		expect(Kind.IDENTIFIER);
		parseExpression();
		System.out.println("[END RULE Assignment]");
	}

	// Conditional --> "conditional" Expression Stmt Stmt
	private void parseConditional() {
		System.out.println("[RULE Conditional]");
		expect(Kind.CONDITIONAL);
		parseExpression();
		parseStmt();
		parseStmt();
		System.out.println("[END RULE Conditional]");
	}

	// Loop --> "loop" Expression Stmt
	private void parseLoop() {
		System.out.println("[RULE Loop]");
		expect(Kind.LOOP);
		parseExpression();
		parseStmt();
		System.out.println("[END RULE Loop]");
	}

	// Block --> "block" Statements
	private void parseBlock() {
		System.out.println("[RULE Block]");
		expect(Kind.BLOCK);
		parseStatements();
		System.out.println("[END RULE Block]");
	}

	// Expression --> Identifier | Literal | "(" Operation Expression Expression ")"
	private void parseExpression() {
		System.out.println("[RULE Expression]");
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
		System.out.println("[END RULE Expression]");
	}

	// Operation --> "+" | "-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" |
	// "or" | "and"
	private void parseOperation() {
		System.out.println("[RULE Operation]");
		switch (currentToken.kind) {
			case OR:
				expect(Kind.OR);
			case AND:
				expect(Kind.AND);
				break;
			default:
				expect(Kind.OPERATOR);
		}
		System.out.println("[END RULE Operation]");
	}
}
