package asteroids.model.program;

import asteroids.model.program.expressions.DoubleLiteral;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.expressions.entityexpressions.*;
import asteroids.model.program.expressions.operations.*;
import asteroids.model.program.statements.*;
import asteroids.model.program.statements.actions.*;
import asteroids.model.program.types.DoubleType;
import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.SourceLocation;

import javax.swing.plaf.nimbus.State;
import java.util.List;

/**
 * Created by WimKunnen on 18/04/2017.
 */
@SuppressWarnings("unchecked")
public abstract class ProgramFactory implements IProgramFactory{

    /**
     * Create a statement that represents the assignment of a variable.
     *
     * @param variableName
     *            The name of the assigned variable
     * @param value
     *            An expression that evaluates to the assigned value
     */
    public Statement createAssignmentStatement(String variableName, Expression value, SourceLocation sourceLocation){
        return new Assignment(variableName, value);
    }

    /**
     * Create a statement that represents a while loop.
     *
     * @param condition
     *            The condition of the while loop
     * @param body
     *            The body of the loop (most likely this is a sequence
     *            statement).
     */
    public Statement createWhileStatement(Expression condition, Statement body, SourceLocation sourceLocation){
        return new While(condition, body);
    }

    /**
     * Create a statement that represents a break statement.
     */
    public Statement createBreakStatement(SourceLocation sourceLocation){
        return new Break();
    }

    /**
     * Create a statement that represents a return statement.
     *
     * @param value
     *            An expression that evaluates to the value to be returned
     */
    public Statement createReturnStatement(Expression value, SourceLocation sourceLocation){
        return new Return(value);
    }

    /**
     * Create an if-then-else statement.
     *
     * @param condition
     *            The condition of the if statement
     * @param ifBody
     *            The body of the if-part, which must be executed when the
     *            condition evaluates to true
     * @param elseBody
     *            The body of the else-part, which must be executed when the
     *            condition evaluates to false. Can be null if no else clause is
     *            specified.
     */
    public Statement createIfStatement(Expression condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation){
        return new If(condition, ifBody, elseBody);
    }

    /**
     * Create a print statement that prints the value obtained by evaluating the
     * given expression.
     *
     * @param value
     *            The expression to evaluate and print
     */
    public Statement createPrintStatement(Expression value, SourceLocation sourceLocation){
        return new Print(value);
    }

    /**
     * Create a sequence of statements.
     *
     * @param statements
     *            The statements that must be executed in the given order.
     */
//    public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation){
//        return new Sequence(statements);
//    }

    /**
     * Create an expression that evaluates to the given expression with changed
     * sign (i.e., negated).
     *
     * @param expression
     */
    public Expression createChangeSignExpression(Expression expression, SourceLocation sourceLocation){
        return new ChangeSign(expression);
    }

    /**
     * Create an expression that evaluates to true when the given expression
     * evaluates to false, and vice versa.
     *
     * @param expression
     */
    public Expression createNotExpression(Expression expression, SourceLocation sourceLocation){
        return new BooleanNegation(expression);
    }

    /**
     * Creates an expression that represents a literal double value.
     */
    public Expression createDoubleLiteralExpression(double value, SourceLocation location){
        return new DoubleLiteral(value);
    }

    /**
     * Creates an expression that represents the null value.
     */
    public Expression createNullExpression(SourceLocation location){
        return new EntityNullLiteral();
    }

    /**
     * Creates an expression that represents the self value, evaluating to the
     * ship that executes the program.
     */
    public Expression createSelfExpression(SourceLocation location){
        return new EntitySelfLiteral();
    }

    //TODO: return correct ship
    /**
     * Creates an expression that evaluates to the ship that is closest to the
     * ship that is executing the program.
     */
    public Expression createShipExpression(SourceLocation location){
        return new ClosestShip();
    }

    /**
     * Returns an expression that evaluates to the position along the x-axis of
     * the entity to which the given expression evaluates.
     */
    public Expression createGetXExpression(Expression e, SourceLocation location){
        return new GetPositionX(e);
    }

    /**
     * Returns an expression that evaluates to the position along the y-axis of
     * the entity to which the given expression evaluates.
     */
    public Expression createGetYExpression(Expression e, SourceLocation location){
        return new GetPositionY(e);
    }

    /**
     * Returns an expression that evaluates to the velocity along the x-axis of
     * the entity to which the given expression evaluates.
     */
    public Expression createGetVXExpression(Expression e, SourceLocation location){
        return new GetVelocityX(e);
    }

    /**
     * Returns an expression that evaluates to the velocity along the y-axis of
     * the entity to which the given expression evaluates.
     */
    public Expression createGetVYExpression(Expression e, SourceLocation location){
        return new GetVelocityY(e);
    }

    /**
     * Returns an expression that evaluates to the radius of the entity to which
     * the given expression evaluates.
     */
    public Expression createGetRadiusExpression(Expression e, SourceLocation location){
        return new GetRadius(e);
    }

    /**
     * Returns an expression that evaluates to true if the evaluation of the
     * first expression yields a value that is less than the value obtained by
     * evaluating the second expression.
     */
    public Expression createLessThanExpression(Expression e1, Expression e2, SourceLocation location){
        return new LesserThan((Expression< DoubleType>) e1, (Expression< DoubleType>)e2);
    }

    /**
     * Returns an expression that evaluates to true if the evaluation of the
     * first expression yields a value that is equal to the value obtained by
     * evaluating the second expression.
     */
    public Expression createEqualityExpression(Expression e1, Expression e2, SourceLocation location){
        return new Equality((Expression< DoubleType>)e1,(Expression< DoubleType>)e2);
    }

    /**
     * Returns an expression that evaluates to the addition of the values
     * obtained by evaluating the first and second given expressions.
     */
    public Expression createAdditionExpression(Expression e1, Expression e2, SourceLocation location){
        return new Addition((Expression< DoubleType>)e1, (Expression< DoubleType>)e2);
    }

    /**
     * Returns an expression that evaluates to the multiplication of the values
     * obtained by evaluating the first and second given expressions.
     */
    public Expression createMultiplicationExpression(Expression e1, Expression e2, SourceLocation location){
        return new Multiplication((Expression< DoubleType>)e1, (Expression< DoubleType>)e2);
    }

    /**
     * Returns an expression that evaluates to the square root of the value
     * obtained by evaluating the given expression.
     */
    public Expression createSqrtExpression(Expression e, SourceLocation location){
        return new SquareRoot((Expression< DoubleType>)e);
    }

    /**
     * Returns a statement that turns the thruster of the ship executing the
     * program on.
     */
    public Statement createThrustOnStatement(SourceLocation location){
        return new Thrust_On();
    }

    /**
     * Returns a statement that turns the thruster of the ship executing the
     * program off.
     */
    public Statement createThrustOffStatement(SourceLocation location){
        return new Thrust_Off();
    }

    /**
     * Returns a statement that fires a bullet from the ship that is executing
     * the program.
     */
    public Statement createFireStatement(SourceLocation location){
        return new Fire();
    }

    /**
     * Returns a statement that makes the ship that is executing the program
     * turn by the given amount.
     */
    public Statement createTurnStatement(Expression angle, SourceLocation location){
        return new Turn((Expression< DoubleType>)angle);
    }

    /**
     * Returns a statement that does nothing.
     */
    public Statement createSkipStatement(SourceLocation location){
        return new Skip();
    }
}
