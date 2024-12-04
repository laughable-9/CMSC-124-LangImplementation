// TOKEN CLASS

public class Token {
  
  // Value of the token: "P", "TRUE", etc.
  private String value;
  // Type of the token: "IDENTIFIER", "SYMBOL", etc.
  private TokenType type;

  // Constructor for the Token
  public Token(String value, TokenType type) {
      this.value = value;
      this.type = type;
  }

  // Getters
  public String getValue() {
      return value;
  }

  public TokenType getType() {
      return type;
  }
  // Method to display the token
  public void displayToken() {
    System.out.println("Token Value: " + value + ", Token Type: " + type);
  }
}