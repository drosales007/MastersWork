#/bin/csh
xterm -sb -ls -T P0 -e java GlobalFuncTester sum 0 5 10 &
xterm -sb -ls -T P1 -e java GlobalFuncTester sum 1 5 11 &
xterm -sb -ls -T P2 -e java GlobalFuncTester sum 2 5 12 &
xterm -sb -ls -T P3 -e java GlobalFuncTester sum 3 5 13 &
xterm -sb -ls -T P4 -e java GlobalFuncTester sum 4 5 14 &
