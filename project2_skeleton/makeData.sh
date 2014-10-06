#!/bin/bash
rm data.txt
for alg in heap
do

    for locality in 1 2
    do
	if [ "$locality" = "1" ] ; then
	    printf $'%s\n' $alg >> data.txt
	else
	    printf $'L%s\n' $alg >> data.txt
	fi

	printf $'Size,Locality,First Run,Second Run,Third Run,Fourth Run,Fifth Run,Average Run Time\n' >> data.txt

	for d in 5 15 25 35 45
	do
	    size=6
	    if [ "$alg" = "selection" ] ; then
		printf -v data "data/10^5/L%ddata.txt" $d
		size=5
	    else
		printf -v data "data/10^6/L%ddata.txt" $d
	    fi

	    printf $'10^%d,%d,' $size $d  >> data.txt

	    for run in 1 2 3 4 5
	    do
		if [ "$locality" = "1" ] ; then
		    java -cp .:src Sort -a $alg -f $data >> data.txt
		else
		    java -cp .:src Sort -a $alg -f $data -l $d >> data.txt
		fi
		printf , >> data.txt
	    done
	    printf $'\n' >> data.txt

	done
	printf $'\n\n' >> data.txt
    done
done
