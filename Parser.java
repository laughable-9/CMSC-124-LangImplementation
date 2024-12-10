
import java.util.*;

public class Parser {

    private List<Token> forTokens; // this is the list of inputted tokens that we got from the scanner
    private int tokenIndexValue;     // this is the pointer of the current token

    public Parser(List<Token> forTokens) {
        this.forTokens = forTokens;
        this.tokenIndexValue = 0;
    }

    // this validates the grammar of tokens
    public void parsePlease() { 
        parseSentence(); // this will start on parsing the sentence
        if (tokenIndexValue < forTokens.size()) {
            throw new SyntaxError("\n Unexpected token: " + currentToken().getValue());
        }
    }

    
    private void parseSentence() {
        parseAtomicOrComplex(); // this will going to parse the atomic or complex sentence

        // for handling the operators (or, and, etc.)
        while (tokenIndexValue < forTokens.size() && currentToken().getType() == TokenType.KEYWORD) {
            consumeToken(); // Consume the operator
            parseAtomicOrComplex(); // parse rhs
        }
    }

    // parse the atomic or comp
    private void parseAtomicOrComplex() {
        if (tokenIndexValue >= forTokens.size()) {
            throw new SyntaxError("\n Unexpected end of input. Please try again.");
        }

        Token token = currentToken();

        if (token.getType()
        		== TokenType.SYMBOL 
        		&& token.getValue().equals("(")) {
            consumeToken(); // for ( token
            parseSentence(); // this will parse inner sentence
            if (tokenIndexValue >= forTokens.size() || !currentToken().getValue().equals(")")) { // [arse parenthesis
                throw new SyntaxError("Expected ')' but found: " +
                        (tokenIndexValue < forTokens.size() ? currentToken().getValue() : "end of input"));
            }
            consumeToken(); // for ) token
        } else if (token.getType() // check if iden or literal
        		== TokenType.IDENTIFIER 
        		|| token.getType() 
        		== TokenType.LITERAL) {
        	
            parseAtomic(); // this will parse the atomic
            
        } else if (token.getType() == TokenType.KEYWORD && token.getValue().equals("NOT")) { // not keyword 
            consumeToken(); // for not
            
            parseSentence(); // parse negation
            
            
        } else { // displaying error message
            throw new SyntaxError("\n Unexpected token: " + token.getValue());
        }
    }

    // parse true false p q s
    private void parseAtomic() {
        Token token = currentToken();
        if (token.getType() // iden or literal
        		== TokenType.IDENTIFIER 
        		|| token.getType() 
        		== TokenType.LITERAL) {
            consumeToken(); // for atomic
            
            
        } else { // display error message
            throw new SyntaxError("Expected an atomic sentence but found: " + token.getValue());
        }
    }

    // this will get current token
    private Token currentToken() {
        if (tokenIndexValue < forTokens.size()) {
            return forTokens.get(tokenIndexValue);
        } else {
            throw new NoSuchElementException("Oops. No more Tokens available :{");
        }
    }

    // next token
    
    private void consumeToken() {
        if (tokenIndexValue < forTokens.size()) {
            tokenIndexValue++;
        } else {
            throw new NoSuchElementException("Oops. No more Tokens to consume :{");
        }
    }
}

// this is the exception class for syntax errors
class SyntaxError extends RuntimeException {
    public SyntaxError(String message) {
        super(message);
    }
}
