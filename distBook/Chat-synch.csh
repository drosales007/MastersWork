#/bin/csh
xterm -sb -ls -T P0 -e java Chat chatSynch 0 5 synch &
xterm -sb -ls -T P1 -e java Chat chatSynch 1 5 synch &
xterm -sb -ls -T P2 -e java Chat chatSynch 2 5 synch &
xterm -sb -ls -T P3 -e java Chat chatSynch 3 5 synch &
xterm -sb -ls -T P4 -e java Chat chatSynch 4 5 synch &
