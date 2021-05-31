package mrj.example.deliverytexnomart.model

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by JavohirAI
 */

data class Order(var number: String?, var date: String?, var address: String?,var contactPerson:String?, var phoneNumber:String?, var phoneNumberInFormat:String?):Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(address)
        parcel.writeString(contactPerson)
        parcel.writeString(phoneNumber)
        parcel.writeString(phoneNumberInFormat)
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