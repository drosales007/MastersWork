#/bin/csh
xterm -sb -ls -T P0 -e java CameraTester CamSend 0 5 SenderCamera &
xterm -sb -ls -T P1 -e java CameraTester CamSend 1 5 SenderCamera &
xterm -sb -ls -T P2 -e java CameraTester CamSend 2 5 SenderCamera &
xterm -sb -ls -T P3 -e java CameraTester CamSend 3 5 SenderCamera &
xterm -sb -ls -T P4 -e java CameraTester CamSend 4 5 SenderCamera &
