# spanish-prepositions

Data was acquired from Josh Stephenson (https://github.com/joshstephenson/SEAS) in the form of text files (source_data)  
FindPrepositions.java converts the text files to csv file combined_all.csv  
ReadCSV.java converts the csv file to countMatrix.txt, ocMatrix.txt  
optimal-classification.r does optimal classification using ocMatrix.txt  
create_dissimilarity_matrix.ipynb converts countMatrix.txt to an integer-based dissimilarity matrix (and includes some venn diagrams for fun)  
Dissimilarity-matrix.r converts the integer dissimilarity matrix to a true dissimilarity matrix and graphs it using MDS  
