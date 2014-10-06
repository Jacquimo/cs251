#!/bin/bash
rm runtimes.txt
printf $'L,Selection,LSelection,Merge,LMerge,Heap,LHeap,Quick\r\n' >> runtimes.txt
for d in 5 15 25 35 45
do
############################################
# use 10^5 data to test selection sort     #
printf -v data "data/10^5/L%ddata.txt" $d #
#printf -v data "data/10^6/L%ddata.txt" $d  #
############################################
echo L = $d
for r in 1 2 3 4 5
do
printf $d, >> runtimes.txt
#######################################################################
# comment out these two lines if you are running on 10^6 or more data #
echo Selection run $r                                                 #
java -cp .:src  Sort -a selection -f $data >> runtimes.txt               #
#######################################################################
printf , >> runtimes.txt
echo LSelection run $r
java -cp .:src  Sort -a selection -f $data -l $d >> runtimes.txt
#printf , >> runtimes.txt
#echo Merge run $r
#java -cp bin Sort -a merge -f $data >> runtimes.txt
#printf , >> runtimes.txt
#echo LMerge run $r
#java -cp bin Sort -a merge -f $data -l $d >> runtimes.txt
#printf , >> runtimes.txt
#echo Heap run $r
#java -cp bin Sort -a heap -f $data >> runtimes.txt
#printf , >> runtimes.txt
#echo LHeap run $r
#java -cp bin Sort -a heap -f $data -l $d >> runtimes.txt
#printf , >> runtimes.txt
#echo Quick run $r
#java -cp bin Sort -a quick -f $data >> runtimes.txt
printf $'\r\n' >> runtimes.txt
done
done
