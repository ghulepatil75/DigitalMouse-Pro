# 🖱️ Digital Mouse Pro v2
[![Build APK](https://github.com/ghulepatil75/DigitalMouse-Pro/actions/workflows/build-apk.yml/badge.svg)](https://github.com/ghulepatil75/DigitalMouse-Pro/actions/workflows/build-apk.yml)
[![Kotlin](https://img.shields.io/badge/Kotlin-Android-purple?logo=kotlin)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-9%2B-green?logo=android)](https://developer.android.com/)
[![Bluetooth HID](https://img.shields.io/badge/Bluetooth-HID-blue?logo=bluetooth)](https://developer.android.com/)
[![License](https://img.shields.io/badge/License-Open%20Source-lightgrey)](LICENSE)
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
