#!/bin/bash

delete="D"
express="E"
filelog="$1"

for fi in $(sudo find /home/cpu11183 -type f -size +100k)
do
    #get current time
    now=$(date +"%T")
    echo "current file: $fi"
    read -p 'Press D to delete and press E to express and other to show next file: ' input
    if [ $input == $delete ]; then
        echo "time and file are deleted: $now: $fi" >> $filelog
        rm $fi
        echo "delete successfull"
    elif [ $input == $express ]; then
        echo "time and file are expressed: $now: $fi" >> $filelog
        zip -r $fi.zip $fi
        rm $fi
        echo "express successfull"
    fi
done
    
