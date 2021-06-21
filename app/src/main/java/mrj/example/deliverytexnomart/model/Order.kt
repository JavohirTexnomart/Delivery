package mrj.example.deliverytexnomart.model

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by JavohirAI
 */

class Order() : Parcelable {
    var number = ""
    var date = ""
    var dateInNumber = ""
    var address = ""
    var contactPerson = ""
    var phoneNumber = ""
    var numberRouteSheet = ""
    var dateRouteSheet = ""

    constructor(
        number: String?,
        date: String?,
        dateInNumber: String?,
        address: String?,
        contactPerson: String?,
        phoneNumber: String?,
        numberRouteSheet: String?,
        dateRouteSheet: String?
    ) : this() {
        this.number = number!!
        this.date = date!!
        this.dateInNumber = dateInNumber!!
        this.address = address!!
        this.contactPerson = contactPerson!!
        this.phoneNumber = phoneNumber!!
        this.numberRouteSheet = numberRouteSheet!!
        this.dateRouteSheet = dateRouteSheet!!
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(number)
        parcel.writeString(date)
        parcel.writeString(dateInNumber)
        parcel.writeString(address)
        parcel.writeString(contactPerson)
        parcel.writeString(phoneNumber)
        parcel.writeString(numberRouteSheet)
        parcel.writeString(dateRouteSheet)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}

class OrdersResponse() {
    var message = ""
    var message_code = ""
    var result = mutableListOf<Order>()

    constructor(message: String?, message_code: String?, result: List<Order>?) : this() {
        this.message = message!!
        this.message_code = message_code!!
        if (result != null) {
            this.result.addAll(result)
        }
    }
}