package mrj.example.deliverytexnomart.model

class RouteSheet(
    var number: String,
    var date: String,
    var dateInNumber: String
) {
}

class RouteSheetsResponse() {
    var message = ""
    var message_code = ""
    var result = mutableListOf<RouteSheet>()

    constructor(message: String?, message_code: String?, result: List<RouteSheet>?) : this() {
        this.message = message!!
        this.message_code = message_code!!
        if (result != null) {
            this.result.addAll(result)
        }
    }
}