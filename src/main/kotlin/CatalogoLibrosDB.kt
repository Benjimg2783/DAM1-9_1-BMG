import un6.eje6_5.serializeToMap
import java.sql.DriverManager
data class Libro(val id:String,val author:String,val title:String,val genre:String,val price:String,val publishDate:String,val description:String)
class CatalogoLibrosDB(val idLibro: String) : CatalogoLibrosInterface {
    val connection = DriverManager
        .getConnection("jdbc:oracle:thin:@localhost:1521:XE", "programacion", "programacion")

    override fun existeLibro(idLibro: String): Boolean {
        val ids = mutableListOf<String>()
        val query = connection.prepareStatement("select id from catalog ")
        val result = query.executeQuery()
        while (result.next()) {
            ids.add(result.getString("id"))
        }
        return idLibro in ids
    }

    override fun infoLibro(idLibro: String): Map<String, Any> {
        var info = mutableMapOf<String, Libro>()
        var libro:Libro
        val query = connection.prepareStatement("select * from catalog where id= '$idLibro'")
        val result = query.executeQuery()
        while (result.next()) {
            val id = result.getString("ID")
            val author = result.getString("AUTHOR")
            val title = result.getString("TITLE")
            val genre = result.getString("GENRE")
            val price = result.getInt("PRICE").toString()
            val publishDate = result.getDate("PUBLISH_DATE").toString()
            val description = result.getString("DESCRIPTION")
            info[idLibro]=Libro(id,author,title,genre,price,publishDate,description)
        }
        return info
    }
}


fun main() {
    val catalogo = CatalogoLibrosDB("bk101")
    println("Introduce un id:")
    catalogo.existeLibro(readln())
    println("Introduce un id:")
    println(catalogo.infoLibro(readln()))
}