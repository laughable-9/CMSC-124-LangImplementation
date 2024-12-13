@echo off
echo CMSC 124 - Language Implementation of a Grammar of Sentences in Propositional Logic
echo A Machine Project
echo Prepared by Arriola, Bisenio, Domingo, Pagunsan, Zamora
echo Compiling Java files.
echo
javac *.java
if errorlevel 1 (
	echo Compilation failed. Exiting.
	exit /b
)
echo Running...
java Main
pause