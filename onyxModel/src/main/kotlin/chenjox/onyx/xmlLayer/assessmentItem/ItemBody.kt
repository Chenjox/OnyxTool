package chenjox.onyx.xmlLayer.assessmentItem

import dsl.inspect
import org.w3c.dom.Element

/**
 * Stellt den <itemBody> tag in einem AssessmentItem dar
 */
public class ItemBody private constructor(
    public val domElement: Element,
    public val images: List<ImgRef>,
    public val templateItemBodyRef: List<TemplateItemBodyRef>
) {

    public companion object {
        public operator fun invoke(domElement: Element): ItemBody{
            var imgList = emptyList<ImgRef>()
            var printedVariableList = emptyList<TemplateItemBodyRef>()
            domElement.inspect {
                imgList = searchChildElements("img").map { ImgRef(it) }
            }
            domElement.inspect {
                printedVariableList = searchChildElements("printedVariable").map { TemplateItemBodyRef(it) }
            }
            return ItemBody(domElement, imgList, printedVariableList)
        }
    }
    // TODO Utility Functions mit images und so...

}