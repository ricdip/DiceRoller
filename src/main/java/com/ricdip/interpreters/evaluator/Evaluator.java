package com.ricdip.interpreters.evaluator;

import com.ricdip.interpreters.ast.*;
import com.ricdip.interpreters.object.*;
import com.ricdip.interpreters.parser.Operator;
import com.ricdip.interpreters.random.DiceRoller;
import com.ricdip.interpreters.symbol.DiceType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * class that traverses the AST returning the result of the evaluation.
 */
@RequiredArgsConstructor
public class Evaluator {
    private final DiceRoller diceRoller;
    @Getter
    private final List<String> prints = new ArrayList<>();

    /**
     * Evaluates an {@link ASTNode} object and returns its evaluation.
     *
     * @param node a {@link ASTNode} object that is a parsed node of the AST.
     * @return the evaluation of the parsed node.
     */
    public EvaluatedObject evaluate(ASTNode node) {
        if (node instanceof RootASTNode rootNode) {

            return evaluate(rootNode.getExpression());

        } else if (node instanceof PrefixExpression prefixExpression) {
            EvaluatedObject right = evaluate(prefixExpression.getRight());

            return evaluatePrefixExpression(prefixExpression.getOperator(), right);

        } else if (node instanceof InfixExpression infixExpression) {
            EvaluatedObject left = evaluate(infixExpression.getLeft());
            EvaluatedObject right = evaluate(infixExpression.getRight());

            return evaluateInfixExpression(infixExpression.getOperator(), left, right);

        } else if (node instanceof DiceLiteral diceLiteral) {

            return evaluateDiceLiteral(diceLiteral.getNumberOfDices(), diceLiteral.getDiceType());

        } else if (node instanceof IntegerLiteral integerLiteral) {

            return new IntegerObject(integerLiteral.getValue());

        } else if (node instanceof RollExpression rollExpression) {
            EvaluatedObject expression = evaluate(rollExpression.getExpression());

            return evaluateSeparatedDiceRollExpression(
                    rollExpression.getDice().getNumberOfDices(),
                    rollExpression.getDice().getDiceType(),
                    expression
            );

        } else {

            return new ErrorObject("illegal evaluated node: '%s'", node.toString());

        }
    }

    private EvaluatedObject evaluateInfixExpression(Operator operator, EvaluatedObject left, EvaluatedObject right) {
        if (ObjectType.INT.equals(left.type()) && ObjectType.INT.equals(right.type())) {

            return evaluateInfixExpressionIntegerInteger(operator, left, right);

        } else if (ObjectType.DICE_ROLL.equals(left.type()) && ObjectType.INT.equals(right.type())) {

            return evaluateInfixExpressionIntegerDice(operator, left, right);

        } else if (ObjectType.INT.equals(left.type()) && ObjectType.DICE_ROLL.equals(right.type())) {

            return evaluateInfixExpressionIntegerDice(operator, left, right);

        } else if (ObjectType.DICE_ROLL.equals(left.type()) && ObjectType.DICE_ROLL.equals(right.type())) {

            return evaluateInfixExpressionDiceDice(operator, left, right);

        } else if (ObjectType.ERROR.equals(left.type())) {

            return left;

        } else if (ObjectType.ERROR.equals(right.type())) {

            return right;

        } else {

            return new ErrorObject("incompatible types for infix expression: '%s %s %s'", left.type(), operator.getValue(), right.type());

        }
    }

    private EvaluatedObject evaluateInfixExpressionIntegerDice(Operator operator, EvaluatedObject left, EvaluatedObject right) {
        IntegerObject leftDiceResult = null;
        IntegerObject rightDiceResult = null;

        if (ObjectType.INT.equals(left.type())) {
            leftDiceResult = (IntegerObject) left;
        } else if (ObjectType.DICE_ROLL.equals(left.type())) {
            leftDiceResult = new IntegerObject(((DiceRollObject) left).getResult());
        } else {
            return new ErrorObject("unexpected type for left operand of infix expression: '%s'", left.type());
        }

        if (ObjectType.INT.equals(right.type())) {
            rightDiceResult = (IntegerObject) right;
        } else if (ObjectType.DICE_ROLL.equals(right.type())) {
            rightDiceResult = new IntegerObject(((DiceRollObject) right).getResult());
        } else {
            return new ErrorObject("unexpected type for right operand of infix expression: '%s'", right.type());
        }

        return evaluateInfixExpressionIntegerInteger(operator, leftDiceResult, rightDiceResult);
    }

