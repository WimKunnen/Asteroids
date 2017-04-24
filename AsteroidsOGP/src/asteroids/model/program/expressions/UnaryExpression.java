package asteroids.model.program.expressions;

/**
 * Created by WimKunnen on 23/04/2017.
 */
public abstract class UnaryExpression extends Expression {

    public UnaryExpression(String operand){
        this.setOperand(operand);
    }

    private String operand;

    public String getOperand() {
        return operand;
    }

    private void setOperand(String operand) {
        this.operand = operand;
    }
}
