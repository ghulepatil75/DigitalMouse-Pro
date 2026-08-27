# Digital Mouse Pro v2

Features:
- Wi-Fi mouse + keyboard
- Bluetooth HID mouse/keyboard foundation (Android 9+)
- Touchpad
- Left/right/middle click
- Two-finger scroll in Wi-Fi mode
- GitHub Actions APK build
- Windows local server

## GitHub build
Upload the whole project to a GitHub repository, push to `main`, open Actions, run **Build Digital Mouse Pro**, then download the `DigitalMouse-Pro-APK` artifact.

## Wi-Fi
On Windows install Python and `pc_server/requirements.txt`, run the server, use `ipconfig`, and enter the IPv4 address in the phone app.

## Bluetooth
On supported Android versions, tap Bluetooth in the app and grant Bluetooth permissions. Windows pairing/connection behavior depends on the phone's Bluetooth HID support. Android Bluetooth HID Device is not available on every phone/vendor build.

## Safety
The Wi-Fi server is intentionally LAN-only. Do not expose it to the public internet.
