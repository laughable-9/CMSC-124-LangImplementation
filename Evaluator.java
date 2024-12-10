import java.util.HashMap;
import java.util.Map;


abstract class Sentence {
    abstract boolean eval(Evaluator evaluator);
}
class AtomicSentence extends Sentence {
    private Boolean val;
    
    public AtomicSentence(Boolean val) {
        this.val = val;
    }
    public boolean eval(Evaluator evaluator) {
        return val;
    }
}
class Variable extends Sentence {
    private String var;

    public Variable(String var) {
        this.var = var;
    }
    public boolean eval(Evaluator evaluator) {
        return evaluator.getvarVal(var);
    }
}
class ComplexSentence extends Sentence {
    private Sentence left, right;
    private String connective;

    public ComplexSentence(Sentence left, String connective, Sentence right) {
        this.left = left;
        this.connective = connective;
        this.right = right;
    }
    public boolean eval(Evaluator evaluator) {
        switch(connective) {
            case "AND":
                return left.eval(evaluator) && right.eval(evaluator);
            case "OR":
                return left.eval(evaluator) || right.eval(evaluator);
            case "IMPLIES":
                return !left.eval(evaluator) || right.eval(evaluator);
            case "EQUIVALENT":
                return left.eval(evaluator) == right.eval(evaluator);
            default:
                throw new IllegalArgumentException("Unknown connective");
        }
    }
}
class Not extends Sentence {
    private Sentence sentence;

    public Not(Sentence sentence) {
        this.sentence = sentence;
    }
    public boolean eval(Evaluator evaluator) {
        return !sentence.eval(evaluator);
    }
}

public class Evaluator {
    public Map<String, Boolean> varVal;

    public Evaluator() {
        varVal = new HashMap<>();
    }

    public void setvarVal(String var, Boolean val) {
        varVal.put(var,val);
    }

    public boolean getvarVal(String var) {
        if(!varVal.containsKey(var)) {
            throw new IllegalArgumentException("No value assigned to variable");
        }
        return varVal.get(var);
    }

    public boolean evaluate(Sentence sentence) {
        return sentence.eval(this);
    }
}
