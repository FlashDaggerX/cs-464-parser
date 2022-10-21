## Complete all the methods.

EBNF of Mini Language

	Program" --> "("Sequence State")".
	Sequence --> "("Statements")".
	Statements --> Stmt*
	Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
	State -->  "("Pairs")".
	Pairs -->  Pair*.
	Pair --> "("Identifier Literal")".
	NullStatement --> "skip".
	Assignment --> "assign" Identifier Expression.
	Conditional --> "conditional" Expression Stmt Stmt.
	Loop --> "loop" Expression Stmt.
	Block --> "block" Statements.
	Expression --> Identifier | Literal | "("Operation Expression Expression")".
	Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".

**Treat Identifier and Literal as terminal symbols. Every symbol inside `"` and `"` is
a terminal symbol. The rest are non terminals.**
