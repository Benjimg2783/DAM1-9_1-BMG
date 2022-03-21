import un6.eje6_5.CatalogoLibrosXML
import java.util.logging.Level
import java.util.logging.LogManager
import javax.swing.JOptionPane

internal val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }
internal fun i(tag: String, msg: String) {
    l.info("[$tag] - $msg")
}

interface CatalogoLibrosInterface{
    fun existeLibro(idLibro:String):Boolean
    fun infoLibro(idLibro: String): Map<String, Any>
}
interface gestionLibrosIU{
    fun introducirId():String
    fun existeLibro(boolean: Boolean):String
}

class gestionLibrosIU1 : gestionLibrosIU {
    override fun introducirId(): String {
        return JOptionPane.showInputDialog("Introduzca el ID:")
    }

    override fun existeLibro(boolean: Boolean): String {
        return if (boolean) "Este libro existe!"
        else "Este libro no existe!!"
    }
}
class gestionLibrosIU2 : gestionLibrosIU {
    override fun introducirId(): String {
        println("Introduzca el ID:")
        return readln()
    }

    override fun existeLibro(boolean: Boolean): String {
        return if (boolean) "Este libro existe!"
        else "Este libro no existe!!"
    }
}
open class catalogoLibros(archivo: CatalogoLibrosInterface, idLibro: gestionLibrosIU) {
    var cat = archivo
    val id = idLibro
    fun preguntarPorUnLibro() {
        val idLibro= id.introducirId()
        if (cat.existeLibro(idLibro))
            println( id.existeLibro(cat.existeLibro(idLibro)))

        else
            println( id.existeLibro(cat.existeLibro(idLibro)))
    }

    fun mostrarInfoDeUnLibro() {
        val idLibro = id.introducirId()
        val infoLibro = cat.infoLibro(idLibro)
        if (!infoLibro.isEmpty())
            println( infoLibro)
        else
            println("No se encontró información sobre el libro")
    }

}