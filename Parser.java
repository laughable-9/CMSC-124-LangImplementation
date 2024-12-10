import java.util.*;

public class Parser {

    private List<Token> forTokens; // this is the list of input
    private int tokenIndexValue;     // this is the pointer of 

    public Parser(List<Token> forTokens) {
        this.forTokens = forTokens;
        this.tokenIndexValue = 0;
    }

    // this validates the grammar of tokens
    public void parsePlease() { 
        parseSentence(); // this will start on parsing the sent
        if (tokenIndexValue < forTokens.size()) {
            throw new SyntaxError("\n Unexpected token: " + cur
        }
    }

    
    private void parseSentence() {
        parseAtomicOrComplex(); // this will going to parse the

        // for handling the operators (or, and, etc.)
        while (tokenIndexValue < forTokens.size() && currentTok
            consumeToken(); // Consume the operator
            parseAtomicOrComplex(); // parse rhs
        }
    }

    // parse the atomic or comp
    private void parseAtomicOrComplex() {
        if (tokenIndexValue >= forTokens.size()) {
            throw new SyntaxError("\n Unexpected end of input. 
        }

        Token token = currentToken();

        if (token.getType()
        		== TokenType.SYMBOL 
        		&& token.getValue().equals("(")) {
            consumeToken(); // for ( token
            parseSentence(); // this will parse inner sentence
            if (tokenIndexValue >= forTokens.size() || !current
                throw new SyntaxError("Expected ')' but found: 
                        (tokenIndexValue < forTokens.size() ? c
            }
            consumeToken(); // for ) token
        } else if (token.getType() 
        		== TokenType.IDENTIFIER 
        		|| token.getType() 
        		== TokenType.LITERAL) {
        	
            parseAtomic(); // this will parse the atomic
            
        } else if (token.getType() == TokenType.KEYWORD && toke
            consumeToken(); // for not
            
            parseSentence(); // parse negation
            
            
        } else { // displaying error message
            throw new SyntaxError("\n Unexpected token: " + tok
        }
    }

    // parse true false p q s
    private void parseAtomic() {
        Token token = currentToken();
        if (token.getType()
        		== TokenType.IDENTIFIER 
        		|| token.getType() 
        		== TokenType.LITERAL) {
            consumeToken(); // for atomic
            
            
        } else { // display error message
            throw new SyntaxError("Expected an atomic sentence 
        }
    }

    // this will get current token
    private Token currentToken() {
        if (tokenIndexValue < forTokens.size()) {
            return forTokens.get(tokenIndexValue);
        } else {
            throw new NoSuchElementException("Oops. No more Tok
        }
    }

    // next token
    
    private void consumeToken() {
        if (tokenIndexValue < forTokens.size()) {
            tokenIndexValue++;
        } else {
            throw new NoSuchElementException("Oops. No more Tok
        }
    }
}

// this is the exception class for syntax errors
class SyntaxError extends RuntimeException {
    public SyntaxError(String message) {
        super(message);
    }
}
