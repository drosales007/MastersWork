#/bin/csh
xterm -sb -ls -T P0 -e java SensorTester std 0 5 1 &
xterm -sb -ls -T P1 -e java SensorTester std 1 5 1 &
xterm -sb -ls -T P2 -e java SensorTester std 2 5 1 &
xterm -sb -ls -T P3 -e java SensorTester std 3 5 1 &
xterm -sb -ls -T P4 -e java SensorTester std 4 5 1 &
