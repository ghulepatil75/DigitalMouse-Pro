package com.ghulepatil.digitalmouse

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice
import android.bluetooth.BluetoothHidDeviceAppSdpSettings
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.os.Build
import java.util.concurrent.Executors

class BluetoothHidController(private val context: Context) {
    private val adapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var hid: BluetoothHidDevice? = null
    private var host: BluetoothDevice? = null
    private val callbackExecutor = Executors.newSingleThreadExecutor()

    private val callback = object : BluetoothHidDevice.Callback() {
        override fun onAppStatusChanged(pluggedDevice: BluetoothDevice?, registered: Boolean) {
            if (!registered) host = null
        }

        override fun onConnectionStateChanged(device: BluetoothDevice, state: Int) {
            if (state == BluetoothProfile.STATE_CONNECTED) host = device
            if (state == BluetoothProfile.STATE_DISCONNECTED && host == device) host = null
        }

        override fun onGetReport(device: BluetoothDevice, type: Byte, id: Byte, bufferSize: Int) {
            // No feature/input report needs to be returned for this simple controller.
        }

        override fun onInterruptData(device: BluetoothDevice, reportId: Byte, data: ByteArray) {}

        override fun onSetProtocol(device: BluetoothDevice, protocol: Byte) {}

        override fun onSetReport(device: BluetoothDevice, type: Byte, id: Byte, data: ByteArray) {}

        override fun onVirtualCableUnplug(device: BluetoothDevice) {
            if (host == device) host = null
        }
    }

    @SuppressLint("MissingPermission")
    fun start(onReady: (Boolean, String) -> Unit) {
        if (Build.VERSION.SDK_INT < 28) {
            onReady(false, "Bluetooth HID requires Android 9+")
            return
        }
        if (adapter == null) {
            onReady(false, "Bluetooth is not available")
            return
        }
        try {
            adapter.getProfileProxy(context, object : BluetoothProfile.ServiceListener {
                override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                    hid = proxy as? BluetoothHidDevice
                    if (hid == null) {
                        onReady(false, "Bluetooth HID profile unavailable")
                        return
                    }
                    val sdp = BluetoothHidDeviceAppSdpSettings(
                        "Digital Mouse Pro",
                        "Wireless Mouse and Keyboard",
                        "Digital Mouse Pro",
                        BluetoothHidDevice.SUBCLASS1_COMBO,
                        REPORT_DESCRIPTOR
                    )
                    val registered = hid?.registerApp(
                        sdp, null, null, callbackExecutor, callback
                    ) ?: false
                    onReady(registered, if (registered) "Bluetooth HID ready" else "HID registration failed")
                }

                override fun onServiceDisconnected(profile: Int) {
                    hid = null
                    host = null
                }
            }, BluetoothProfile.HID_DEVICE)
        } catch (e: SecurityException) {
            onReady(false, "Bluetooth permission required")
        }
    }

    @SuppressLint("MissingPermission")
    fun pairedDevices(): List<BluetoothDevice> {
        if (adapter == null) return emptyList()
        return try { adapter.bondedDevices?.toList() ?: emptyList() } catch (_: SecurityException) { emptyList() }
    }

    @SuppressLint("MissingPermission")
    fun connect(device: BluetoothDevice): Boolean {
        return try { hid?.connect(device) ?: false } catch (_: SecurityException) { false }
    }

    @SuppressLint("MissingPermission")
    fun mouse(dx: Int, dy: Int, wheel: Int = 0, buttons: Int = 0) {
        val h = hid ?: return
        val d = byteArrayOf(
            buttons.toByte(),
            dx.coerceIn(-127, 127).toByte(),
            dy.coerceIn(-127, 127).toByte(),
            wheel.coerceIn(-127, 127).toByte()
        )
        try { h.sendReport(host, 1, d) } catch (_: SecurityException) {}
    }

    fun click(button: Int) {
        mouse(0, 0, 0, button)
        mouse(0, 0, 0, 0)
    }

    fun scroll(amount: Int) {
        mouse(0, 0, amount, 0)
    }

    @SuppressLint("MissingPermission")
    fun key(c: Char) {
        val key = keyCode(c) ?: return
        val h = hid ?: return
        val down = byteArrayOf(0, 0, key.toByte(), 0, 0, 0, 0, 0)
        val up = ByteArray(8)
        try {
            h.sendReport(host, 2, down)
            h.sendReport(host, 2, up)
        } catch (_: SecurityException) {}
    }

    private fun keyCode(c: Char): Int? = when (c.lowercaseChar()) {
        in 'a'..'z' -> 4 + (c.lowercaseChar() - 'a')
        in '1'..'9' -> 30 + (c - '1')
        '0' -> 39
        ' ' -> 44
        '\n' -> 40
        '\t' -> 43
        '-' -> 45
        '=' -> 46
        '[' -> 47
        ']' -> 48
        '\\' -> 49
        ';' -> 51
        '\'' -> 52
        ',' -> 54
        '.' -> 55
        '/' -> 56
        else -> null
    }

    companion object {
        // Report ID 1 = mouse, Report ID 2 = keyboard.
        val REPORT_DESCRIPTOR = byteArrayOf(
            0x05, 0x01, 0x09, 0x02, 0xA1.toByte(), 0x01,
            0x85.toByte(), 0x01, 0x09, 0x01, 0xA1.toByte(), 0x00,
            0x05, 0x09, 0x19, 0x01, 0x29, 0x03, 0x15, 0x00, 0x25, 0x01,
            0x95, 0x03, 0x75, 0x01, 0x81.toByte(), 0x02,
            0x95, 0x01, 0x75, 0x05, 0x81.toByte(), 0x01,
            0x05, 0x01, 0x09, 0x30, 0x09, 0x31,
            0x09, 0x38, 0x15, 0x81.toByte(), 0x25, 0x7F,
            0x75, 0x08, 0x95, 0x03, 0x81.toByte(), 0x06,
            0xC0.toByte(), 0xC0.toByte(),
            0x05, 0x01, 0x09, 0x06, 0xA1.toByte(), 0x01,
            0x85.toByte(), 0x02,
            0x05, 0x07, 0x19, 0xE0.toByte(), 0x29, 0xE7.toByte(),
            0x15, 0x00, 0x25, 0x01, 0x75, 0x01, 0x95, 0x08,
            0x81.toByte(), 0x02,
            0x95, 0x01, 0x75, 0x08, 0x81.toByte(), 0x01,
            0x95, 0x06, 0x75, 0x08, 0x15, 0x00, 0x25, 0x65,
            0x05, 0x07, 0x19, 0x00, 0x29, 0x65, 0x81.toByte(), 0x00,
            0xC0.toByte()
        )
    }
}
