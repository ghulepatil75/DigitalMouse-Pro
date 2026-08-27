package com.ghulepatil.digitalmouse
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice
import android.bluetooth.BluetoothHidDeviceAppSdpSettings
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.os.Build
import java.util.concurrent.Executors

class BluetoothHidController(private val context:Context) {
 private val adapter=BluetoothAdapter.getDefaultAdapter()
 private var hid:BluetoothHidDevice?=null
 private var host:BluetoothDevice?=null
 private val ex=Executors.newSingleThreadExecutor()
 private val callback=object:BluetoothHidDevice.Callback(){
  override fun onAppStatusChanged(device:BluetoothDevice?, registered:Boolean){ }
  override fun onConnectionStateChanged(device:BluetoothDevice?, state:Int){
   if(state==BluetoothProfile.STATE_CONNECTED) host=device
   if(state==BluetoothProfile.STATE_DISCONNECTED && host==device) host=null
  }
 }
 fun start(onReady:(Boolean,String)->Unit){
  if(Build.VERSION.SDK_INT<28){onReady(false,"Bluetooth HID needs Android 9+");return}
  if(adapter==null){onReady(false,"Bluetooth unavailable");return}
  try{
   adapter.getProfileProxy(context,object:BluetoothProfile.ServiceListener{
    override fun onServiceConnected(profile:Int,proxy:BluetoothProfile){
     hid=proxy as BluetoothHidDevice
     val sdp=BluetoothHidDeviceAppSdpSettings("Digital Mouse Pro","Mouse + Keyboard","Digital Mouse",BluetoothHidDevice.SUBCLASS1_MOUSE,REPORT_DESC)
     hid?.registerApp(sdp,null,Executors.newSingleThreadExecutor(),callback)
     onReady(true,"Bluetooth HID ready — pair from Windows")
    }
    override fun onServiceDisconnected(profile:Int){hid=null;host=null}
   },BluetoothProfile.HID_DEVICE)
  }catch(e:SecurityException){onReady(false,"Bluetooth permission required")}
 }
 fun connect(device:BluetoothDevice){try{hid?.connect(device)}catch(_:Exception){}}
 fun mouse(dx:Int,dy:Int,buttons:Int=0){val h=hid?:return;val d=byteArrayOf(buttons.toByte(),dx.coerceIn(-127,127).toByte(),dy.coerceIn(-127,127).toByte(),0);try{h.sendReport(host,1,d)}catch(_:Exception){}}
 fun click(button:Int){mouse(0,0,button);mouse(0,0,0)}
 fun key(c:Char){
  val code=keyCode(c) ?: return
  val h=hid?:return
  val down=byteArrayOf(0,0,code,0,0,0,0,0)
  val up=ByteArray(8)
  try{h.sendReport(host,2,down);h.sendReport(host,2,up)}catch(_:Exception){}
 }
 private fun keyCode(c:Char):Int? = when(c.lowercaseChar()){
  in 'a'..'z'->4+(c.lowercaseChar()-'a')
  in '1'..'9'->30+(c-'1')
  '0'->39
  ' '->44
  '
'->40
  else->null
 }
 companion object{
  val REPORT_DESC=byteArrayOf(
   0x05,0x01,0x09,0x02,0xA1.toByte(),0x01,0x09,0x01,0xA1.toByte(),0x00,
   0x05,0x09,0x19,0x01,0x29,0x03,0x15,0x00,0x25,0x01,0x95,0x03,0x75,0x01,0x81.toByte(),0x02,0x95,0x01,0x75,0x05,0x81.toByte(),0x01,
   0x05,0x01,0x09,0x30,0x09,0x31,0x15,0x81.toByte(),0x25,0x7F,0x75,0x08,0x95,0x02,0x81.toByte(),0x06,0xC0.toByte(),0xC0.toByte(),
   0x05,0x01,0x09,0x06,0xA1.toByte(),0x01,0x05,0x07,0x19,0xE0.toByte(),0x29,0xE7.toByte(),0x15,0x00,0x25,0x01,0x75,0x01,0x95,0x08,0x81.toByte(),0x02,
   0x95,0x01,0x75,0x08,0x81.toByte(),0x01,0x95,0x06,0x75,0x08,0x15,0x00,0x25,0x65,0x05,0x07,0x19,0x00,0x29,0x65,0x81.toByte(),0x00,0xC0.toByte()
  )
 }
}
