package com.ricdip.interpreters.ast;

import com.ricdip.interpreters.token.TokenType;

public interface Expression extends ASTNode {
    TokenType getTokenType();
}
