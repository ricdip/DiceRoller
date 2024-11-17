package com.ricdip.interpreters.ast;

import com.ricdip.interpreters.token.TokenType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RollExpression implements Expression {
    private final DiceLiteral dice;
    private final Expression expression;

    @Override
    public TokenType getTokenType() {
        return TokenType.LSQUARE;
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", dice.toString(), expression.toString());
    }
}
