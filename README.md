# java-mvp-clean

Support library for "MVP Clean" architecture.

## About

The library is a ported version of the android clean architecture used in an 
[android app project](https://github.com/GameplayJDK/Vertretungsplan/tree/master/app/src/main/java/de/GameplayJDK/Vertretungsplan/Mvp) 
 and a 
[swing application project](https://github.com/GameplayJDK/java-wfc-image/tree/master/src/de/gameplayjdk/jwfcimage/mvp)
 of mine.
 
At least the swing application will be ported to use this library in the future.

Supported handlers are:
* swing/awt event queue (through reflection)
* android handler (through reflection)
* a user-defined implementation through a supplier

The reason why reflection is used for the both supported implementations is, that both will never be available at the 
same time and thus using the explicit calls would not work.

## Installation

For now, there are no prebuilt jar files available, so you will have to clone (or download) this repository and build
and artifact yourself locally. Then add it as a jar dependency to your project.

The project requires Java version 8.

> Open TODO:

A prebuilt jar file will be available with the first release.

## Example

> Open TODO:

A usage example will soon be available.

## License

It's MIT.
