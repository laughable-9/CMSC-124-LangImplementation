import java.util.*;

public class Parser {

    private List<Token> forTokens; // List of inputted tokens obtained from the scanner
    private int tokenIndexValue;     // Pointer of the current token

    public Parser(List<Token> forTokens) {
        this.forTokens = forTokens;
        this.tokenIndexValue = 0;
    }

    // Validates the grammar of tokens
    public Sentence parsePlease() { 
        Sentence sentence = parseSentence(); // Starts parsing the sentence
        if (tokenIndexValue < forTokens.size()) {
            throw new SyntaxError("\n Unexpected token: " + currentToken().getValue());
        }
        return sentence;
    }

    
    private Sentence parseSentence() {
        Sentence left = parseAtomicOrComplex(); // Parses the left side of the sentence

        // Handles the connectives
        while (tokenIndexValue < forTokens.size() && currentToken().getType() == TokenType.KEYWORD) {
            String connective = currentToken().getValue(); // Gets the connective
            consumeToken(); // Consumes the connective
            Sentence right = parseAtomicOrComplex(); // Parses the right side of the sentence
            left = new ComplexSentence(left,connective,right); // Constructs a new ComplexSentence with the left and right parts
        }
        return left;
    }

    // Parses the Atomic/Complex sentence, then returns the corresponding Sentence object
    private Sentence parseAtomicOrComplex() {
        if (tokenIndexValue >= forTokens.size()) {
            throw new SyntaxError("\n Unexpected end of input. Please try again.");
        }

        Token token = currentToken();

        if (token.getType()
        		== TokenType.SYMBOL 
        		&& token.getValue().equals("(")) {
            consumeToken(); // For "(" token
            Sentence innerSentence = parseSentence(); // Parses the inner sentence
            if (tokenIndexValue >= forTokens.size() || !currentToken().getValue().equals(")")) { // Parse parenthesis
                throw new SyntaxError("Expected ')' but found: " +
                        (tokenIndexValue < forTokens.size() ? currentToken().getValue() : "end of input"));
            }
            consumeToken(); // For ")" token
            return innerSentence;
        } else if (token.getType() // Check if Identifier/Literal
        		== TokenType.IDENTIFIER 
        		|| token.getType() 
        		== TokenType.LITERAL) {
        	return parseAtomic(); // Parses the Atomic sentence
        } else if (token.getType() == TokenType.KEYWORD && token.getValue().equals("NOT")) { // not keyword 
            consumeToken(); // For "not" token
            Sentence notSentence = parseAtomicOrComplex();
            return new Not(notSentence);          
        } else { // Displays error message
            throw new SyntaxError("\n Unexpected token: " + token.getValue());
        }
    }

    // Parses Atomic sentence
    private Sentence parseAtomic() {
        Token token = currentToken();
        // Identifier
        if (token.getType() == TokenType.IDENTIFIER) {
            consumeToken(); // Consumes Atomic token
            return new Variable(token.getValue());
        // Literal
        } else if (token.getType() == TokenType.LITERAL) {
            consumeToken(); // Consumes Atomic token
            return new Variable(token.getValue());
        } else { // Displays error message
            throw new SyntaxError("Expected an atomic sentence but found: " + token.getValue());
        }
    }

    // Gets current token
    private Token currentToken() {
        if (tokenIndexValue < forTokens.size()) {
            return forTokens.get(tokenIndexValue);
        } else {
            throw new NoSuchElementException("Oops. No more Tokens available :{");
        }
    }

    // Next token
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
