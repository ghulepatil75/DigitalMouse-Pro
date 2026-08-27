package com.ghulepatil.digitalmouse
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import kotlin.math.roundToInt

class TouchpadView(c: Context): View(c) {
 interface Listener { fun move(x:Int,y:Int); fun scroll(v:Int) }
 var listener: Listener?=null
 private val p=Paint(Paint.ANTI_ALIAS_FLAG)
 private var x=0f; private var y=0f; private var multi=false
 override fun onDraw(c:Canvas) {
  p.color=Color.rgb(28,30,36); c.drawRect(0f,0f,width.toFloat(),height.toFloat(),p)
  p.color=Color.rgb(110,115,125); p.textAlign=Paint.Align.CENTER; p.textSize=18f
  c.drawText("TOUCHPAD",width/2f,height/2f,p); p.textSize=13f
  c.drawText("1 finger: cursor   •   2 fingers: scroll",width/2f,height/2f+27,p)
 }
 override fun onTouchEvent(e:MotionEvent):Boolean {
  when(e.actionMasked){
   MotionEvent.ACTION_DOWN->{x=e.x;y=e.y;multi=false}
   MotionEvent.ACTION_POINTER_DOWN->{if(e.pointerCount>1)multi=true;y=e.getY(0)}
   MotionEvent.ACTION_MOVE->{
    if(e.pointerCount>1||multi){val d=(e.getY(0)-y).roundToInt(); if(d!=0)listener?.scroll(-d/3); y=e.getY(0)}
    else {val dx=(e.x-x).roundToInt();val dy=(e.y-y).roundToInt();if(dx!=0||dy!=0)listener?.move(dx*2,dy*2);x=e.x;y=e.y}
   }
   MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL->{multi=false}
  }; return true
 }
}
