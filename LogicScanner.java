import java.util.*;

public class LogicScanner {

    // Keywords, identifiers, constants, and symbols defined in the grammar
    // Logical operators
    private static final String[] KEYWORDS = {"NOT", "AND", "OR", "IMPLIES", "EQUIVALENT"};

    // Identifiers
    private static final String[] IDENTIFIERS = {"P", "Q", "S"};

    // Booleans
    private static final String[] LITERALS = {"TRUE", "FALSE"};

    // Parentheses
    private static final char[] SYMBOLS = {'(', ')'};

    // Main function of the Logic Scanner
    // "Tokenizes" a given string input by checking it character by character
    // INPUT: String
    // OUTPUT: List of tokens
    // NOTE: We defined Token as a class (Token.java)
    public List<Token> tokenize(String input) {
      
      // ArrayList for tokens
      // Use ArrayList instead of Arrays for flexibility in array size
      List<Token> tokens = new ArrayList<>();
      
      // StringBuilder for the current token being checked
      StringBuilder currentToken = new StringBuilder();
  
      // Check each character in the input string
      for (int i = 0; i < input.length(); i++) {
          char c = input.charAt(i);
  
          // FOR WHITESPACE
          if (Character.isWhitespace(c)) {

              // If there is a token in currentToken and a whitespace is reached
              // Classify the token first
              if (currentToken.length() > 0) {
                  classifyAndAddToken(currentToken.toString(), tokens);
                  currentToken.setLength(0); // Clear the current token
              }

              // If there is no token, just skip the whitespace
              continue;
          }
  
          // FOR SYMBOLS/PARENTHESES
          if (isSymbol(Character.toString(c))) {
              // If there is a token in currentToken and a parenthesis is reached
              // Classify the token first
              if (currentToken.length() > 0) {
                  classifyAndAddToken(currentToken.toString(), tokens);
                  currentToken.setLength(0); // Clear the current token
              }

              // Else, we add the parenthesis
              tokens.add(new Token(Character.toString(c), TokenType.SYMBOL));
          } 

          // FOR LETTERS, append it to currentToken
          else if (Character.isLetter(c)) {
              currentToken.append(c);
          }

          // FOR INVALID SYMBOLS
          else {
              // If there is a token in currentToken and a parenthesis is reached
              // Classify the token first
              if (currentToken.length() > 0) {
                  classifyAndAddToken(currentToken.toString(), tokens);
                  currentToken.setLength(0);
              }
              // Add it as a token immediately with token type unknown
              tokens.add(new Token(Character.toString(c), TokenType.UNKNOWN));
          }
      }
  
      // If there is something in currentToken after the loop, classify and add it
      if (currentToken.length() > 0) {
          classifyAndAddToken(currentToken.toString(), tokens);
      }
  
      // Return the ArrayList of the tokens
      return tokens;
  }
  
  // HELPER FUNCTIONS

  // Reads a given input token, classifies it, and adds it to a list
  // INPUT: Token to be classified and list to be added to
  private void classifyAndAddToken(String token, List<Token> tokens) {

    // Note that we don't check for (, and )
    // Because we put the process for it inside the main loop

      if (isKeyword(token)) { // "NOT", "AND", "OR", "IMPLIES", "EQUIVALENT"
          tokens.add(new Token(token, TokenType.KEYWORD));
      } else if (isIdentifier(token)) { // "P", "Q", "S"
          tokens.add(new Token(token, TokenType.IDENTIFIER));
      } else if (isLiteral(token)) { // "TRUE", "FALSE"
          tokens.add(new Token(token, TokenType.LITERAL));
      } else { // Anything other than those mentioned
          tokens.add(new Token(token, TokenType.UNKNOWN)); // Catch invalid tokens
      }
  }
  

    // Helper methods for token classification
    // Goes through all keywords in the KEYWORDS array
    // And compares it with a given input word
    public boolean isKeyword(String word) {
        for (String keyword : KEYWORDS) {
            if (word.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

      // Goes through all keywords in the IDENTIFIERS array
    // And compares it with a given input word
    public boolean isIdentifier(String word) {
        for (String identifier : IDENTIFIERS) {
            if (word.equals(identifier)) {
                return true;
            }
        }
        return false;
    }

      // Goes through all keywords in the LITERALS array
    // And compares it with a given input word
    public boolean isLiteral(String word) {
        for (String literal : LITERALS) {
            if (word.equals(literal)) {
                return true;
            }
        }
        return false;
    }

    // Goes through all keywords in the SYMBOLS array
    // And compares it with a given input word
    public boolean isSymbol(String word) {
        if (word.length() == 1) {
            char c = word.charAt(0);
            for (char symbol : SYMBOLS) {
                if (c == symbol) {
                    return true;
                }
            }
        }
        return false;
    }
}
