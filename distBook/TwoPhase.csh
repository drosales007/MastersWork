#/bin/csh
xterm -sb -ls -T P0 -e java TwoPhaseTester TwoPhase 0 5  &
xterm -sb -ls -T P1 -e java TwoPhaseTester TwoPhase 1 5  t &
xterm -sb -ls -T P2 -e java TwoPhaseTester TwoPhase 2 5  t  &
xterm -sb -ls -T P3 -e java TwoPhaseTester TwoPhase 3 5  f  &
xterm -sb -ls -T P4 -e java TwoPhaseTester TwoPhase 4 5  t  &
