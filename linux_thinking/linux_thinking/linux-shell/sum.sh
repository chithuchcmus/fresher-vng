#!/bin/bash
sum=0
file="$1"
for i in $(cat < $1)
do
	sum=$(( $sum + $i ))
done

echo $sum