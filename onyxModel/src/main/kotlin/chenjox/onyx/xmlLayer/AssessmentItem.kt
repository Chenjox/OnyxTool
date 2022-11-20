package chenjox.onyx.xmlLayer

import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import chenjox.onyx.xmlLayer.assessmentItem.ItemBody
import chenjox.onyx.xmlLayer.assessmentItem.Template
import dsl.ElementBuilder
import dsl.deepCopy
import dsl.inspect
import org.w3c.dom.Document

/**
 * Eine Klasse die ein AssessmentItem darstellt.
 */
public class AssessmentItem(
    public val document: Document,
    templates: List<Template>,
    public val itemBody: ItemBody
    ) {

    private var _templates : MutableList<Template> = templates.toMutableList()
    public val templates : List<Template> = _templates

    public var identifier: String
        get() {
            var s : String? = null
            document.inspect {
                inspectRoot {
                    s = findAttr("identifier")
                }
            }
            return s ?: throw IllegalStateException("No identifier in AssessmentItem!")
        }
        set(value) {
            document.inspect {
                inspectRoot {
                    set("identifier",value)
                }
            }
        }

    public var title: String
        get() {
            var s : String? = null
            document.inspect {
                inspectRoot {
                    s = findAttr("title")
                }
            }
            return s ?: throw IllegalStateException("No title found!")
        }
        set(value) {
            document.inspect {
                inspectRoot {
                    set("title",value)
                }
            }
        }

    ////// Template Manipulation

    public fun setTemplateNode(templateIdentifier: String, newTagName: String, builder: ElementBuilder.() -> Unit){
        val template = _templates.firstOrNull {
            it.identifier == templateIdentifier
        }
        if(template != null){
            template.setExpression(newTagName, builder)
        }else{
            throw IllegalArgumentException("No template '$templateIdentifier' found!")
        }
    }

    public fun setBaseType(templateIdentifier: String, newBaseType: BaseType){
        val template = _templates.firstOrNull {
            it.identifier == templateIdentifier
        }
        if(template != null){
            template.baseType = newBaseType
        }else{
            throw IllegalArgumentException("No template '$templateIdentifier' found!")
        }
    }

    public fun getBaseType(templateIdentifier: String): BaseType{
        val template = _templates.firstOrNull {
            it.identifier == templateIdentifier
        }
        if(template != null){
            return template.baseType
        }else{
            throw IllegalArgumentException("No template '$templateIdentifier' found!")
        }
    }

    public fun normalizeIdentifiers(){
        identifier = "${identifier.replace('_','-')}-norm"
    }

    public fun addTemplateNodeAfter(
        afterIdentifier: String,
        templateIdentifier: String,
        baseType: BaseType,
        cardinality: String,
        expressionTagName: String,
        expressionBuilder: ElementBuilder.() -> Unit
    ){
        val afterNode = _templates.firstOrNull {
            it.identifier == afterIdentifier
        }
        if(afterNode!=null){
            val t = afterNode.insertAfter(templateIdentifier, baseType, cardinality, expressionTagName, expressionBuilder)
            val index = _templates.indexOf(afterNode)
            _templates.add(index+1, t)
        }
    }

    public fun addTemplateNodeBefore(
        beforeIdentifier: String,
        templateIdentifier: String,
        baseType: BaseType,
        cardinality: String,
        expressionTagName: String,
        expressionBuilder: ElementBuilder.() -> Unit
    ){
        val afterNode = _templates.firstOrNull {
            it.identifier == beforeIdentifier
        }
        if(afterNode!=null){
            val t = afterNode.insertBefore(templateIdentifier, baseType, cardinality, expressionTagName, expressionBuilder)
            val index = _templates.indexOf(afterNode)
            _templates.add(index, t)
        }
    }

    public fun deleteTemplateNode(identifier: String){

    }

    public fun deepClone() : AssessmentItem {
        return AssessmentItemBuilder(document.deepCopy()).build()
    }
    //TODO Variablen Insertion und so
    //TODO Custom Operator
    //TODO HTML Scanner


}