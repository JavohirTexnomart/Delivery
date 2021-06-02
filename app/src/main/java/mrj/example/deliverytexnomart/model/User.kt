package mrj.example.deliverytexnomart.model

class User() {
    constructor(login: String?, password: String?, code_client: String?) : this() {
        this.login = login!!
        this.password = password!!
        this.code_client = code_client!!
    }

    var login = ""
    var password = ""
    var code_client = ""
}

class UserResonse() {
    var message = ""
    var message_code = ""
    var result = User()

    constructor(message: String?, message_code: String?, result: User?) : this() {
        this.message = message!!
        this.message_code = message_code!!
        this.result = result!!
    }
}