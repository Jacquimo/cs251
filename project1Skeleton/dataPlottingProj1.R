# Initialize JVM
library(rJava)
.jinit(classpath=".")

# Call Part2a multiple times. Each time, it appends to the output file
.jcall("simulation/Part2a", "V", "main", .jarray(c("../test_input.txt", "-d", 10), "java/lang/String"))