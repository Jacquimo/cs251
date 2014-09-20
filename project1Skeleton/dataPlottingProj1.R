# Go to proper working directory
setwd("~/../Desktop/Fall2014/CS 251/Projects/project1Skeleton/project1_skeleton/src")
library(ggplot2)

# Initialize JVM
library(rJava)
.jinit(classpath=".")

# Erase all the data that was in the output file and replace it with the headers
headers <- c("Level", "MeanWait", "MaxWait", "NumDocs", "Busyness")
write(headers, file = "../2aOutput.txt", ncolumns = 5, sep = "\t")

# Call Part2a multiple times. Each time, it appends to the output file
for (i in 1:100) {
  .jcall("simulation/Part2a", "V", "main", .jarray(c("../test_input.txt", "-d", i), "java/lang/String"))
}

# Retrieve the output of all of the runs of the Part2a simulation
data <- read.table("../2aOutput.txt", header= TRUE, sep="\t")
mean <- data[which(data$Level == 1),]$MeanWait
max <- data$MaxWait
docs <- data[which(data$Level == 1),]$NumDocs
busy <- data$Busyness

# f <- function(x,a,b,c) {a + b * x^(-c)}
# starting <- coef(nls(log(mean) ~ log(f(docs, a, b, c)), data.frame(docs, mean), start = c(a = 1, b = 1, c=0.2)))

# plot(docs, mean, xlab="Number of Doctors", ylab="Mean Wait Time (min)", main="Number of Doctors vs Mean Wait Time")
# nls_fit <- nls(mean ~ f(docs, a, b, c), data=data.frame(docs, mean), start=starting)
# lines(docs, predict(nls_fit), col="red")
# ggplot(data.frame(docs, mean), aes(docs, mean)) + geom_point() + geom_smooth(method="lm")
# ggplot(data.frame(docs, mean),aes(x = docs,y = mean)) +
#   geom_point() + 
#   stat_smooth(method = 'nls', formula = 'y~a*x^b', start = list(a = 1,b=1),se=FALSE)

d <- data.frame(docs, mean)
model <- lm(log(mean) ~ log(docs), data=d)
newData <- data.frame(docs=seq(min(d$docs), max(d$docs), len=100))
plot(mean ~ docs, data=d)
lines(newData$docs, exp(predict(model, newData)))
model