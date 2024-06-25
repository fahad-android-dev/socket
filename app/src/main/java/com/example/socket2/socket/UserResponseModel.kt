package com.example.socket2.socket

data class UserResponseModel(
    val data : ArrayList<UserDataModel> ?= arrayListOf()
)

data class UserDataModel(
    val name : String ?= null,
    val age : String ?= null,
    val designation : String ?= null,
    val salary : String ?= null,
)
