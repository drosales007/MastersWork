#/bin/csh
xterm -sb -ls -T P0 -e java StableTreeTester stabTree 0 5  &
xterm -sb -ls -T P1 -e java StableTreeTester stabTree 1 5  &
xterm -sb -ls -T P2 -e java StableTreeTester stabTree 2 5   &
xterm -sb -ls -T P3 -e java StableTreeTester stabTree 3 5   &
xterm -sb -ls -T P4 -e java StableTreeTester stabTree 4 5   &
