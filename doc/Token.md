**You do not need a separate token (number) for each operator. All of them
should be translated to one token (number) which is OPERATOR. See below**

	Program --> Sequence State.
	Sequence --> (Statements ).
	Statements --> Statements Stmt | Stmt
	Stmt --> NullStatement | Assignment | Conditional | Loop | Block.
	State -->  (Pairs).
	Pairs --> Pairs Pair | Pair.
	Pair --> (Identifier Literal).
	NullStatement --> (skip).
	Assignment --> (assign Identifier Expression).
	Conditional --> (conditional Expression Stmt Stmt).
	Loop --> (loop Expression Stmt).
	Block --> (block Statements).
	Expression --> Identifier | Literal | (Operation Expression Expression).
	Operation --> + | - | * | / | < | <= | > | >= | = | != | or | and.
