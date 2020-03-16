#!/bin/sh
sort $1 > this
sort $2 > that
resultado=$(diff this that)
if [ $? -eq 0 ]
then echo "IGUAIS"
else echo "DIFERENTES"
fi
