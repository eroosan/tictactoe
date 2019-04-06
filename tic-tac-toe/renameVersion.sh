#!/bin/bash         
grep -m 1 '<version>' $2 > file.txt
$(sed -i 's/<version>[^-]*-\(.*\)<\/version>/\1/' file.txt)
sed -i "s/<version>.*<\/version>/<version>$1<\/version>/" $2
rm file.txt
