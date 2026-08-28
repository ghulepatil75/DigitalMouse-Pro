import socket
import threading
import pyautogui

HOST = "0.0.0.0"
PORT = 8765

pyautogui.PAUSE = 0

def handle(client, address):
    print(f"[+] Connected: {address}")
    buffer = b""
    try:
        while True:
            data = client.recv(4096)
            if not data:
                break
            buffer += data
            while b"\n" in buffer:
                raw, buffer = buffer.split(b"\n", 1)
                command = raw.decode("utf-8", errors="ignore").strip()
                if not command:
                    continue
                parts = command.split(" ", 2)
                try:
                    if parts[0] == "MOVE" and len(parts) == 3:
                        pyautogui.moveRel(int(parts[1]), int(parts[2]), duration=0)
                    elif parts[0] == "SCROLL" and len(parts) >= 2:
                        pyautogui.scroll(int(parts[1]))
                    elif parts[0] == "CLICK" and len(parts) >= 2:
                        button = {"1": "left", "2": "right", "4": "middle"}.get(parts[1])
                        if button:
                            pyautogui.click(button=button)
                    elif parts[0] == "KEY" and len(parts) >= 2:
                        pyautogui.press(chr(int(parts[1])))
                except Exception as exc:
                    print("Command error:", exc)
    finally:
        client.close()
        print(f"[-] Disconnected: {address}")

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server.bind((HOST, PORT))
server.listen(10)

print(f"Digital Mouse Pro server listening on {PORT}")
print("Use only on your trusted local network.")

try:
    while True:
        client, address = server.accept()
        threading.Thread(target=handle, args=(client, address), daemon=True).start()
except KeyboardInterrupt:
    print("\nStopping...")
finally:
    server.close()
