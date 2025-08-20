# Einführung

Wenn wir *von Full-Stack-Entwicklung* sprechen , geht es um weit mehr
als nur das Zusammenfügen von Backend und Frontend. Wir sprechen vom
Aufbau eines **lebendigen Systems** , das Eingaben entgegennimmt,
präzise verarbeitet und sie so präsentiert, dass Menschen sie verstehen
und darauf reagieren können. Dieses Buch führt Sie durch den gesamten
Prozess.

Ich werde Sie nicht nur durch die Syntax und Semantik von Spring Boot
und React führen, sondern Ihnen auch beibringen, **wie ein
Softwarearchitekt zu denken** . Wir werden dabei zielstrebig vorgehen,
als Ingenieure und nicht als Hobby-Copy-Paste-Experten, denn unser Ziel
besteht nicht nur darin, „ etwas zum Laufen zu
bringen<span dir="rtl">“</span>, sondern **etwas zu schaffen, das es
wert ist, gebaut zu werden** .

### **Das Projekt im Überblick**

Wir werden ein **Produktmanagementsystem** erstellen , das sowohl
realistisch als auch erweiterbar ist, also die Art von Projekt, das
problemlos in einer Produktionsumgebung mit echten Benutzern und echten
Daten eingesetzt werden kann.

>**Unser Backend** wird eine **Spring Boot 3.x REST API** sein :
>
>**Sicher** : Authentifizierung und Autorisierung mit JWT.
>
>**Zuverlässig** : Schichtarchitektur, die die Belange sauber trennt.
>
>**Leistungsstark** : Caching mit Redis, Datenbankmigrationen mit Flyway
und Paginierung.
>
>**Observable** : Metriken über Actuator und Prometheus.
>
>**Getestet** : mit Spring Boot Test, Testcontainern und JUnit.
>
>**Unser Frontend** wird ein **mit Vite erstelltes React SPA** sein :
>
>**Responsive** : optimiert für Desktop und Mobilgeräte.
>
>**Benutzerfreundlich** : klares UI-Design, Formularvalidierung und
sofortiges Feedback über Benachrichtigungen.
>
>**Verbunden** : Nutzen Sie unsere API sicher und effizient.
>
>**Wartbar** : modulare Komponenten, wiederverwendbare Hooks und sauberes
Statusmanagement.

Nach der Fertigstellung wird dieses System ein portfoliowürdiges
Beispiel für Ihre Fähigkeit sein, eine Full-Stack-Anwendung von Null bis
zur Produktion zu entwerfen, zu implementieren und bereitzustellen.

### **Unsere Entwicklungsphilosophie**

In diesem Buch folgen wir drei Leitprinzipien:

>**Verstehen Sie, bevor Sie implementieren** : Ich werde erklären
, *warum* jede Entscheidung getroffen wird, oft im Gegensatz zu
alternativen Ansätzen, ob von anderen Java-Stacks oder völlig anderen
Ökosystemen wie Node.js, Django oder Ruby on Rails.

>**Schrittweises Erstellen** : Jedes Kapitel fügt eine Komplexitätsebene
hinzu, sodass Sie testen, überprüfen und verinnerlichen können, bevor
Sie fortfahren.

>**Arbeiten Sie wie ein Profi** : Wir integrieren GitHub-Workflows,
inkrementelle Zweige, API-Tests, Umgebungskonfiguration und sogar eine
Migration von Maven zu Gradle, da diese Übergänge in realen Projekten
vorkommen.

### **Die High-Level Architektur**

Bevor wir eine einzige Codezeile schreiben, müssen wir die *Karte* sehen.

![High-level architecture of the system](@site/static/img/intro.png)

Die beiden kommunizieren über **HTTP/JSON** , wobei sichere Endpunkte
durch JWT-Authentifizierung geschützt sind.

In **Teil V** werden wir sowohl das Backend als auch das Frontend mit
Docker containerisieren, sie für die Bereitstellung konfigurieren und
eine Überwachung hinzufügen, um sie funktionsfähig zu halten.

### **So arbeiten Sie dieses Buch**

Dies ist kein Zuschauersport. Ab dem ersten Kapitel **klonen Sie unser
GitHub-Repository** , sehen sich den Startzweig an
und **programmieren** mit mir. Unser Repository ist so strukturiert,
dass Sie entweder sequenziell von Kapitel 1 bis zum Ende arbeiten, Ihre
Arbeit jederzeit mit dem offiziellen Zweig vergleichen oder mittendrin
in das Projekt einsteigen können, wenn Sie möchten. Sehen Sie sich
einfach den Zweig für das jeweilige Kapitel an.

Ihnen stehen außerdem **Anhänge** zur Verfügung, die sozusagen die
Sprechstunden Ihres *Professors in gedruckter* Form darstellen . Sie
enthalten:

> Workflow-Diagramme zum Mitverfolgen
>
> GitHub-Verzweigungs- und Wiederherstellungstechniken
>
> IDE-Setup und Produktivitätsverknüpfungen
>
> Spickzettel für Spring Boot-Anmerkungen
>
> Fehlerbehebung bei häufigen Fehlern
>
> Checklisten für Bereitstellung und Sicherheit.

### **Ein Hinweis zu Werkzeugen**

Sie beginnen mit **Maven** für den Backend-Aufbau, da es weithin bekannt
und für Anfänger einfach zu bedienen ist. Später wechseln wir
zu **Gradle** , einem schnelleren und flexibleren Build-Tool, und ich
zeige Ihnen genau, wie Sie migrieren. Im Frontend verwenden
wir **Vite** aufgrund seiner Geschwindigkeit und der modernen Tools, die
es gegenüber herkömmlichen React-Setups bietet.

### **Letzte Worte, bevor wir beginnen**

Ich werde Ihr Führer sein, aber Sie sind der Erbauer. Betrachten Sie
jedes Kapitel nicht als eine Aufgabe, die es zu erledigen gilt, sondern
als eine **Fähigkeit, die es zu meistern gilt** . Auf der letzten Seite
haben Sie nicht nur eine professionelle Full-Stack-Anwendung erstellt,
sondern verstehen auch die Prinzipien, Muster und Arbeitsabläufe, die es
professionellen Entwicklern ermöglichen, *jede beliebige* Anwendung zu
erstellen.

Beginnen wir unsere Reise mit einer groben Übersicht über das System,
das wir erstellen werden. Öffnen Sie also Ihre IDE, klonen Sie das
Repository und betreten Sie den Maschinenraum unseres Systems: **das
Backend** .
