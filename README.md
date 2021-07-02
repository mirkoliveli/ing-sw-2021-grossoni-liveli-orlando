# Masters Of Renaissance Board Game

![latest release](https://img.shields.io/badge/release-v1.1-green)

<img src="http://www.craniocreations.it/wp-content/uploads/2019/06/Masters-of-Renaissance_box3D_ombra.png" width=192px height=192 px align="right" />

Masters Of Renaissance Board Game is the final test of **"Software Engineering"**, course of **"Computer Science Engineering"** held at Politecnico di Milano (2020/2021).

**Teacher** Pierluigi San Pietro


## Project specification
The project consists of a Java version of the board game *Masters Of Renaissance*, made by Cranio Creations

You can find the full game [here](http://www.craniocreations.it/prodotto/masters-of-renaissance/).

The final version includes:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* source code of the implementation;
* source code of unity tests.
* javadoc of the entire code.
## Find out more



## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Complete rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Socket |[![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| CLI |[![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| GUI | [![YELLOW](http://placehold.it/15/ffdd00/ffdd00)]() |
| Multiple games | [![RED](http://placehold.it/15/f03c15/f03c15)]()|
| Local Single Player | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Persistence | [![RED](http://placehold.it/15/f03c15/f03c15)]() |
| Cheats Functions | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |

#### Legend
[![RED](http://placehold.it/15/f03c15/f03c15)]() Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)]() Implementing&nbsp;&nbsp;&nbsp;&nbsp;[![GREEN](http://placehold.it/15/44bb44/44bb44)]() Implemented


<!--
[![RED](http://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)
-->

### Build instructions

The jar were built using the [Maven Install Plugin](https://maven.apache.org/plugins/maven-install-plugin/)
If you want to build yourself the executables jar files run:
```
mvn install
```

### How to start
Requirements java 15+
#### Server

Run:
```
java -jar Server.jar
```
The server will be opened on port 1234
###Client

The ClientColoured.jar shows coloured text on the terminal (only Linux).
Both versions work correctly.


#### Client.jar
From the directory where the jar file is located, launch the following command:
```
java -jar Client.jar
````

#### ClientColoured.jar
From the directory where the jar file is located, launch the following command:
```
java -jar ClientColoured.jar
````

#### Gui.jar
Requirements: Javafx16
From the directory where the jar file is located, launch the following command:
```
java --module-path /<YOURPATH>/javafx-sdk-16/lib --add-modules javafx.base,javafx.controls,javafx.fxml -jar Gui.jar
```
you have to import javafx modules in the <YOURPATH>, then launch with the command above
  
  
[comment]: <> (## Test cases)

[comment]: <> (All tests in model and controller has a classes' coverage at 100%.)

[comment]: <> (**Coverage criteria: code lines.**)

[comment]: <> (| Package |Tested Class | Coverage |)

[comment]: <> (|:-----------------------|:------------------|:------------------------------------:|)

[comment]: <> (| Controller | ActionController | 115/135 &#40;85%&#41;)

[comment]: <> (| Controller | Controller | 50/54 &#40;92%&#41;)

[comment]: <> (| Controller | TurnControllerTest | 100/140 &#40;71%&#41;)

[comment]: <> (| Model | Global Package | 667/710 &#40;93%&#41;)

## The Team PSP31
* [Riccardo Grossoni](https://github.com/riccardogrossoni)
* [Mirko Li Veli](https://github.com/mirkoliveli)
* [Andrea Orlando](https://github.com/orlandrea)

## Software used
**draw.io** - sequence diagrams

**draw.io** - UML diagrams

**Intellij IDEA Ultimate** - main IDE 


