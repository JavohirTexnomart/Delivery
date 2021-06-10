package mrj.example.deliverytexnomart.model

class User() {
    constructor(login: String?, password: String?, code_client: String?,status:String?) : this() {
        this.login = login!!
        this.password = password!!
        this.code_client = code_client!!
        this.status = status!!
    }

    var login = ""
    var password = ""
    var code_client = ""
    var status = ""
}

class UserResponse() {
    var message = ""
    var message_code = ""
    var result = User()

    constructor(message: String?, message_code: String?, result: User?) : this() {
        this.message = message!!
        this.message_code = message_code!!
        this.result = result!!
    }
}