#/bin/csh
xterm -sb -ls -T P0 -e java Chat chatSim 0 5 simple &
xterm -sb -ls -T P1 -e java Chat chatSim 1 5 simple &
xterm -sb -ls -T P2 -e java Chat chatSim 2 5 simple &
xterm -sb -ls -T P3 -e java Chat chatSim 3 5 simple &
xterm -sb -ls -T P4 -e java Chat chatSim 4 5 simple &
