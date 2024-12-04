import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize the scanner
        LogicScanner scanner = new LogicScanner();

        // Example input sentence in propositional logic
        String input = "(P AND Q)";

        // Tokenize the input using the scanner
        List<Token> tokens = scanner.tokenize(input);

        // Display the tokens
        System.out.println("Scanned Tokens:");
        for (Token token : tokens) {
            token.displayToken(); // show the tokens
        }
    }
}
