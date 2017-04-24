package asteroids.program.expressions;

/**
 * Created by WimKunnen on 23/04/2017.
 */
public abstract class BinaryExpression extends Expression {

    public BinaryExpression(String operand1, String operand2, String operator){
        this.setOperand1(operand1);
        this.setOperand2(operand2);
        this.setOperator(operator);
    }

    private String operand1;

    public String getOperand1() {
        return operand1;
    }

    private void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    private String operand2;

    public String getOperand2() {
        return operand2;
    }

    private void setOperand2(String operand2) {
        this.operand2 = operand2;
    }

    private String operator;

    public String getOperator() {
        return operator;
    }

    private void setOperator(String operator) {
        this.operator = operator;
    }
}
