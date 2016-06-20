#! /bin/bash
if [ $# -ne 2 ] 
then
 echo "Usage: replaceAll <original> <new>";
 exit;
fi
/bin/ls *.java | xargs -n 1 sed -i "s/$1/$2/g"
