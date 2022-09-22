package com.example.assignment

import java.time.LocalDate
import java.util.*

data class User(
    val PhoneNumber:String,
    val Password:String,
    val Name:String = "",
    val IcNumber:String = "",
    val Gender:String = "",
    val Address:String="",
    val Birthday:String ="",
    val Status:String = "Not Approved",
    val RejectedReason:String = "",
    val ApprovedTime:String=""
)
