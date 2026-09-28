🖱️ Digital Mouse Pro v2

""Build APK" (https://github.com/ghulepatil75/DigitalMouse-Pro/actions/workflows/build-apk.yml/badge.svg)" (https://github.com/ghulepatil75/DigitalMouse-Pro/actions/workflows/build-apk.yml)
""Kotlin" (https://img.shields.io/badge/Kotlin-Android-purple?logo=kotlin)" (https://kotlinlang.org/)
""Android" (https://img.shields.io/badge/Android-9%2B-green?logo=android)" (https://developer.android.com/)
""Bluetooth HID" (https://img.shields.io/badge/Bluetooth-HID-blue?logo=bluetooth)" (https://developer.android.com/)
""License" (https://img.shields.io/badge/License-Open%20Source-lightgrey)" (LICENSE)

«Turn your Android smartphone into a wireless mouse and keyboard using Wi-Fi or Bluetooth HID.»

Digital Mouse Pro is an Android application designed to provide convenient remote mouse and keyboard control for compatible computers.

---

✨ Features

- 🖱️ Touchpad mouse control
- 👆 Left, right, and middle click
- 🔄 Two-finger scrolling in Wi-Fi mode
- ⌨️ Keyboard control
- 📡 Wi-Fi mouse and keyboard
- 🔵 Bluetooth HID mouse and keyboard
- 🖥️ Windows local server support
- 🤖 Automated APK builds with GitHub Actions
- 📱 Android 9+ Bluetooth HID foundation
- 🌐 Local network communication
- 🛠️ Built-in troubleshooting documentation

---

📡 How It Works

Wi-Fi Mode

Android Phone
      │
      │ Wi-Fi / LAN
      ▼
Windows PC
      │
      ▼
Python Server
      │
      ▼
Mouse / Keyboard Input

The Android application communicates with the Python server running on the Windows PC over the local network.

Bluetooth Mode

Android Phone
      │
      │ Bluetooth HID
      ▼
Compatible Computer
      │
      ▼
Mouse / Keyboard Input

Bluetooth HID support depends on the Android device, Android version, manufacturer implementation, and computer compatibility.

---

🚀 Wi-Fi Setup

1. Install Python

Install Python on your Windows PC.

Verify the installation:

python --version

If the command is unavailable, make sure Python is installed correctly and added to your system PATH.

---

2. Clone the Repository

git clone https://github.com/ghulepatil75/DigitalMouse-Pro.git

Enter the project directory:

cd DigitalMouse-Pro

---

3. Install Server Requirements

Install the Python dependencies:

pip install -r pc_server/requirements.txt

---

4. Start the Server

Run:

python pc_server/server.py

The default communication port is:

8765

Keep the server terminal running while using Wi-Fi mode.

---

5. Find Your PC IPv4 Address

Open Command Prompt:

ipconfig

Find the IPv4 address of the active network adapter.

Example:

IPv4 Address . . . . . . . . . . : 192.168.1.100

---

6. Connect the Android App

1. Open Digital Mouse Pro.
2. Select Wi-Fi mode.
3. Enter the PC's IPv4 address.
4. Connect.
5. Test the mouse and keyboard controls.

The phone and PC should normally be connected to the same local network.

---

🔵 Bluetooth HID Setup

Bluetooth HID allows the Android device to communicate directly with a compatible computer as a Bluetooth input device.

Steps

1. Turn on Bluetooth on the Android device.
2. Turn on Bluetooth on the computer.
3. Open Digital Mouse Pro.
4. Grant the required Bluetooth permissions.
5. Start Bluetooth HID.
6. Pair/connect the computer.
7. Test mouse and keyboard functionality.

Compatibility

Bluetooth HID availability depends on:

- Android version
- Device manufacturer
- Bluetooth hardware
- Android HID implementation
- Computer Bluetooth compatibility

Some devices may not support Bluetooth HID functionality correctly.

---

🛠️ Troubleshooting

📱 Phone Cannot Connect to the PC

Check the following:

- Make sure the phone and PC are connected to the same Wi-Fi network.
- Make sure the Python server is running.
- Verify that the PC IPv4 address entered in the app is correct.
- Check that port "8765" is not blocked by Windows Firewall.
- Restart the Python server.
- Reconnect the phone to the Wi-Fi network.

---

🖱️ Mouse Commands Are Not Responding

Try:

1. Restarting the Python server.
2. Reconnecting the phone to Wi-Fi.
3. Checking the server terminal for errors.
4. Confirming that the correct PC IP address is entered.
5. Restarting the Digital Mouse Pro application.

---

⌨️ Keyboard Is Not Working

Check:

- The phone is connected to the correct PC.
- The Python server is running.
- The connection has not been interrupted.
- The selected input mode is supported.
- No firewall rule is blocking the server.

---

🖥️ Server Does Not Start

First install the required dependencies:

pip install -r pc_server/requirements.txt

Then start the server:

python pc_server/server.py

If it still fails, check the terminal output for the specific Python or network error.

---

🔥 Windows Firewall

Windows Firewall can prevent local network communication.

If the application cannot connect even though the IP address is correct, check whether Windows Firewall is blocking Python or the server port.

Only allow the required application/port on networks you trust.

---

🤖 GitHub Actions

The repository includes an automated Android APK build workflow using GitHub Actions.

Workflow:

Git Push
   ↓
GitHub Actions
   ↓
Gradle Build
   ↓
APK
   ↓
GitHub Actions Artifact

To view the workflow:

GitHub → Actions → Build APK

After a successful build, the generated APK can be downloaded from the workflow artifacts.

---

🏗️ Project Structure

DigitalMouse-Pro/
│
├── app/
│   └── Android application source
│
├── pc_server/
│   ├── server.py
│   └── requirements.txt
│
├── screenshots/
│   └── Project screenshots
│
├── docs/
│   └── Documentation
│
├── .github/
│   └── workflows/
│       └── build-apk.yml
│
├── README.md
├── CHANGELOG.md
├── CONTRIBUTING.md
└── SECURITY.md

---

🔧 Technology Stack

Android

- Kotlin
- Android SDK
- Bluetooth HID APIs
- Android networking

PC Server

- Python
- Local network communication
- Windows input control

CI/CD

- GitHub Actions
- Gradle
- Automated APK builds

---

📱 Android Compatibility

Digital Mouse Pro is designed for:

Android 9+

Actual Bluetooth HID compatibility may vary between manufacturers and devices.

---

🌐 Network Requirements

For Wi-Fi mode:

Phone ───── Wi-Fi ───── PC

Both devices should be able to communicate over the local network.

The default server port is:

8765

Avoid exposing the local input-control server directly to the public internet.

---

🔐 Security

Digital Mouse Pro is intended primarily for trusted local networks.

Users should:

- Use trusted Wi-Fi networks.
- Avoid exposing the server port to the public internet.
- Keep Windows Firewall enabled.
- Avoid forwarding port "8765" from the router.
- Stop the server when it is not required.

For security-related information, see:

""SECURITY.md"" (SECURITY.md)

---

🤝 Contributing

Contributions are welcome.

If you want to improve Digital Mouse Pro:

1. Fork the repository.
2. Create a feature branch.
3. Make your changes.
4. Test the changes.
5. Commit your changes.
6. Push the branch.
7. Open a Pull Request.

Example:

git checkout -b feature/improve-wifi

After making your changes:

git add .
git commit -m "feat: improve Wi-Fi connectivity"
git push origin feature/improve-wifi

Then create a Pull Request on GitHub.

See ""CONTRIBUTING.md"" (CONTRIBUTING.md) for additional contribution guidelines.

---

📝 Changelog

See ""CHANGELOG.md"" (CHANGELOG.md) for project version history and updates.

---

📄 License

This project is released under the license included in the repository.

See:

""LICENSE"" (LICENSE)

---

👨‍💻 Author

Rohit Ghule

Electronics & Telecommunication Engineering
IoT • Embedded Systems • Android • Python

GitHub:
https://github.com/ghulepatil75

LinkedIn:
https://www.linkedin.com/in/rohitghule75/

---

⭐ Support the Project

If you find Digital Mouse Pro useful:

- ⭐ Star the repository
- 🐛 Report bugs
- 💡 Suggest improvements
- 🔧 Contribute through Pull Requests
- 📢 Share the project

Your feedback helps improve the project.

---

🖱️ Digital Mouse Pro

Android → Wi-Fi / Bluetooth → Computer

Built with Kotlin, Python, Android and open-source technologies.
