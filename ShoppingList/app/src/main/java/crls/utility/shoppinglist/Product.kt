package crls.utility.shoppinglist
/*//quando necessario um objeto e funcionaliades
class Product {
    //deve corresponder exatamente ao tipo de dados que é suposto ser
    var name : String
    var qtt : Int
    var isChecked : Boolean

    constructor(nome: String, qtt: Int, isChecked: Boolean) {
        this.nome = name
        this.qtt = qtt
        this.isChecked = isChecked
    }
}
 */
//usado quando se pretente apenas uma estrutura de dados
data class Product(var name : String, var qtt : Int, var isChecked : Boolean = false)