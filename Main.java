import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String userInput;

        // Continuously prompt the user for input
        while (true) {
            System.out.print("Enter command: ");

            // Read the user's input
            userInput = scanner.nextLine();

            // Check if the user input starts with "LOGIC"
            if (userInput.startsWith("LOGIC ")) {
                // Extract the filename part by removing "LOGIC "
                String fileName = userInput.substring(6).trim();

                // Check if the filename ends with ".pl"
                if (fileName.endsWith(".pl")) {
                    try {
                        // Read the file content
                        String fileContent = readFile(fileName);

                        // Tokenize the content using LogicScanner
                        LogicScanner logicScanner = new LogicScanner();
                        List<Token> tokens = logicScanner.tokenize(fileContent);

                        // Display the tokens (FOR DEBUGGING)
                        System.out.println("\nTokens:");
                        for (Token token : tokens) {
                            token.displayToken();
                        }
                        // this is for validating the parser
                        try {
                            Parser parser = new Parser(tokens); // Pass tokens to the parser
                            Sentence parsedSentence = parser.parsePlease();
                            
                            Set<String> varSet = new HashSet<>();
                            for(Token token:tokens) {
                                if(token.getType()==TokenType.IDENTIFIER) {
                                    varSet.add(token.getValue());
                                }
                            }
                            String[] variables = varSet.toArray(new String[0]);
                            System.out.println("\nTruth Table of: "+ fileContent.trim());
                            for(String variable:variables) {
                               System.out.print("\t|"+variable+"\t|"); 
                            }
                            System.out.print("\t| "+ fileContent.trim()+"\n");

                            Evaluator evaluator = new Evaluator();
                            evaluator.evaluateTable(parsedSentence,variables);

                        } catch (SyntaxError message) {
                        	System.out.println("\n Oops. Your input seems have an error." + message.getMessage());
                        }
                        
                        
                        break; // Exit the loop after successfully processing the file

                    } catch (IOException e) {
                        // Handle file reading errors
                        System.out.println("Error: Could not read the file. Please check the filename and try again.");
                    }
                } else {
                    System.out.println("Error: The file must have a .pl extension.");
                }
            } else {
                // Ignore any input that doesn't start with "LOGIC "
                System.out.println("Invalid command. Please type 'LOGIC filename.pl' to process a file.");
            }
        }
    }

    // HELPER FUNCTION

    // Reads the content of a file and returns it as a string
    // INPUT: Name of the file (String)
    // OUTPUT: Content of the file (String)
    private static String readFile(String fileName) throws IOException {
        // Initialize a StringBuilder to efficiently store the file content
        StringBuilder content = new StringBuilder();

        // Use a BufferedReader to read the file line by line
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        // Loop through each line in the file until no lines are left
        while ((line = reader.readLine()) != null) {
            //Ends program if error occurs
            ErrorHandling errorHandle = new ErrorHandling();
            if (errorHandle.errorCheck(line) == false) {
                System.exit(0);
            }

            // Append the current line to the content and add a newline character
            content.append(line).append("\n");
        }

        reader.close();

        // Convert the StringBuilder to a String and remove trailing spaces or newlines
        return content.toString().trim();
    }
}
