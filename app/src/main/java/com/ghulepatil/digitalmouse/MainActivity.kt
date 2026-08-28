package com.ghulepatil.digitalmouse

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), TouchpadView.Listener {
    private lateinit var status: TextView
    private lateinit var ip: EditText
    private lateinit var wifiPanel: LinearLayout
    private lateinit var btPanel: LinearLayout
    private lateinit var keyboard: EditText
    private lateinit var btDevices: Spinner

    private val wifi = WifiController()
    private lateinit var bluetooth: BluetoothHidController
    private var btList = emptyList<BluetoothDevice>()
    private var bluetoothMode = false

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_main)

        status = findViewById(R.id.status)
        ip = findViewById(R.id.ip)
        wifiPanel = findViewById(R.id.wifiPanel)
        btPanel = findViewById(R.id.btPanel)
        keyboard = findViewById(R.id.keyboard)
        btDevices = findViewById(R.id.btDevices)

        bluetooth = BluetoothHidController(this)
        findViewById<TouchpadView>(R.id.touchpad).listener = this

        findViewById<Button>(R.id.wifiMode).setOnClickListener {
            bluetoothMode = false
            wifiPanel.visibility = View.VISIBLE
            btPanel.visibility = View.GONE
            status.text = "Wi-Fi mode"
        }

        findViewById<Button>(R.id.btMode).setOnClickListener {
            bluetoothMode = true
            wifiPanel.visibility = View.GONE
            btPanel.visibility = View.VISIBLE
            requestBluetoothPermissions()
            bluetooth.start { _, message -> runOnUiThread { status.text = message; refreshBluetoothDevices() } }
        }

        findViewById<Button>(R.id.connect).setOnClickListener {
            val host = ip.text.toString().trim()
            if (host.isEmpty()) { status.text = "Enter PC IPv4 address"; return@setOnClickListener }
            wifi.connect(host) { _, message -> runOnUiThread { status.text = message } }
        }

        findViewById<Button>(R.id.btRefresh).setOnClickListener { refreshBluetoothDevices() }

        findViewById<Button>(R.id.btConnect).setOnClickListener {
            val pos = btDevices.selectedItemPosition
            if (pos in btList.indices) {
                val ok = bluetooth.connect(btList[pos])
                status.text = if (ok) "Bluetooth connection requested" else "Bluetooth connection failed"
            }
        }

        findViewById<Button>(R.id.left).setOnClickListener { click(1) }
        findViewById<Button>(R.id.middle).setOnClickListener { click(4) }
        findViewById<Button>(R.id.right).setOnClickListener { click(2) }

        keyboard.addTextChangedListener(object : android.text.TextWatcher {
            private var busy = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                if (busy || s == null) return
                val value = s.toString()
                if (value.isNotEmpty()) {
                    value.forEach { sendKey(it) }
                    busy = true
                    keyboard.setText("")
                    busy = false
                }
            }
        })
    }

    private fun requestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= 31 &&
            checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_ADVERTISE
                ), 100
            )
        }
    }

    private fun refreshBluetoothDevices() {
        btList = bluetooth.pairedDevices()
        val names = btList.map { device ->
            try { device.name ?: device.address } catch (_: SecurityException) { "Paired device" }
        }
        btDevices.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, names)
        if (names.isEmpty()) status.text = "No paired Bluetooth devices. Pair Windows first."
    }

    private fun click(button: Int) {
        if (bluetoothMode) bluetooth.click(button) else wifi.send("CLICK $button")
    }

    private fun sendKey(c: Char) {
        if (bluetoothMode) bluetooth.key(c) else wifi.send("KEY ${c.code}")
    }

    override fun move(dx: Int, dy: Int) {
        if (bluetoothMode) bluetooth.mouse(dx, dy) else wifi.send("MOVE $dx $dy")
    }

    override fun scroll(amount: Int) {
        if (amount == 0) return
        if (bluetoothMode) bluetooth.scroll(amount) else wifi.send("SCROLL $amount")
    }

    override fun onDestroy() {
        wifi.close()
        super.onDestroy()
    }
}
