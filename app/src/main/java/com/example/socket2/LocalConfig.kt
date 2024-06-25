package com.example.socket2

import com.example.socket2.socket.UserDataModel


object LocalConfig {

    fun getInfoData() : ArrayList<UserDataModel> {
        return arrayListOf(
            UserDataModel(
                name = "Fahad",
                salary = "30",
                age = "20",
                designation = "Developer"
            ),
            UserDataModel(
                name = "Arhum",
                salary = "30",
                age = "20",
                designation = "Developer"
            ),
            UserDataModel(
                name = "Atuf",
                salary = "30",
                age = "20",
                designation = "Developer"
            ),
            UserDataModel(
                name = "Naved",
                salary = "30",
                age = "20",
                designation = "Developer"
            ),
        )
    }

}