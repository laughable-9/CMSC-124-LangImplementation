// Enum for token types

public enum TokenType {
  KEYWORD,      // "NOT", "AND", "OR", "IMPLIES", "EQUIVALENT"
  IDENTIFIER,   // "P", "Q", "S"
  LITERAL,      // "TRUE", "FALSE"
  SYMBOL,       // "(", ")"
  UNKNOWN       // For invalid tokens
}
