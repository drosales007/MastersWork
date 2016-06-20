#/bin/csh

# $1 is the name of the Camera Algorithm
# $2 is the name of the CamUser
xterm -sb -ls -T P0 -e java CameraTester Camera 0 5 $1 $2 &
xterm -sb -ls -T P1 -e java CameraTester Camera 1 5 $1 $2 &
xterm -sb -ls -T P2 -e java CameraTester Camera 2 5 $1 $2 &
xterm -sb -ls -T P3 -e java CameraTester Camera 3 5 $1 $2 &
xterm -sb -ls -T P4 -e java CameraTester Camera 4 5 $1 $2 &
