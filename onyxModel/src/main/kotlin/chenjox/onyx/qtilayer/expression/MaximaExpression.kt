package chenjox.onyx.qtilayer.expression

import dsl.ElementBuilder
import mu.KotlinLogging
import java.lang.Integer.max

private val logger = KotlinLogging.logger {}

private const val MAXIMA_TAGNAME = "customOperator"

public class MaximaExpression(public val formula: String) : QTIExpression{

    public companion object {
        public fun getRandomExpression(step: Int, upper: Float, lower: Float, places: Int): QTIExpression {
            if(places < 0) logger.warn {
                "Illegal amount of places in PRNG expression: Step: $step, Upper Bound: $upper, Lower Bound $lower, places: $places"
            }

            val placesFinal = max(1,places)

            val num2 = "1".padEnd(placesFinal+1,'0')

            val random1to100 = "truncate( prng / ( 10^(ceiling( log( prng )/log( 10 ) - $placesFinal ) ) ) ) )/($num2)"

            return MaximaExpression("block([prng, obereGrenze, untereGrenze, komma], " +
                    "prng:{PRNG_$step}, " +
                    "obereGrenze:$upper, " +
                    "untereGrenze:$lower , " +
                    "( untereGrenze + (obereGrenze - untereGrenze)*( $random1to100 ) )") // FIXME q=signum({q_roh})*truncate(abs({q_roh})*10000+0.5)/10000
        }
        public fun getPrngStartExpression(startTemplate: String, mult: Long = 134775813, inkr: Long = 1, modul: Long = 4294967296): QTIExpression{
            return MaximaExpression("block([vorher, mult, inkr, modul], vorher:{$startTemplate}, mult:$mult , inkr:$inkr , modul:$modul, mod( (mult*vorher+inkr) , modul))")
        }
        public fun getPrngExpression(step: Int, mult: Long = 134775813, inkr: Long = 1, modul: Long = 4294967296): QTIExpression{
            return MaximaExpression("block([vorher, mult, inkr, modul], vorher:{PRNG_$step}, mult:$mult , inkr:$inkr , modul:$modul, mod( (mult*vorher+inkr) , modul))")
        }
    }

    override fun buildNode(): Pair<String, ElementBuilder.() -> Unit> {
        if (formula.isBalanced()) {
            val ids = formula.extractIdentifiers()
            val qtiForm = formula.replaceIdentifiersInOrder(ids)

            return MAXIMA_TAGNAME to {
                attributes {
                    "definition" with "MAXIMA"
                    "value" with qtiForm
                }
                ids.forEach {
                    childElement("variable"){
                        attributes {
                            "identifier" with it
                        }
                    }
                }
            }
        } else throw IllegalArgumentException("Formula '$formula' is not balanced!")
    }

}
