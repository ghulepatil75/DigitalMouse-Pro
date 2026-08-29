# 🖱️ Digital Mouse Pro v2

Turn your Android phone into a wireless mouse and keyboard.

## ✨ Features

- 🖱️ Touchpad mouse control
- 👆 Left / Right / Middle click
- 🔄 Two-finger scrolling in Wi-Fi mode
- ⌨️ Keyboard control
- 📡 Wi-Fi mouse + keyboard
- 🔵 Bluetooth HID mouse + keyboard
- Windows local server
- 🤖 GitHub Actions APK build
- 📱 Android 9+ Bluetooth HID foundation

## 📡 How It Works

### Wi-Fi

Android Phone → Wi-Fi/LAN → Windows PC → Python Server

### Bluetooth

Android Phone → Bluetooth HID → Compatible Computer

## 🚀 Wi-Fi Setup

1. Install Python on Windows.
2. Install the server requirements:

`pip install -r pc_server/requirements.txt`

3. Start the server:

`python pc_server/server.py`

4. Run:

`ipconfig`

5. Find the PC IPv4 address.
6. Enter the address in the Digital Mouse Pro app.

## 🔵 Bluetooth Setup

1. Turn on Bluetooth.
2. Open Digital Mouse Pro.
3. Grant Bluetooth permissions.
4. Start Bluetooth HID.
5. Pair/connect your computer.
6. Test the mouse and keyboard.

Bluetooth HID availability depends on the Android device and manufacturer.

## 🤖 GitHub Actions

The project includes an automated APK build workflow.

Open:

GitHub → Actions → Build Digital Mouse Pro

Then download the generated APK artifact.

## 🏗️ Project Structure

```text
DigitalMouse-Pro/
├── app/
├── pc_server/
├── screenshots/
├── docs/
├── .github/workflows/
├── README.md
├── CHANGELOG.md
├── CONTRIBUTING.md
└── SECURITY.md
