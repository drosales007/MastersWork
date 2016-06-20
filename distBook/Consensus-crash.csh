#/bin/csh
xterm -sb -ls -T P0 -e java ConsensusTester consenCrash 0 5 &
xterm -sb -ls -T P1 -e java ConsensusTester consenCrash 1 5 &
xterm -sb -ls -T P2 -e java ConsensusTester consenCrash 2 5 &
xterm -sb -ls -T P3 -e java ConsensusTester consenCrash 3 5 &
xterm -sb -ls -T P4 -e java ConsensusTester consenCrash 4 5 &
