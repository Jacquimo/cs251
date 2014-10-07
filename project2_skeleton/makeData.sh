#!/bin/bash
#rm data.txt
for alg in $1 $2 $3 $4
do

    if [[ -z "$alg"  ]] ; then
	break
    fi

    printf -v dataFile $'%sdata.txt' $alg 
    #dataFile="data.txt"
    rm $dataFile

    #printf $'L%s\n' $alg >> $dataFile
    #printf $'Size,Locality,First Run,Second Run,Third Run,Fourth Run,Fifth Run,Average Run Time\n' >> $dataFile

    #for size in 3 5 6
    #do

	for locality in 2
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
		    size=5
		    #printf $'%d\n' $size
		    printf -v data "data/10^%d/L%ddata.txt" $size  $d
		else
		    printf -v data "data/10^%d/L%ddata.txt" $size  $d
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
	    printf $'\n' >> $dataFile
	done
	printf $'\n\n' >> $dataFile
    #done
    #printf $'\n\n' >> $dataFile
done