    private EvaluatedObject evaluateInfixExpressionDiceDice(Operator operator, EvaluatedObject left, EvaluatedObject right) {
        Integer leftDiceResult = ((DiceRollObject) left).getResult();
        Integer rightDiceResult = ((DiceRollObject) right).getResult();

        return evaluateInfixExpressionIntegerInteger(operator, new IntegerObject(leftDiceResult), new IntegerObject(rightDiceResult));
    }

    private EvaluatedObject evaluateInfixExpressionIntegerInteger(Operator operator, EvaluatedObject left, EvaluatedObject right) {
        Integer leftValue = ((IntegerObject) left).getValue();
        Integer rightValue = ((IntegerObject) right).getValue();

        return switch (operator) {
            case PLUS -> new IntegerObject(leftValue + rightValue);
            case MINUS -> new IntegerObject(leftValue - rightValue);
            case ASTERISK -> new IntegerObject(leftValue * rightValue);
            case SLASH -> new IntegerObject((int) Math.floor((float) leftValue / rightValue));
            case LSQUARE ->
                    new ErrorObject("unexpected operator for infix expression: '%d %s %d'", leftValue, operator.getValue(), rightValue);
        };
    }

    private EvaluatedObject evaluatePrefixExpression(Operator operator, EvaluatedObject right) {
        return switch (operator) {
            case MINUS -> evaluatePrefixExpressionMinus(right);
            default ->
                    new ErrorObject("unexpected operator for prefix expression: '%s%s'", operator.getValue(), right.type());
        };
    }

    private EvaluatedObject evaluatePrefixExpressionMinus(EvaluatedObject right) {
        if (ObjectType.INT.equals(right.type())) {
            IntegerObject integerObject = (IntegerObject) right;

            return new IntegerObject(-integerObject.getValue());

        } else if (ObjectType.ERROR.equals(right.type())) {

            return right;

        } else {

            return new ErrorObject("unexpected type for prefix expression with '-' operator: '-%s'", right.type());

        }
    }

    private EvaluatedObject evaluateDiceLiteral(Integer numberOfDices, DiceType diceType) {
        List<Integer> results = new ArrayList<>();
        int result = 0;

        // execute <numberOfDices> rolls
        for (int i = 0; i < numberOfDices; i++) {
            results.add(diceRoller.roll(diceType));
        }

        result = results.stream().reduce(result, (partialResult, currentValue) -> partialResult + currentValue);

        DiceRollObject diceRollObject = new DiceRollObject(
                numberOfDices,
                diceType,
                results,
                result
        );

        prints.add(diceRollObject.toDetailsString());

        return diceRollObject;
    }

    private EvaluatedObject evaluateSeparatedDiceRollExpression(Integer numberOfDices, DiceType diceType, EvaluatedObject expression) {
        Integer expressionValue = null;
        if (ObjectType.INT.equals(expression.type())) {
            expressionValue = ((IntegerObject) expression).getValue();
        } else if (ObjectType.ERROR.equals(expression.type())) {
            return expression;
        } else {
            return new ErrorObject("unexpected type inside dice roll expression: '%s'", expression.type());
        }

        List<Integer> diceResults = new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        int randomResult = 0;

        // execute <numberOfDices> rolls
        for (int i = 0; i < numberOfDices; i++) {
            randomResult = diceRoller.roll(diceType);
            diceResults.add(randomResult);
            results.add(randomResult + expressionValue);
        }

        SeparatedDiceRollObject separatedDiceRollObject = new SeparatedDiceRollObject(
                numberOfDices,
                diceType,
                diceResults,
                expressionValue,
                results
        );

        prints.add(separatedDiceRollObject.toDetailsString());

        return separatedDiceRollObject;
    }
}
