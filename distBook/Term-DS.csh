#/bin/csh
xterm -sb -ls -T P0 -e java TermTester termDS 0 5 1 &
xterm -sb -ls -T P1 -e java TermTester termDS 1 5 1 &
xterm -sb -ls -T P2 -e java TermTester termDS 2 5 1 &
xterm -sb -ls -T P3 -e java TermTester termDS 3 5 1 &
xterm -sb -ls -T P4 -e java TermTester termDS 4 5 1 &
