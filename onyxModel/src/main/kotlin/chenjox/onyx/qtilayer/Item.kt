package chenjox.onyx.qtilayer

import chenjox.onyx.qtilayer.expression.QTIExpression
import chenjox.onyx.xmlLayer.AssessmentItem
import chenjox.onyx.xmlLayer.ItemResource
import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import chenjox.onyx.xmlLayer.assessmentItem.ImgRef
import chenjox.onyx.xmlLayer.assessmentTest.AssessmentItemRef

public class Item(
    private val assessmentItem: AssessmentItem, // Die Aufgabe selbst
    private val assessmentItemRef: AssessmentItemRef, // Ref in der Section
    private val itemResource: ItemResource // Ref im Imsmanifest.
) {

    init {
        check(assessmentItem.identifier == assessmentItemRef.identifier)
        check(itemResource.identifier == assessmentItemRef.identifier)
        check(itemResource.identifier == assessmentItem.identifier)

        check(itemResource.associatedFileName == assessmentItemRef.associatedFileName)
    }

    public var title: String
        get() {
            return assessmentItem.title
        }
        set(value) {
            assessmentItem.title = value
        }

    public var identifier: String
        get() {
            return assessmentItem.identifier
        }
        set(value) {
            assessmentItem.identifier = value
            assessmentItemRef.identifier = value
            itemResource.identifier = value
        }

    public val associatedFileName: String
        get() {
            return assessmentItemRef.associatedFileName
        }

    /**
     * Sets the Expression for the given Template
     */
    public fun setExpressionFor(templateIdentifier: String, newExpression: QTIExpression){
        val (tagName, builder) = newExpression.buildNode()
        assessmentItem.setTemplateNode(templateIdentifier, tagName, builder)
    }

    public fun setBaseTypeFor(templateIdentifier: String, newBaseType: BaseType){
        assessmentItem.setBaseType(templateIdentifier, newBaseType)
    }

    public fun getBaseTypeFor(templateIdentifier: String): BaseType{
        return assessmentItem.getBaseType(templateIdentifier)
    }

    public fun setImgReference(newRef: String, filter: (ImgRef) -> Boolean){
        assessmentItem.itemBody.images.filter(filter).forEach {
            it.source = newRef
        }
    }

    public fun hasTemplateIdentifier(templateIdentifier: String): Boolean{
        return assessmentItem.templates.find { it.identifier == templateIdentifier } != null
    }

    public fun templates() : String{
        return assessmentItem.templates.joinToString { it.identifier }
    }

    public fun setTemplateName(templateIdentifier: String, newTemplateIdentifier: String){
        assessmentItem.templates.firstOrNull { it.identifier == templateIdentifier }?.apply {
            identifier = newTemplateIdentifier
        }
    }

    public fun insertTemplateBefore(templateIdentifier: String, newTemplate: String, baseType: BaseType, newExpression: QTIExpression, cardinality: String = "single"){
        val (tagName, builder) = newExpression.buildNode()
        assessmentItem.addTemplateNodeBefore(
            templateIdentifier,
            newTemplate,
            baseType,
            cardinality,
            tagName,
            builder
        )
    }

    public fun insertTemplateAfter(templateIdentifier: String, newTemplate: String, baseType: BaseType, newExpression: QTIExpression, cardinality: String = "single"){
        val (tagName, builder) = newExpression.buildNode()
        assessmentItem.addTemplateNodeAfter(
            templateIdentifier,
            newTemplate,
            baseType,
            cardinality,
            tagName,
            builder
        )
    }

    public fun insertTemplateAtBeginning(newTemplate: String, baseType: BaseType, newExpression: QTIExpression, cardinality: String = "single"){
        val (tagName, builder) = newExpression.buildNode()
        assessmentItem.addTemplateNodeBefore(
            assessmentItem.templates.first().identifier,
            newTemplate,
            baseType,
            cardinality,
            tagName,
            builder
        )
    }

    internal fun copyOfAssessmentItem() : AssessmentItem {
        return assessmentItem.deepClone()
    }

}