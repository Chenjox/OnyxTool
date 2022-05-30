package chenjox.onyx.qtilayer.expression

fun main(){
    testExtraction()

    testBrackets()
    testReplace()
}

fun testExtraction(){
    val id = "{Hello}+{I}+{AM}-max({IDENTIFIER})+{AM}-{AM}".extractIdentifiers()
    println(id.toString())
}

fun testBrackets(){
    val malformed = "()()()()()([)][][][]{}{}{}{}"
    println(malformed.isBalanced())
    val malformed2 = "(){}}{"
    println(malformed2.isBalanced())
}

fun testReplace(){
    val formula = "{Hello}+{I}+{AM}-max({IDENTIFIER})+{AM}-{AM}"
    val ids = formula.extractIdentifiers()
    val formString = formula.replaceIdentifiersInOrder(ids)
    println(formString)
}