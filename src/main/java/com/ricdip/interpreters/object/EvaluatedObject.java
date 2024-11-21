package com.ricdip.interpreters.object;

/**
 * Interface implemented by each evaluated object.
 */
public interface EvaluatedObject {
    /**
     * Returns an instance of {@link ObjectType} that identifies the object type.
     *
     * @return An instance of {@link ObjectType}.
     */
    ObjectType type();
}
