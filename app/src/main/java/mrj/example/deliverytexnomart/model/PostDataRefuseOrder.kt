package mrj.example.deliverytexnomart.model

data class PostDataRefuseOrder(
    var all: Boolean,
    var goods: Array<String>,
    var number: String?,
    var date: String?,
    var numberRouteSheet: String?,
    var dateRouteSheet: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostDataRefuseOrder

        if (all != other.all) return false
        if (!goods.contentEquals(other.goods)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = all.hashCode()
        result = 31 * result + goods.contentHashCode()
        return result
    }

}
