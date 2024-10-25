import java.util.ArrayList;

public class Question {
    public String question;
    public String[] options;
    public String correctOp;

    public Question(String question, String[] options, String correctOp) {
        this.question = question;
        this.options = options;
        this.correctOp = correctOp;
    }
}
