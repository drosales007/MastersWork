#/bin/csh
xterm -sb -ls -T P0 -e java CameraTester CamRecv 0 5 RecvCamera &
xterm -sb -ls -T P1 -e java CameraTester CamRecv 1 5 RecvCamera &
xterm -sb -ls -T P2 -e java CameraTester CamRecv 2 5 RecvCamera &
xterm -sb -ls -T P3 -e java CameraTester CamRecv 3 5 RecvCamera &
xterm -sb -ls -T P4 -e java CameraTester CamRecv 4 5 RecvCamera &
