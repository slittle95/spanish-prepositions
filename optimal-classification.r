# The meat of the code I used is directly from https://www.unm.edu/~wcroft/MDS.html.
# Following the to-do list for the linguist at the top of the code, I used ocMatrix.txt for DATA.
# I made one change: replace the declaration of data.txt with:
data_orig <- read.delim(DATA,header=TRUE)
data.txt <- data_orig[, names(data_orig) != "del"]
# This is because the java code outputs a fake 'del' column that needs to be deleted.

# After running that code, I merely tacked on a few ggplots as follows:
library(dplyr)
bothocs <- result$legislators[,7:8]
library(ggplot2)

#Generates points only
jpeg(paste (title, 'Points_Fig1_ggplot.jpeg', sep="_"),width = 7, height = 7, units = 'in', res=300)
ggplot(data = bothocs, aes(x = coord1D, y = coord2D, color = "red")) + geom_jitter() + labs(x="Dimension 1", y="Dimension 2", title="OC Plot \nStimuli (Row) Ideal Points")
dev.off()

#Generates Labels Only
jpeg(paste (title, 'Points_Fig2_ggplot.jpeg', sep="_"),width = 7, height = 7, units = 'in', res=300)
ggplot(data = bothocs, aes(x = coord1D, y = coord2D)) + annotate(geom = "text", x = jitter(oc1, amount=0.05), y = jitter(oc2, amount=0.05), label = names, color="blue") + labs(x="Dimension 1", y="Dimension 2", title="OC Plot \nStimuli (Row) Ideal Points")
dev.off()

#Points and labels
jpeg(paste (title, 'Points_Fig5_ggplot.jpeg', sep="_"),width = 7, height = 7, units = 'in', res=300)
ggplot(data = bothocs, aes(x = coord1D, y = coord2D, color="red")) + geom_jitter() + annotate(geom = "text", x = jitter(oc1, amount=0.06), y = jitter(oc2, amount =0.06), label = names, color="blue") + labs(x="Dimension 1", y="Dimension 2", title="OC Plot \nStimuli (Row) Ideal Points")
dev.off()
