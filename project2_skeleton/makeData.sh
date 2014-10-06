#!/bin/bash
#rm data.txt
for alg in $1 $2 $3 $4
do

    if [[ -z "$alg"  ]] ; then
	break
    fi

    printf -v dataFile $'%sdata.txt' $alg 
    rm $dataFile

    for locality in 1 2
    do
	if [ "$locality" = "1" ] ; then
	    printf $'%s\n' $alg >> $dataFile
	else
	    if [ "$alg" = "quick" ] ; then
		break
	    fi
	    printf $'L%s\n' $alg >> $dataFile
	fi

	printf $'Size,Locality,First Run,Second Run,Third Run,Fourth Run,Fifth Run,Average Run Time\n' >> $dataFile

	for d in 5 15 25 35 45
	do
	    size=6
	    if [ "$alg" = "selection" ] ; then
		printf -v data "data/10^5/L%ddata.txt" $d
		size=5
	    else
		printf -v data "data/10^6/L%ddata.txt" $d
	    fi

	    printf $'10^%d,%d,' $size $d  >> $dataFile

	    for run in 1 2 3 4 5
	    do
		if [ "$locality" = "1" ] ; then
		    java -cp .:src Sort -a $alg -f $data >> $dataFile
		else
		    java -cp .:src Sort -a $alg -f $data -l $d >> $dataFile
		fi
		printf , >> $dataFile
	    done
	    printf $'\n' >> $dataFile

	done
	printf $'\n\n' >> $dataFile
    done
done
