package com.ricdip.interpreters.ast;

import com.ricdip.interpreters.parser.Operator;
import com.ricdip.interpreters.token.TokenType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PrefixExpression implements Expression {
    private final TokenType tokenType;
    private final Operator operator;
    private final Expression right;

    @Override
    public String toString() {
        return String.format("(%s%s)", operator.getValue(), right.toString());
    }
}
