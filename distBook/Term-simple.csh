#/bin/csh
xterm -sb -ls -T P0 -e java TermTester termSim 0 5 0 &
xterm -sb -ls -T P1 -e java TermTester termSim 1 5 0 &
xterm -sb -ls -T P2 -e java TermTester termSim 2 5 0 &
xterm -sb -ls -T P3 -e java TermTester termSim 3 5 0 &
xterm -sb -ls -T P4 -e java TermTester termSim 4 5 0 &
