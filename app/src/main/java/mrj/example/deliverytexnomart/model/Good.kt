package mrj.example.deliverytexnomart.model


/**
 * Created by JavohirAI
 */

class Good() {
    var name = ""
    var amount = 0
    var price = 0.0
    var sum = 0.0
}

class GoodsResponse() {
    var message = ""
    var message_code = ""
    var result = mutableListOf<Good>()

    constructor(message: String?, message_code: String?, result: List<Good>?) : this() {
        this.message = message!!
        this.message_code = message_code!!
        if (result != null) {
            this.result.addAll(result)
        }
    }
}