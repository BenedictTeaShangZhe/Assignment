package com.example.assignment

import java.time.LocalDate
import java.util.*

data class User(
    val phoneNumber:String= "",
    val password:String= "",
    val name:String = "",
    val icNumber:String = "",
    val gender:String = "",
    val address:String="",
    val birthday:String ="",
    val status:String = "Not Approved",
    val rejectedReason:String = "",
    val approvedTime:String=""
)
