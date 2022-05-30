package chenjox.onyx.qtilayer.expression

import dsl.ElementBuilder

public interface QTIExpression {

    /**
     * @return A pair of a ElementBuilder and the nodes tagName
     */
    public fun buildNode() : Pair<String, ElementBuilder.() -> Unit>

    /**
     *
     */

}