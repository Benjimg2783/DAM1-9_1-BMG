package un6.eje6_5

import CatalogoLibrosInterface
import mu.KotlinLogging
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.time.LocalDate
import javax.xml.parsers.DocumentBuilderFactory

class CatalogoLibrosXML(private val cargador: String): CatalogoLibrosInterface {

    companion object {
        val l = KotlinLogging.logger("LOG")
    }
    internal fun i(msg:String)
    {
        CatalogoLibrosXML.l.info { msg }
    }
    private var xmlDoc: Document? = null
    init {
        try {
            xmlDoc = readXml(cargador)
            xmlDoc?.let { it.documentElement.normalize() }
        } catch (e: Exception) {
            requireNotNull(xmlDoc) { e.message.toString() }
        }
    }

    private fun readXml(pathName: String): Document {
        val xmlFile = File(pathName)
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile)
    }

    private fun obtenerListaNodosPorNombre(doc: Document, tagName: String): MutableList<Node> {
        val bookList: NodeList = doc.getElementsByTagName(tagName)
        val lista = mutableListOf<Node>()
        for (i in 0 until bookList.length)
            lista.add(bookList.item(i))
        return lista
    }

    private fun obtenerAtributosEnMapKV(e: Element): MutableMap<String, String> {
        val mMap = mutableMapOf<String, String>()
        for (j in 0 until e.attributes.length)
            mMap.putIfAbsent(e.attributes.item(j).nodeName, e.attributes.item(j).nodeValue)
        return mMap
    }

    /*
    *
    * @return  Devuelve true si existe, `false` en caso contrario.
    * */
    override fun existeLibro(idLibro: String): Boolean {
        val existe: Boolean
        existe = if (idLibro.isBlank())
            false
        else {
            val encontrado = xmlDoc?.let {
                val nodosLibro = obtenerListaNodosPorNombre(it, "book")
                ((nodosLibro.indexOfFirst {
                    if (it.getNodeType() === Node.ELEMENT_NODE) {
                        val elem = it as Element
                        obtenerAtributosEnMapKV(elem)["id"] == idLibro
                    } else
                        false
                }) >= 0)
            }
            (encontrado != null && encontrado)
        }
        return existe
    }

    /*
      *
      * @return  Devuelve true si existe, `false` en caso contrario.
      * */
    override fun infoLibro(idLibro: String): Map<String, Any> {
        val m = mutableMapOf<String, Any>()
        if (!idLibro.isBlank())
            xmlDoc?.let {
                val nodosLibro = obtenerListaNodosPorNombre(it, "book")

                val posicionDelLibro = nodosLibro.indexOfFirst {
                    if (it.getNodeType() === Node.ELEMENT_NODE) {
                        val elem = it as Element
                        obtenerAtributosEnMapKV(elem)["id"] == idLibro
                    } else false
                }
                if (posicionDelLibro >= 0) {
                    if (nodosLibro[posicionDelLibro].getNodeType() === Node.ELEMENT_NODE) {
                        val elem = nodosLibro[posicionDelLibro] as Element
                        m.put("id", idLibro)
                        m.put("author", elem.getElementsByTagName("author").item(0).textContent)
                        m.put("genre", elem.getElementsByTagName("genre").item(0).textContent)
                        m.put("price", elem.getElementsByTagName("price").item(0).textContent.toDouble())
                        m.put(
                            "publish_date",
                            LocalDate.parse(elem.getElementsByTagName("publish_date").item(0).textContent)
                        )
                        m.put("description", elem.getElementsByTagName("description").item(0).textContent)
                    }
                }
            }
        return m
    }

}

fun main() {
    val portatil = "/home/edu/IdeaProjects/IESRA-DAM-Prog/ejercicios/src/main/kotlin/un5/eje5_4/Catalog.xml"
    var casa =
        "/home/usuario/Documentos/workspace/IdeaProjects/IESRA-DAM/ejercicios/src/main/kotlin/un5/eje5_4/Catalog.xml"
    val cat = CatalogoLibrosXML(portatil)
    val id = "bk105"
    cat.i(cat.existeLibro(id).toString())
    cat.i(cat.infoLibro(id).toString())
}