package crls.learn.budgetlisttoastmenunewativity

import java.util.Date

// esta class descreve o nosso budget ou o objeto que pretendemos
data class BudgetItem (
    var description : String,
    var value : Double,
    var date : Date
)