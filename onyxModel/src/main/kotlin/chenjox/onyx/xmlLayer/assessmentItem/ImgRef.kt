package chenjox.onyx.xmlLayer.assessmentItem

import dsl.inspect
import org.w3c.dom.Element

/**
 * <img alt="" height="300" src="media%2Fimage009.png" width="550"/>
 */
public class ImgRef(public val element: Element) {

    public val srcRaw: String
        get() {
            var b: String? = null
            element.inspect {
                b = findAttr("src")
            }
            return b ?: throw IllegalStateException("No src attribute found!")
        }

    public var source: String
        get() {
            var b: String? = null
            element.inspect {
                b = findAttr("src")?.replace("%2F", "/")
            }
            return b ?: throw IllegalStateException("No src attribute found!")
        }
        set(value) {
            element.inspect {
                set("src", value.replace("/","%2F"))
            }
        }

    public var alt: String
        get() {
            var b: String? = null
            element.inspect {
                b = findAttr("alt")
            }
            return b ?: ""
        }
        set(value) {
            element.inspect {
                set("alt", value)
            }
        }

    public val width: Int
        get() {
            var b: Int = -1
            element.inspect {
                b = findAttr("width")?.toIntOrNull() ?: -1
            }
            return b
        }

    public val height: Int
        get() {
            var b: Int = -1
            element.inspect {
                b = findAttr("height")?.toIntOrNull() ?: -1
            }
            return b
        }

}