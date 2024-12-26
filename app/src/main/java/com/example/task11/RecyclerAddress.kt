package com.example.task11
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
@Parcelize
data class RecyclerAddress(var id: Int, var locationImage: Int,var place:String, var fullLocation:String, var isChecked: Boolean = false):Parcelable
