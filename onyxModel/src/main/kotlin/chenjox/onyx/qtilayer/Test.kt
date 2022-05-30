package chenjox.onyx.qtilayer

import chenjox.onyx.xmlLayer.AssessmentTest
import chenjox.onyx.xmlLayer.TestResource
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

public class Test(
    private val assessmentTest: AssessmentTest,
    private val testResource: TestResource
) {

    init {
        check(assessmentTest.identifier == testResource.identifier) {
            "${assessmentTest.identifier} =/= ${testResource.identifier}"
        }
    }

    public var identifier: String
        get() {
            return assessmentTest.identifier
        }
        set(value) {
            assessmentTest.identifier = value
            testResource.identifier = value
        }

    public var title: String
        get() {
            return assessmentTest.title
        }
        set(value) {
            assessmentTest.title = value
        }

    public val associatedFileName: String
        get() {
            return testResource.associatedFileName
        }

    public fun addImgDependency(href: String){
        testResource.addImgDependency(href)
    }

}