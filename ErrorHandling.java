import java.util.*;

//Error handling for syntax error ie. P and (not Q
//Input: user input 
//Output: error message if synatx error is detected
public class ErrorHandling {
    //Uses UserInput and Logic Scanner 
    LogicScanner logScan = new LogicScanner();

    public boolean errorCheck(String input) {
        String[] container = spaceWords(input).split(" ");
        List<String> wordContainer = new LinkedList<String> (Arrays.asList(container));

        int wcN = wordContainer.size();
        int count = 0;

        for (int i = 0; i < wcN; i++) {
            String word = wordContainer.get(i);
            String nextWord = " ";

            //Checks if current word is valid
            if (logScan.isKeyword(word) == false &&
                logScan.isIdentifier(word) == false &&
                logScan.isSymbol(word) == false) {

                count = 0;
                
                System.out.println("Error: The token '" + word + "' is not recognized on string '" + input + "'");
                
                return false;
            };

            //Checks if current word and adjacent word is a keyword excluding "NOT", print error if true
            if (i < (wcN-1) && logScan.isKeyword(word) == true) {
                nextWord = wordContainer.get(i+1);

                if (logScan.isKeyword(nextWord) == true && 
                    nextWord.compareTo("NOT") != 0) {
                    
                    errorMessage(input);
                    return false;
                }
            };

            //Checks if current word and adjacent word is an identifier, print error if true
            if (i < (wcN-1) && logScan.isIdentifier(word) == true) {
                nextWord = wordContainer.get(i+1);

                if (logScan.isIdentifier(nextWord) == true) {
                    errorMessage(input);
                    return false;
                }
            };

            //Checks if starting word is keyword excluding not, print error if true
            if (i == 0) {
                if (logScan.isKeyword(word) == true && 
                    word.compareTo("NOT") != 0) {
                   
                    errorMessage(input);
                    return false;
                }
            };

            //Checks if last word is keyword, print error if true
            if (i == (wcN-1)) {
                if (logScan.isKeyword(word) == true) {
                    
                    errorMessage(input);
                    return false;
                }
            };

            //Grouping Checker
            if (word.compareTo("(") == 0) {
                count++;
                
                //Checks if next word from "(" is and identifier, if true error
                if (i < wcN-1) {
                    nextWord = wordContainer.get(i+1);

                    if (logScan.isKeyword(nextWord) == true && 
                        nextWord.compareTo("NOT") != 0) {
                            
                        count = 0;
                        
                        errorMessage(input);
                        return false;
                    }
                }
            };

            if (word.compareTo(")") == 0) {
                count--;

                //Checks if ")" is detected before open parenthesis
                if (count < 0) {
                    count = 0;
                    
                    System.out.println("Error: Close parenthesis detected before open parenthesis on string '" + input + "'");

                    return true;
                };

                //Checks if last token before ")" is an identifier, if true error
                if (i > 0) {
                    nextWord = wordContainer.get(i-1);

                    if (logScan.isKeyword(nextWord) == true) {
                        count = 0;
                        
                        errorMessage(input);
                        return false;
                    }
                };
            };

        };

        //Checks if grouping was done properly
        if (count != 0) {
            count = 0;

            System.out.println("Error: Incomplete grouping, please add a close parenthesis at the end of the string '" + input + "'");

            return false;
        };

        return true;

    };

    //Helper function
    //Puts a space before and after a symbol 
    private String spaceWords(String input) {
        StringBuilder spacedWord = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '(') {
                spacedWord.append(c);
                spacedWord.append(" ");

                continue;
            };

            if (c == ')') {
                spacedWord.append(" ");
                spacedWord.append(c);

                continue;
            };

            spacedWord.append(c);

        };

        return spacedWord.toString();

    };

    //Error message for incorrect format of logic
    private void errorMessage(String input) {
        
        System.out.println("Error: The string '" + input + "' does not make sense, please try again");

    };

};
