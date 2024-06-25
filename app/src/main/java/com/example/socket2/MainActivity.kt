package com.example.socket2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.socket2.databinding.ActivityMainBinding
import com.example.socket2.interfaces.CommonInterfaceClickEvent
import com.example.socket2.socket.InfoListAdapter
import com.example.socket2.socket.UserDataModel
import com.example.socket2.socket.UserResponseModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket


class MainActivity : AppCompatActivity() {
    private lateinit var serverSocket: ServerSocket
    private lateinit var binding: ActivityMainBinding
    private lateinit var edtWriter: PrintWriter
    private lateinit var clientSocket: Socket
    private var adapter = InfoListAdapter()
    private var arrListInfoData = ArrayList<UserDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initializeSocket()
        initializeFields()


    }

    private fun initializeSocket(){
        Thread {
            try {
                val serverPort = 8085 // Choose any free port number
                serverSocket = ServerSocket(serverPort)

                while (true) {
                    clientSocket = serverSocket.accept()
                    handleClientSocket(clientSocket)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()



        binding.btnSend.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val message = binding.edtMessage.text.toString()
                val outputStream = clientSocket.getOutputStream()
                edtWriter = PrintWriter(outputStream, true)
                edtWriter.println(message) // Send the message to the server
                println("Send: $message")
            }
        }
    }

    private fun initializeFields() {
        binding.rvInfo.adapter = adapter
    }

    private fun handleClientSocket(clientSocket: Socket) {
        try {
            // Example of reading data from the client
            val input = clientSocket.getInputStream()
            val reader = input.bufferedReader()
            var line = reader.readLine()

            while (line != null) {
                // Process the received message
                println("Received: $line")

                val jsonArray = JSONArray(line)
                val arrInfoList = ArrayList<UserDataModel>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val name = jsonObject.getString("name")
                    val age = jsonObject.getString("age")
                    val salary = jsonObject.getString("salary")
                    val designation = jsonObject.getString("designation")
                    arrInfoList.add(UserDataModel(name, age, designation = designation,salary = salary))
                }

                /*val jsonObject = JSONObject(line)
                // Now you can work with jsonObject
                // Example: accessing values
                val value1 = jsonObject.getString("name")
                val value2 = jsonObject.getString("age")
                val value3 = jsonObject.getString("designation")
                val value4 = jsonObject.getString("salary")

                val model = UserDataModel(
                    name = value1,
                    age = value2,
                    designation = value3,
                    salary = value4
                )

                runOnUiThread {
                  //  setData()
                    *//*binding.txtName.text = "Name: ${model.name}"
                    binding.txtAge.text = "Age: ${model.age}"
                    binding.txtDesignation.text = "Designation: ${model.designation}"
                    binding.txtSalary.text = "Salary: ${model.salary}"*//*
                }*/

                runOnUiThread {
                    setData(arrInfoList)
                }

                // Example of writing data back to the client
                val output = clientSocket.getOutputStream()
                val writer = output.bufferedWriter()
                writer.write("Server received: $line\n")

                writer.flush()


                // Read the next line
                line = reader.readLine()

            }



            // Optionally, you can handle client disconnection here
            println("Client disconnected")

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            clientSocket.close() // Close the client socket when done
        }
    }

    private fun setData(data : ArrayList<UserDataModel>) {
        arrListInfoData.clear()

        arrListInfoData.addAll(data)
        adapter.setData(arrListInfoData)

        adapter.onClickEvent = object : CommonInterfaceClickEvent {
            override fun onItemClick(type: String, position: Int) {
                if (type == "itemClicked"){

                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        if (!serverSocket.isClosed) {
            try {
                serverSocket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}