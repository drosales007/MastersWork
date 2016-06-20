#/bin/csh
xterm -sb -ls -T P0 -e java ElectionTester ring 0 5 &
xterm -sb -ls -T P1 -e java ElectionTester ring 1 5 &
xterm -sb -ls -T P2 -e java ElectionTester ring 2 5 &
xterm -sb -ls -T P3 -e java ElectionTester ring 3 5 &
xterm -sb -ls -T P4 -e java ElectionTester ring 4 5 &
