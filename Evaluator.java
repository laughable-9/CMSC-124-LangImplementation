import java.util.HashMap;
import java.util.Map;

// Class for Logical sentence
abstract class Sentence {
    // Evaluator method that evaluates received sentence
    abstract boolean eval(Evaluator evaluator);
}
// Class for Atomic sentence
class AtomicSentence extends Sentence {
    private Boolean val;
    // Initializes a Boolean value
    public AtomicSentence(Boolean val) {
        this.val = val;
    }
    // Evaluates the Atomic sentence and gets the stored truth value
    public boolean eval(Evaluator evaluator) {
        return val;
    }
}
// Class for Variable in a expression
class Variable extends Sentence {
    private String var;
    // Initializes variable
    public Variable(String var) {
        this.var = var;
    }
    // Evaluates the variable and gets it truth value
    public boolean eval(Evaluator evaluator) {
        return evaluator.getvarVal(var);
    }
}
// Class for Complex sentence
class ComplexSentence extends Sentence {
    private Sentence left, right;
    private String connective;
    // Initializes the two operands (left,right) and the logical connective.
    public ComplexSentence(Sentence left, String connective, Sentence right) {
        this.left = left;
        this.connective = connective;
        this.right = right;
    }
    // Evaluates the Complex Sentence based on the received connective
    public boolean eval(Evaluator evaluator) {
        switch(connective) {
            case "AND":
                // Both left and right must be TRUE to get TRUE
                return left.eval(evaluator) && right.eval(evaluator);
            case "OR":
                // At least one TRUE to get TRUE
                return left.eval(evaluator) || right.eval(evaluator);
            case "IMPLIES":
                // If left is TRUE and right is FALSE, output is FALSE, else TRUE
                return !left.eval(evaluator) || right.eval(evaluator);
            case "EQUIVALENT":
                // Left and right must have equal truth values to get TRUE
                return left.eval(evaluator) == right.eval(evaluator);
            default:
                throw new IllegalArgumentException("Unknown connective");
        }
    }
}
// Class for Not/Negation 
class Not extends Sentence {
    private Sentence sentence;
    // Initializes sentence
    public Not(Sentence sentence) {
        this.sentence = sentence;
    }
    // Evaluates sentence by negating/inverting its truth value
    public boolean eval(Evaluator evaluator) {
        return !sentence.eval(evaluator);
    }
}

// Evaluates the logical sentence
public class Evaluator {
    public Map<String, Boolean> varVal;
    // Initializing evaluator with an empty map
    public Evaluator() {
        varVal = new HashMap<>();
    }
    // Sets truth value for a variable
    public void setvarVal(String var, Boolean val) {
        varVal.put(var,val);
    }
    // Gets the truth value of a variable
    public boolean getvarVal(String var) {
        if(!varVal.containsKey(var)) {
            throw new IllegalArgumentException("No value assigned to variable");
        }
        return varVal.get(var);
    }
    // Evaluates the sentence 
    public boolean evaluate(Sentence sentence) {
        return sentence.eval(this);
    }
    // Generates the truth table for the sentence and variables
    public void evaluateTable(Sentence sentence, String[] variables) {
        int varNum = variables.length;            // Number of variables (n)
        int combNum = (int) Math.pow(2,varNum);   // Number of combinations of variables (2^n)
    
        for(int i=0; i<combNum; i++) {
            for(int j=0; j<varNum; j++) {
                // Calculates truth value for the variable (0 = false, 1 = true)
                boolean value = ((i >> (varNum - 1 - j)) & 1) == 0;
                setvarVal(variables[j],value);
            }
    
            boolean result = evaluate(sentence);
    
            String row = "";
            for(String variable:variables) {
                row +=(getvarVal(variable)?"True":"False")+"\t|";
            }
            row += result;
            System.out.println(row);
        }
    }
}
