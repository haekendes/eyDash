# 1. Installation

Hardware Voraussetzung:
- Raspberry Pi mit Wlan & Bluetooth
- PIR Bewegungssensor (~50cm entfernt vom Raspberry platzieren)
- Smartphone oder anderes Gerät mit Bluetooth

Software Voraussetzung:
- eyDash.jar 
- Dazugehörige Bibliotheken
- WiringPi
- xscreensaver
- PostgreSQL und pgAdmin


Konfiguration des Raspberrys:
- PostgreSQL Datenbank mit pgAdmin installieren
  - Neue Datenbank anlegen, Name: "test", PW: "kuchen123"
  - Anlegen der Tabellen mithilfe der SQL Datei unter
      eyDashDatabaseManager/sqlFile/eyDash - Initialize Database PostgreSQL.sql
- WiringPi per apt-get installieren
- PIR Bewegungssensor anschließen
  - Vcc (5V) Pin zur Stromversorgung mit dem Raspberry Pin 2
  - GND (Masse) mit Pin 6
  - Dout (Digital out) zur Datenübertragung mit Pin 7, auch GPIO 4
- xscreensaver per apt-get installieren
  - xscreensaver abschalten, damit der Bildschirm nicht automatisch abgeschaltet wird.
  
  
Konfiguration Smartphone/anderes Bluetoothgerät:
- "Visible to all nearby devices" permanent aktivieren.
  
  
Installation eyDash Software:
- eyDash.jar zusammen mit lib Ordner in beliebigen Ordner des Raspberrys kopieren
  
  
# 2. Programmstart
  
- Per Linux Konsole in das Installationsverzeichnis der eyDash Software navigieren
  - Befehl "sudo java -jar eyDash.jar" führt das Programm normal aus
  - Befehl "sudo java -jar eyDash.jar u" registriert einen neuen Benutzer
  
  
# 3. Source Code aufsetzen

- eyDash, eyDashDatabaseManager sind Netbeans Projekte
- eyDashGoogleConnector ist ein Gradle Projekt, kann per Gradle Plugin in Netbeans importiert werden
- eyDashLibraries sind benötigte Bibliotheken
- build beinhaltet die neuste, direkt ausführbare Version des Programms

eyDash ist das Hauptprojekt. eyDashDatabaseManager und eyDashGoogleConnector müssen separat kompiliert und als Bibliotheken zum eyDash Projekt hinzugefügt werden, da das eyDash Hauptprojekt diese beiden Unterprojekte verwendet.
Auch die Bibliotheken in eyDashLibraries müssen dem eyDash Hauptprojekt als Bibliotheken hinzugefügt werden.

Bis auf den DisplayController des Hauptprojekts sollte jeder Teil aller Projekte auch auf einem Windows PC ausführbar sein.
Der DisplayController läuft nur auf Linux.
