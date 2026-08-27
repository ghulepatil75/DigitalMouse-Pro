package com.ghulepatil.digitalmouse
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.Executors
class WifiController {
 private var socket:Socket?=null; private var out:PrintWriter?=null
 private val ex=Executors.newSingleThreadExecutor()
 fun connect(host:String,onResult:(Boolean,String)->Unit){
  ex.execute{try{val s=Socket();s.connect(java.net.InetSocketAddress(host,8765),2500);socket=s;out=PrintWriter(s.getOutputStream(),true);onResult(true,"Connected to $host")}catch(e:Exception){onResult(false,"Wi-Fi failed: ${e.message}")}}
 }
 fun send(s:String){ex.execute{try{out?.println(s)}catch(_:Exception){}}}
 fun close(){try{socket?.close()}catch(_:Exception){};socket=null;out=null}
}
