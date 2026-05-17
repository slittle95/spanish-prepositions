library(entropy)
library(data.table)
rm(list=ls(all=TRUE)) #  Remove all objects just to be safe
Directory_Name <- 'P:'

DATA <- 'combined_all.csv'   

data = read.csv(DATA, strip.white = TRUE)
data.sub = droplevels(data[data$code !='AMBIG' & data$code !='ID' & data$code != 'XXXX',])

df <- prop.table(xtabs(~ data.sub$code))


