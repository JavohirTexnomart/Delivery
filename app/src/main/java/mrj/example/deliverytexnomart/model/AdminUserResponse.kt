package mrj.example.deliverytexnomart.model

class AdminUserResponse() {
    var message = ""
    var message_code = ""
    var result = mutableListOf<Car>()

    constructor(message: String?, message_code: String?, result: List<Car>?) : this() {
        this.message = message!!
        this.message_code = message_code!!
        if (result != null) {
            this.result.addAll(result)
        }
    }
}