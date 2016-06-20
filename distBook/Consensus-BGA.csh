#/bin/csh
xterm -sb -ls -T P0 -e java BGATester consenBGA 0 5 &
xterm -sb -ls -T P1 -e java BGATester consenBGA 1 5 &
xterm -sb -ls -T P2 -e java BGATester consenBGA 2 5 &
xterm -sb -ls -T P3 -e java BGATester consenBGA 3 5 &
xterm -sb -ls -T P4 -e java BGATester consenBGA 4 5 &
