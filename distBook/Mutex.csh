#/bin/csh

# $1 is the name of the mutex algorithm class
xterm -sb -ls -T P0 -e java LockTester Mutex 0 3 $1 &
xterm -sb -ls -T P1 -e java LockTester Mutex 1 3 $1 &
xterm -sb -ls -T P2 -e java LockTester Mutex 2 3 $1 &
