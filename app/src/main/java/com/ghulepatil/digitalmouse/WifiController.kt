package com.ghulepatil.digitalmouse

import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Executors

class WifiController {
    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private val executor = Executors.newSingleThreadExecutor()

    fun connect(host: String, result: (Boolean, String) -> Unit) {
        close()
        executor.execute {
            try {
                val s = Socket()
                s.connect(InetSocketAddress(host, 8765), 2500)
                socket = s
                writer = PrintWriter(s.getOutputStream(), true)
                result(true, "Connected to $host")
            } catch (e: Exception) {
                result(false, "Wi-Fi connection failed: ${e.message}")
            }
        }
    }

    fun send(command: String) {
        executor.execute {
            try { writer?.println(command) } catch (_: Exception) {}
        }
    }

    fun close() {
        try { socket?.close() } catch (_: Exception) {}
        socket = null
        writer = null
    }
}
