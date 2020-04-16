CONNECT-4- THE GAME

ANFORDERUNGEN

Für das Spiel sind mindestens bzw. höchstens 2 Spieler erforderlich. Die Spieler können auf Zeilenebene bis zu 8 Felder und auf Spaltenebene bis zu 9 Felder, das Spielfeld gestalten.
Der Quellcode beinhaltet die benötigten Librarys der JavaUmgebung und muss nicht konfiguriert werden.

WICHTIG : Die Zeilenebene muss aus Algorithmischen und funktionalen Gegebenheiten in der Programmierung, immer tiefer als die Spaltenebene sein.

Die Spaltenzahl darf maximal bzw. muss minimal eine Einheit höher als die Reihenzahl sein!


        5X6                                     6X5
____________________                  _______________________
| 0   0   0   0   0 |  FALSCH        | 0   0   0   0   0   0 |  RICHTIG
| 0   0   0   0   0 |                | 0   0   0   0   0   0 |
| 0   0   0   0   0 |                | 0   0   0   0   0   0 |
| 0   0   0   0   0 |                | 0   0   0   0   0   0 |
| 0   0   0   0   0 |                | 0   0   0   0   0   0 |
| 0   0   0   0   0 |                 -----------------------
---------------------



SPIELREGELN
In diesem klassischem Gesellschaftsspiel ist das Ziel,
als erster Spieler eine 4er Reihe in horizontaler, vertikaler oder auch diagonaler Reihenfolge zu platzieren.
Der erste Spieler, der diese Bedingung erfüllt, gewinnt das Spiel.


TECHNISCHE DETAILS

Bei diesem Projekt wurde  auf ein klassischen MVC-Pattern verzichtet. Bei der Programmierung wurde auf eine Model-Instanz verzichtet, weil sie in diesem Kontext als nicht notwendig gefunden wurde.
Alle Inputs werden direkt mit der Controller-Instanz geregelt und auf die View übertragen.

Ich fand diese Lösung für ausgereifter und codesparender.

Der Code für die Bewertung der gespielten Felder basiert auf folgende Muster:
Die Bewertung der Spielfelder erfolgt auf horizontaler, vertikaler und mehreren diagonalen Prüfmethoden.

proofRows = horizontale Ebene
proofColumns = vertikale Ebene


proofDiagOne,proofDiagTwo, proofDiagThree, proofDiagFour, proofDiagFive, proofDiagSix;

             _______proofDiagOne_______
            | 1   0   0   0   0   0   0|
            | 2   1   0   0   0   0   0|
            | 3   2   1   0   0   0   0|
            | 0   3   2   1   0   0   0|
            | 0   0   3   2   1   0   0|
            | 0   0   0   3   2   1   0|
             --------------------------

             _______proofDiagTwo_______
            | 0   1   2   0   0   0   0|
            | 0   0   1   2   0   0   0|
            | 0   0   0   1   2   0   0|
            | 0   0   0   0   1   2   0|
            | 0   0   0   0   0   1   2|
            | 0   0   0   0   0   0   1|
             --------------------------

              _______proofDiagThree_____
             | 0   0   0   1   0   0   0|
             | 0   0   0   0   1   0   0|
             | 0   0   0   0   0   1   0|
             | 0   0   0   0   0   0   1|
             | 0   0   0   0   0   0   0|
             | 0   0   0   0   0   0   0|
              --------------------------

              _______proofDiagFour______
             | 0   0   0   1   0   0   0|
             | 0   0   1   0   0   0   0|
             | 0   1   0   0   0   0   0|
             | 1   0   0   0   0   0   0|
             | 0   0   0   0   0   0   0|
             | 0   0   0   0   0   0   0|
              --------------------------

              _______proofDiagFive______
             | 0   0   0   0   0   0   1|
             | 0   0   0   0   0   1   2|
             | 0   0   0   0   1   2   3|
             | 0   0   0   1   2   3   0|
             | 0   0   1   2   3   0   0|
             | 0   1   2   3   0   0   0|
              --------------------------

              _______proofDiagSix______
             | 0   0   0   0   2   1   0|
             | 0   0   0   2   1   0   0|
             | 0   0   2   1   0   0   0|
             | 0   2   1   0   0   0   0|
             | 2   1   0   0   0   0   0|
             | 1   0   0   0   0   0   0|
              --------------------------

Dieser Algorithmus ermöglicht eine variable Spielfeldgrösse, wobei die OBEN abgebildeten Zahlen die Reihenfolge der Iterierung am Spielfeld aufzeigen.
Je nachdem in welche Richtung die Überprüfung erfolgt, links-diagonal oder rechts-diagonal, werden die letzten bzw. ersten drei Spalten bei der Iterierung ausgelassen.

 			  __________________________
             | X   X   X   M   X   X   X|
             | X   X   M   M   M   X   X|
             | X   M   M   M   M   M   X|
             | X   M   M   M   M   M   X|
             | X   X   M   M   M   X   X|
             | X   X   X   M   X   X   X|
              --------------------------

Ich wünsche viel Spass beim Spielen.

Der Quellcode dieses Projekts steht nach der Bewertung des Dozenten, für alle Interessenten frei verfügbar und kann für persönliche Zwecke genutzt werden.


Cakmakci Mulla