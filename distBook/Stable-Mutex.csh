#/bin/csh
xterm -sb -ls -T P0 -e java StableMutexTester stabMutex 0 5  &
xterm -sb -ls -T P1 -e java StableMutexTester stabMutex 1 5  &
xterm -sb -ls -T P2 -e java StableMutexTester stabMutex 2 5   &
xterm -sb -ls -T P3 -e java StableMutexTester stabMutex 3 5   &
xterm -sb -ls -T P4 -e java StableMutexTester stabMutex 4 5   &
