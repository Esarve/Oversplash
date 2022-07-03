package com.sourav.oversplash.data.photo

import java.io.Serializable

data class Location(
    val city: String,
    val country: String,
    val name: String,
    val position: Position
):Serializable