
Dieses README beschreibt das Aufsetzen eines Java Projekts, das Einträge in eine Datenbank vornehmen kann.

Voraussetzungen: Netbeans, PostgreSQL mit initialisierter Datenbank + Schema.
HINWEIS: Großbuchstaben sollten bei der Benennung von Tabellen, Schemata und Spalten nicht verwendet werden.
-> Großbuchstaben haben bei mir konsistent zu Abstürzen geführt.

Folgend wird alles in nummerierten Schritten erläutert:

0. Verbindung zur Datenbank erstellen

In Netbeans wird im linken Fenster, wo die Projekte im Tab Projects aufgelistet sind,
auf den Tab Services gewechselt.
Hier wird das Kontext-Menü durch einen Rechtslick auf Databases geöffnet.
Dort wird "New Connection..." gewählt.
Im sich öffnenden Fenster wird unter "Driver" als Treiber "PostgreSQL" gewählt.
Wenn PostgreSQL installiert ist, sollte ein Treiber in der Tabelle erscheinen.
-> Wenn kein Treiber aufgelistet ist, sollte bis nach Schritt 3 gewartet werden,
    bevor Schritt 0 wiederholt wird.
"Next" wird bestätigt.
Im darauffolgenden Fenster werden Namen für Datenbank, Benutzer und dessen Passwort angegeben.
-> Der eingegebene Name muss dem der Datenbank exakt entsprechen, Groß- und Kleinschreibung beachten.
Die Verbindung wird zuletzt getestet, dann ist die Datenbankverbindung mit einem
weiteren Klick auf "Next" eingerichtet.


1. Projekterstellung

In Netbeans wird ein neues Java Maven Projekt erstellt.
-> Maven vereinfacht den Import notwendiger Bibliotheken.


2. Import der Bibliotheken

Im neu erstellten Maven Projekt, öffne "pom.xml" im Ordner "Project Files".
-> pom.xml enthält sogenannte Dependencies. 
    Diese beschreiben, welche Bibliotheken dem Projekt zugeordnet werden sollen.
    Dependencies werden in der XML Datei einfach als normaler Text geschrieben, 
    umgeben von den typischen XML Tags.
    Die so angegebenen Bibliotheken werden beim Ausführen des Programms automatisch
    heruntergeladen.

Hier werden nun die notwendigen Dependencies angegeben, um Zugriff auf die nötigen
Bibliotheken zu bekommen.
Dazu wird innerhalb der <project> tags manuell ein neues tag namens "dependencies" eingegeben.
(<project> ist das root tag der pom.xml)

Bsp:
<dependencies>

</dependencies>

Innerhalb dieser tags können jetzt einzelne Dependencies eingefügt werden.
Diese Dependencies findet man auf:
https://mvnrepository.com/

Gebraucht wird Hibernate, JPA und ein PostgreSQL JDBC Driver.


2.1 Hibernate

Im Maven Repository wird der Suchbegriff "hibernate" eingegeben.
Der Link "Hibernate Core Relocation" wird geöffnet.
Jetzt wird unten in der Tabelle im Tab "Central" einer der neusten Links geöffnet, bei
dem "Final" im Namen steht.
-> Final ist mit höherer Wahrscheinlichkeit fehlerfrei. Alpha/Beta Versionen können Fehler enthalten.
-> Rechts in der Tabelle ist eine Spalte namens "Usages".
    Je höher die Zahl, desto besser. Wenn viele Leute einen Build verwenden, dann funktioniert er wahrscheinlich auch gut.
Auf der nun geöffneten Seite wird im Tab "Maven" die Dependency kopiert und in der Netbeans
pom.xml zwischen den "dependencies" tag eingefügt.
(-> dann wird alt+shift+F gedrückt, um den Text zu formatieren)

Bsp:

    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.4.2.Final</version>
        </dependency>
    </dependencies>


2.2 JPA

Ähnlich wie bei Hibernate wird "JPA" als Suchbegriff eingeben.
Der Link "Java Persistence API, Version 2.1" wird geöffnet.
Version mit den höchsten Usages wird gewählt, dann wird genau gleich wie bei hibernate weiter verfahren.


2.3 PostgreSQL JDBC Driver

Dieser muss dem evtl. vorhandenen Treiber der Datenbank angepasst werden.
Die Version des Treibers lässt sich herausfinden, indem man in Netbeans auf der linken Seite
im "Services" Tab auf den Ordner "Drivers" geht, da das Kontext-Menü von "PostgreSQL" öffnet
und dann auf "Customize" klickt.
Der Treiber wird unter "Driver File(s)" angezeigt.
Sollte keiner vorhanden sein, kann ein neuer wie gehabt vom Maven Repository heruntergeladen werden.


