# PodiumAPI



## Présentation générale

PodiumAPI est une API Rest permettant aux utilisateurs de créer des Podium contenant des éléments et de pouvoir voter pour ces derniers.

La stack technique du projet est constituée de Jetty, Hibernate, Jersey et MySQL.

L'archive livrée est constituée, en plus de cette documentation, des éléments suivants :
- Un script SQL permettant d'importer la base "podiumapi"
- L'application PodiumAPI compilée sous forme de jar
- Les sources du projet, incluant les scripts SQL
- La spécification Swagger respectée par l'application
- Un jeu d'essai au format JSON



## Installation du projet

### Base MySQL

Afin de faire tourner PodiumAPI, une base MySQL est nécessaire. 
Par défaut, l'application essaiera d'atteindre la base locale avec le port 3306, en utilisant l'utilisateur "root" avec le mot de passe "Azerty!59000".
L'utilisateur doit avoir accès en lecture et en écriture à une base "podiumapi", dont le script d'importation est livré dans cette archive. (par exemple, utiliser la commande "mysql -u root -p podiumapi < podiumapi.sql")
Il est possible de modifier cette configuration en modifiant le fichier "hibernate.cfg.xml" présent dans le jar.

### Lancement de l'application

L'application nécessite Java 8 afin de fonctionner (vérifiable via la commande "java -version").
Depuis un shell/invité de commande, il est possible de la démarrer en exécutant "java -jar PodiumAPI-1.1.jar" en utilisant le nom du jar livré.
L'application est désormais lancée ; se référer au fichier swagger.yml afin d'en apprendre davantage sur son utilisation.