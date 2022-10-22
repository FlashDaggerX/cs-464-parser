Reminders:
1. You do not need a separate token (number) for each operator. All of them should be translated to one token (number) which is OPERATOR. See below
2. In the switch the code for identifier and literal is complete. Do not change it
3. Symbol `e` means epsilon.

BNF grammar of Mini Language:

	Program" --> "("Sequence State")".
	Sequence --> "("Statements")".
	Statements --> Statements	Stmt | e
	Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block} ")".
	State -->	"("Pairs")".
	Pairs -->	Pairs Pair | e.
	Pair --> "("Identifier Literal")".
	NullStatement --> "skip".
	Assignment --> "assign" Identifier Expression.
	Conditional --> "conditional" Expression Stmt Stmt.
	Loop --> "loop" Expression Stmt.
	Block --> "block" Statements.
	Expression --> Identifier | Literal | "("Operation Expression Expression")".
	Operation --> "+" | "-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".

**Treat Identifier and Literal as terminal symbols. Every symbol inside `"` and `"` is a terminal symbol. The rest are non terminals.**

Input file: `test.txt`

Output:

	Line: 1, spelling = [(], kind = 9
	Line: 1, spelling = [)], kind = 10
	Line: 1, spelling = [sum], kind = 0
	Line: 1, spelling = [a], kind = 0
	Line: 1, spelling = [2], kind = 1
	Line: 1, spelling = [xyz], kind = 0
	Line: 2, spelling = [skip], kind = 6
	Line: 2, spelling = [assign], kind = 2
	Line: 2, spelling = [conditional], kind = 3
	Line: 2, spelling = [loop], kind = 4
	Line: 2, spelling = [block], kind = 5
	Line: 3, spelling = [1234], kind = 1
	Line: 4, spelling = [+], kind = 11
	Line: 4, spelling = [-], kind = 11
	Line: 4, spelling = [*], kind = 11
	Line: 4, spelling = [/], kind = 11
	Line: 4, spelling = [<], kind = 11
	Line: 4, spelling = [<=], kind = 11
	Line: 4, spelling = [>], kind = 11
	Line: 4, spelling = [>=], kind = 11
	Line: 4, spelling = [=], kind = 11
	Line: 4, spelling = [!=], kind = 11
	Line: 4, spelling = [or], kind = 8
	Line: 4, spelling = [and], kind = 7
	Line: 5, spelling = [-], kind = 11
	Line: 5, spelling = [1234], kind = 1
	Line 6: wrong token !

**After you get an error message for the symbol `=` remove this symbol and
run the program. Repeat this until the last wrong token which is: `?`**

You should get the following error messges:

	Line 6: wrong token `!`
	Line 6: wrong token `?`
