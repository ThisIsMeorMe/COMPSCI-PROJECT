#!/bin/bash
cd "/mnt/chromeos/MyFiles/Downloads/COMPSCI-PROJECT-main (3)/COMPSCI-PROJECT-main" || exit
javac -d bin src/*.java 2>&1
if [ $? -eq 0 ]; then
    java -cp bin Main 2>&1 | head -50
else
    echo "Compilation failed."
fi
read -p "Press Enter to close..."