3. Erstellung von Klassen und anderen Dateien

Gebraucht wird eine Main-Klasse, eine persistence.xml und 
jeweils eine Entity-Klasse für jede Tabelle in der Datenbank.


3.1 Main

Erstellt wird eine normale Main-Klasse.
Diese kann und sollte direkt ausgeführt werden, solange in der main Methode noch kein code steht,
damit Maven alle angegebenen Dependencies herunterlädt.
-> Jetzt kann die Datenbankverbindung von Schritt 0 erstellt werden, sollte es vorher nicht möglich gewesen sein.


3.2 persistence.xml

Diese Datei kann generiert werden.
Es wird das Kontext-Menü mit einem Rechtsklick auf das Projekt geöffnet.
Im sich öffnenden Fenster bei New > Other... wird "persistence" als Filterbegriff eingegeben.
Der gefunde File type "Persistence Unit" wird ausgewählt, dann "Next" gedrückt.
Dort wird nun die vorher konfigurierte Datenbankverbindung angegeben.
Unter "Table Generation Strategy" wird "None" ausgewählt.
-> Das bedeutet, dass keine neue Tabelle beim Ausführen des Programms erstellt wird.
-> Tabelle muss vorher in der Datenbank angelegt worden sein.
-> Ist "create" gewählt (was als default ausgewählt ist), so kann es sein, dass
    das Programm nicht ausführbar ist, sollte die Tabelle bereits existieren.

Wichtig!:
In der persistence.xml in source Ansicht (kann in der oberen Leiste per Button umgeschaltet werden)
muss das <provider> tag entfernt werden, wenn es beim Ausführen des Programms zu Abstürzen kommt.

Die persistence.xml Datei befindet sich fortan im Projektordner Other Sources > src/main/resources > META-INF.


3.3 Entity class

Diese Datei kann generiert werden.
Eine Entity class ist die Repräsentation einer SQL Tabelle im Java Projekt.
Erstellt wird sie ähnlich der persistence.xml, jedoch wird nach "entity" gesucht.
In Category > Persistence wird File Type > Entity Class ausgewählt, dann "Next" gedrückt.
Der dann gewählte Klassenname sollte am besten dem Namen der bestehenden SQL Tabelle entsprechen.
Der Tabellen- und Schemaname kann dann per @Table spezifiziert werden.

Bsp:

@Entity
@Table(name = "test", schema = "public")
public class Test implements Serializable {...}

In der Entity Class werden dann alle vorhandenen Spalten als Instanzvariablen angegeben,
die dort gewählten Datentypen müssen denen der Tabellenspalten entsprechen.
Über jeder Instanzvariable kann der Name der Spalte angegeben werden, mit @Column.

Bsp:
@Column(name = "PhoneNumber")
private int phoneNumber;

Dann müssen alle Instanzvariablen mit getter und setter gekapselt werden.
Kontext-Klick auf die Variable > Entweder Insert Code... oder Refractor > Encapsulate Fields...


4. EntityManagerFactory und EntityManager

In der Main Class wird nun der Code zum Schreiben in die Datenbank eingefügt.

Bsp:

EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("com.mycompany_HibernateTest_jar_1.0-SNAPSHOTPU");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        SomeEntityClass c = new SomeEntityClass();
        c.setName("Hello");
        entityManager.persist(c);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

________________________________________________________________________________

Das Argument der "createEntityManagerFactory" wird der persistance.xml entnommen, 
nämlich vom tag <persistence-unit name="com.mycompany_HibernateTest3_jar_1.0-SNAPSHOTPU"...
kopieren und einfügen.

Das Objekt EntityManager kümmert sich um den Beginn des Schreibens in die Datenbank,
die einzelnen Tabelleneinträge und das committen, also Absenden an sich.

Die Tabelleneinträge, also Reihen, werden einzeln als Java Objekte der Entity class initialisiert , 
per setter mit Werten versehen und dem EntityManager zugeordnet

Am Ende des Prozesses sollten der EntityManager und die EntityManagerFactory geschlossen werden.

________________________________________________________________________________

ERROR: relation "hibernate_sequence" does not exist

Um diesen Fehler zu beheben, muss in der Datenbank eine Sequenz angelegt werden.
Dazu kann folgende Zeile in PostgreSQL ausgeführt werden:

CREATE SEQUENCE public.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;

Diese scheint dem Schema untergeordnet zu sein und wird der SQL Datei hinzugefügt,
um zukünftige Fehler zu vermeiden.


