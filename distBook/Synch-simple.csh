#/bin/csh
xterm -sb -ls -T P0 -e java SynchronizerTester synchSimple 0 5 simple &
xterm -sb -ls -T P1 -e java SynchronizerTester synchSimple 1 5 simple &
xterm -sb -ls -T P2 -e java SynchronizerTester synchSimple 2 5  simple &
xterm -sb -ls -T P3 -e java SynchronizerTester synchSimple 3 5  simple &
xterm -sb -ls -T P4 -e java SynchronizerTester synchSimple 4 5  simple &
