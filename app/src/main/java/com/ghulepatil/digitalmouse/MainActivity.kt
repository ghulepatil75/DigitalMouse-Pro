package com.ghulepatil.digitalmouse
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity:AppCompatActivity(),TouchpadView.Listener{
 private lateinit var status:TextView; private lateinit var ip:EditText
 private lateinit var wifiPanel:LinearLayout; private lateinit var btInfo:TextView; private lateinit var keyboard:EditText
 private val wifi=WifiController(); private lateinit var bt:BluetoothHidController
 private var bluetoothMode=false
 override fun onCreate(b:Bundle?){super.onCreate(b);setContentView(R.layout.activity_main)
  status=findViewById(R.id.status);ip=findViewById(R.id.ip);wifiPanel=findViewById(R.id.wifiPanel);btInfo=findViewById(R.id.btInfo);keyboard=findViewById(R.id.keyboard)
  findViewById<TouchpadView>(R.id.touchpad).listener=this
  bt=BluetoothHidController(this)
  findViewById<Button>(R.id.wifiMode).setOnClickListener{bluetoothMode=false;wifiPanel.visibility=android.view.View.VISIBLE;btInfo.visibility=android.view.View.GONE;status.text="Wi-Fi mode"}
  findViewById<Button>(R.id.btMode).setOnClickListener{bluetoothMode=true;wifiPanel.visibility=android.view.View.GONE;btInfo.visibility=android.view.View.VISIBLE;requestBt();bt.start{ok,msg->runOnUiThread{status.text=msg}}}
  findViewById<Button>(R.id.connect).setOnClickListener{wifi.connect(ip.text.toString().trim()){ok,msg->runOnUiThread{status.text=msg}}}
  findViewById<Button>(R.id.left).setOnClickListener{sendClick(1)}
  findViewById<Button>(R.id.middle).setOnClickListener{sendClick(4)}
  findViewById<Button>(R.id.right).setOnClickListener{sendClick(2)}
  keyboard.addTextChangedListener(object:android.text.TextWatcher{
   var busy=false
   override fun beforeTextChanged(s:CharSequence?,a:Int,c:Int,d:Int){}
   override fun onTextChanged(s:CharSequence?,a:Int,b:Int,c:Int){}
   override fun afterTextChanged(s:android.text.Editable?){if(busy||s==null)return;val t=s.toString();if(t.isNotEmpty()){t.forEach{sendKey(it)};busy=true;keyboard.setText("");busy=false}}
  })
 }
 private fun requestBt(){if(android.os.Build.VERSION.SDK_INT>=31 && checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT)!=PackageManager.PERMISSION_GRANTED)requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT,Manifest.permission.BLUETOOTH_ADVERTISE),44)}
 private fun sendClick(btn:Int){if(bluetoothMode)bt.click(btn) else wifi.send("CLICK $btn")}
 private fun sendKey(c:Char){if(bluetoothMode)bt.key(c) else wifi.send("KEY "+c.code)}
 override fun move(x:Int,y:Int){if(bluetoothMode)bt.mouse(x,y) else wifi.send("MOVE $x $y")}
 override fun scroll(v:Int){if(v!=0){if(bluetoothMode)bt.mouse(0,0);else wifi.send("SCROLL $v")}}
 override fun onDestroy(){wifi.close();super.onDestroy()}
}
