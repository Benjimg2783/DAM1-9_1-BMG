import un6.eje6_5.CatalogoLibrosXML

fun main() {
    val interfaz1= gestionLibrosIU1()
    val portatil = CatalogoLibrosXML("C:\\Users\\Benjamin\\IdeaProjects\\DAM1-6_5-BMG\\src\\main\\kotlin\\catalog.xml")
    //var casa = "/home/usuario/Documentos/workspace/IdeaProjects/IESRA-DAM/ejercicios/src/main/kotlin/un5/eje5_4/Catalog.xml"
    val gestorDeLibros = catalogoLibros(portatil, interfaz1)
    gestorDeLibros.preguntarPorUnLibro()
    gestorDeLibros.mostrarInfoDeUnLibro()

}