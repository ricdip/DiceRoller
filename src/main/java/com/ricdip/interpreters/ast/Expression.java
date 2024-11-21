package com.ricdip.interpreters.ast;

import com.ricdip.interpreters.token.TokenType;

/**
 * Interface implemented by each AST node that is also an expression (a node that produces a value).
 */
public interface Expression extends ASTNode {
    TokenType getTokenType();
}
