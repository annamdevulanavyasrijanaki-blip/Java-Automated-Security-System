# Automated Security System (Phase 1) 🛡️

A robust Java terminal application that simulates a professional security gate. This project demonstrates core backend programming concepts including file handling, security logic, and real-time data logging.

## 🚀 Key Features
* **Multi-Criteria Access Gate:** Validates users based on Age, Account Type (Premium), and encrypted password matching.
* **Persistent Audit Logs:** Automatically records every entry attempt in `security_logs.txt` with precise timestamps using `java.time`.
* **Security Lockout (3-Strike Rule):** Protects against brute-force attacks by locking the system after 3 consecutive failed attempts.
* **Admin View:** Built-in log reader to view system history directly from the terminal.
* **Resilient Error Handling:** Uses `try-catch` blocks to prevent crashes from invalid user inputs (e.g., entering text into number fields).

## 🛠️ Tech Stack
* **Language:** Java 17+
* **File I/O:** `PrintWriter`, `FileWriter`, `BufferedReader`
* **Date/Time:** `LocalDateTime`, `DateTimeFormatter`

## 📖 How to Run
1. Clone this repository or download the `Systemsecurity.java` file.
2. Compile the code:
   ```bash
   javac Systemsecurity.java
