# Sources:
# Suggestion to use smacof library from the user guide linked at https://www.unm.edu/~wcroft/MDS.html
# Documentation and linked code from https://www.rdocumentation.org/packages/smacof/versions/2.1-7/topics/plot.smacof

install.packages('smacof')
library('smacof')
df <- read.csv("integer_dissimilarity.csv")
rownames(df) <- df[,1]
#remove first column from data frame
df <- df[,-1]

# for data in a different shape you may need 
# r <- cor(PVQ40, use = "pairwise.complete.obs")
# diss <- sim2diss(r, method = "corr") 
diss <- dist(df)

View(as.matrix(diss)) #decimal dissimilarity

# Run MDS on the distance matrix
res <- mds(diss)

plot(res, plot.type = "confplot")
plot(res, plot.type = "confplot", label.conf = list(pos = 5)) # avoid overlapping labels
plot(res, plot.type = "resplot")

#Por and Para shaped hulls
porpara <- c('a', 'por', 'por', 'b', 'c', 'd', 'e','para', 'por', 'por', 'por', 'por', 'por', 'por', 'f', 'para', 'g', 'para', 'para', 'h', 'i', 'por', 'por', 'para', 'j', 'para', 'por')
plot(res, label.conf = list(labels, pos = 5), hull.conf = list(hull = TRUE, ind = porpara, lwd = 2))
