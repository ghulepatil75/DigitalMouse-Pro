import socket,threading,pyautogui
PORT=8765
pyautogui.PAUSE=0
def client(c,a):
 print("[+] Connected",a)
 try:
  buf=b""
  while True:
   d=c.recv(4096)
   if not d: break
   buf+=d
   while b"\n" in buf:
    line,buf=buf.split(b"\n",1); p=line.decode("utf-8","ignore").split(" ",2)
    if not p: continue
    try:
     if p[0]=="MOVE": pyautogui.moveRel(int(p[1]),int(p[2]))
     elif p[0]=="SCROLL": pyautogui.scroll(int(p[1]))
     elif p[0]=="CLICK" and len(p)>1:
      b={"1":"left","2":"right","4":"middle"}.get(p[1]); b and pyautogui.click(button=b)
     elif p[0]=="KEY" and len(p)>1: pyautogui.press(chr(int(p[1])))
    except Exception as e: print("Command:",e)
 finally: c.close();print("[-] Disconnected",a)
s=socket.socket();s.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1);s.bind(("0.0.0.0",PORT));s.listen(10)
print("Digital Mouse Pro server listening on",PORT)
while True:
 c,a=s.accept();threading.Thread(target=client,args=(c,a),daemon=True).start()
