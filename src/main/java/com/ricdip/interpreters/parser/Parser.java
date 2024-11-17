package com.ricdip.interpreters.parser;

import com.ricdip.interpreters.ast.*;
import com.ricdip.interpreters.lexer.Lexer;
import com.ricdip.interpreters.symbol.DiceType;
import com.ricdip.interpreters.symbol.Symbol;
import com.ricdip.interpreters.token.Token;
import com.ricdip.interpreters.token.TokenType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Parser {
    private final Lexer lexer;
    @Getter
    private final List<String> errors = new ArrayList<>();
    private final Map<TokenType, Supplier<Expression>> prefixParseFunctions = new HashMap<>();
    private final Map<TokenType, Function<Expression, Expression>> infixParseFunctions = new HashMap<>();
    private final Map<Operator, Precedence> operatorPrecedences = new HashMap<>();
    private Token currToken;
    private Token peekToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();

        // associate a token type to specific function that parses prefix expressions
        prefixParseFunctions.put(TokenType.INT, this::parseIntegerLiteral); // treat integer literals as prefix expression
        prefixParseFunctions.put(TokenType.DICE, this::parseDiceLiteral); // treat dice literals as prefix expression
        prefixParseFunctions.put(TokenType.MINUS, this::parsePrefixExpression); // -rightExpression
        prefixParseFunctions.put(TokenType.LPAREN, this::parseGroupedExpression); // (expression)

        // associate a token type to specific function that parses infix expressions
        infixParseFunctions.put(TokenType.PLUS, this::parseInfixExpression); // left + right
        infixParseFunctions.put(TokenType.MINUS, this::parseInfixExpression); // left - right
        infixParseFunctions.put(TokenType.ASTERISK, this::parseInfixExpression); // left * right
        infixParseFunctions.put(TokenType.SLASH, this::parseInfixExpression); // left / right
        infixParseFunctions.put(TokenType.LSQUARE, this::parseRollExpression); // left[right] (left is a dice, right is an expression to sum to every dice roll)

        // associate a specific operator to a precedence (higher number = higher precedence)
        operatorPrecedences.put(Operator.PLUS, Precedence.SUM);
        operatorPrecedences.put(Operator.MINUS, Precedence.SUM);
        operatorPrecedences.put(Operator.ASTERISK, Precedence.PRODUCT);
        operatorPrecedences.put(Operator.SLASH, Precedence.PRODUCT);
        operatorPrecedences.put(Operator.LSQUARE, Precedence.ROLL);
    }

    private boolean hasNextToken() {
        return lexer.hasNext();
    }

    private void nextToken() {
        currToken = peekToken;
        if (hasNextToken()) {
            peekToken = lexer.next();
        }
    }

    public RootASTNode parse() {
        // create an empty root node
        RootASTNode root = new RootASTNode();

        // begin parsing expressions
        root.setExpression(parseExpression(Precedence.LOWEST));

        if (!hasNextToken()) {
            // no token left after parsing: return AST
            return root;
        } else {
            // tokens left after parsing: some error occurred
            errors.add(String.format("EOF expected, got '%s' (%s)", currToken.getLexeme(), currToken.getTokenType()));
            return null;
        }
    }

    private Expression parseExpression(Precedence precedence) {
        Supplier<Expression> prefixFn = prefixParseFunctions.get(currToken.getTokenType());

        if (prefixFn == null) {
            noPrefixParseFnError(currToken.getTokenType());
            return null;
        }

        Expression left = prefixFn.get();

        while (
                precedence.ordinal() < getOperatorPrecedence(peekToken.getTokenType()).ordinal()
        ) {
            Function<Expression, Expression> infixFn = infixParseFunctions.get(peekToken.getTokenType());

            if (infixFn == null) {
                return left;
            }

            nextToken();

            left = infixFn.apply(left);
        }

        return left;
    }

    private IntegerLiteral parseIntegerLiteral() {
        String lexeme = currToken.getLexeme();

        try {
            Integer value = Integer.parseInt(lexeme);
            return new IntegerLiteral(value);
        } catch (Exception e) {
            errors.add(String.format("error while parsing integer literal: %s", e.getMessage()));
            return null;
        }
    }

    private Expression parseGroupedExpression() {
        nextToken();

        Expression groupedExpression = parseExpression(Precedence.LOWEST);

        if (!TokenType.RPAREN.equals(peekToken.getTokenType())) {
            errors.add(String.format("expected token '%s', got '%s'", TokenType.RPAREN, peekToken.getLexeme()));
            return null;
        }

        nextToken();

        return groupedExpression;
    }

    private Expression parseRollExpression(Expression left) {
        // left must be a dice literal
        if (!TokenType.DICE.equals(left.getTokenType())) {
            errors.add(String.format("expected dice literal, got '%s'", left));
            return null;
        }

        nextToken();

        Expression expression = parseExpression(Precedence.LOWEST);

        if (!TokenType.RSQUARE.equals(peekToken.getTokenType())) {
            errors.add(String.format("expected token '%s', got '%s'", TokenType.RPAREN, peekToken.getLexeme()));
            return null;
        }

        nextToken();

        return new RollExpression((DiceLiteral) left, expression);
    }

    private DiceLiteral parseDiceLiteral() {
        String lexeme = currToken.getLexeme();
        String[] splittedDiceString = lexeme.split(String.valueOf(Symbol.DICE));
        Integer numberOfDices = getNumberOfDices(splittedDiceString[0]);

        if (numberOfDices == null) {
            return null;
        }

        DiceType diceType = getDiceType(splittedDiceString[1]);

        if (diceType == null) {
            return null;
        }

        return new DiceLiteral(numberOfDices, diceType);
    }

    private Integer getNumberOfDices(String diceStringPart) {
        try {
            return Integer.parseInt(diceStringPart);
        } catch (Exception e) {
            errors.add(
                    String.format("error while parsing dice literal, illegal number of dices: '%s'", e.getMessage())
            );
            return null;
        }
    }

    private DiceType getDiceType(String diceStringPart) {
        try {
            return DiceType.valueOf("D" + diceStringPart);
        } catch (Exception e) {
            errors.add(
                    String.format("error while parsing dice literal, illegal dice type: 'd%s'", diceStringPart)
            );
            return null;
        }
    }

    private Expression parsePrefixExpression() {
        TokenType currTokenType = currToken.getTokenType();
        Operator operator = Operator.valueOf(currToken.getTokenType().name());

        nextToken();

        Expression right = parseExpression(Precedence.PREFIX);

        return new PrefixExpression(
                currTokenType,
                operator,
                right
        );
    }

    private Expression parseInfixExpression(Expression left) {
        TokenType currTokenType = currToken.getTokenType();
        Operator operator = Operator.valueOf(currToken.getTokenType().name());
        Precedence precedence = getOperatorPrecedence(operator);

        nextToken();

        Expression right = parseExpression(precedence);

        return new InfixExpression(
                currTokenType,
                operator,
                left,
                right
        );
    }

    private void noPrefixParseFnError(TokenType tokenType) {
        errors.add(String.format("no prefix parse function found for '%s'", tokenType.name()));
    }

    private Precedence getOperatorPrecedence(TokenType tokenType) {
        Operator operator = Operator.fromTokenType(tokenType);

        return (operator == null) ? Precedence.LOWEST : operatorPrecedences.getOrDefault(operator, Precedence.LOWEST);
    }

    private Precedence getOperatorPrecedence(Operator operator) {
        return operatorPrecedences.getOrDefault(operator, Precedence.LOWEST);
    }
}

