import pandas as pd
import csv

def whats_the_difference(column1, column2):
  i = row_names_per_column[column1].difference(row_names_per_column[column2])
  j = row_names_per_column[column2].difference(row_names_per_column[column1])
  """
  print("column1 =", column1, " column2 =", column2)
  print("row names per column 1")
  print(row_names_per_column[column1])
  print("row names per column 2")
  print(row_names_per_column[column2])
  print(i)
  print(j)
  """
  difference = i.union(j)
  #print(difference)
  #the literal set definition of difference--what prepositions make these two functions different?
  return len(difference)

m = pd.read_csv("countMatrix.txt", sep='\t', index_col=0)[:]
m = m.drop(columns=['del']) #'del' is on purpose for deleting
m = m.transpose()

# Sanity check:
# Get column names
column_names = m.columns.tolist()
print("Column Names:")
print(column_names)
# Get row names (index)
row_names = m.index.tolist()
print("\nRow Names (Index):")
print(row_names)


row_names_per_column = []

for col in m.columns:
    # Get row names where the value in the current column is greater than 0
    relevant_row_names = m[m[col] > 0].index.tolist()
    # Convert the list of row names to a set and append to the result list
    # Every semantic function gets a set of prepositions
    row_names_per_column.append(set(relevant_row_names))

length = len(row_names_per_column)
for i in range(length):
  print(i, column_names[i], row_names_per_column[i],'\n')

# Recreate original matrix as a difference matrix where every semantic function is
# compared to every other semantic function via how many prepositions one has that
# the other does not
outer_list = []
samesies = []
print(column_names)
for i in range(len(column_names)):
  inner_list = []
  print(column_names[i])
  for j in range(len(column_names)):
    a = whats_the_difference(i, j)
    inner_list.append(a)
    if (a==0 and column_names[i] != column_names[j] and (column_names[j], column_names[i]) not in samesies):
      samesies.append((column_names[i], column_names[j]))
  outer_list.append(inner_list)
  print(inner_list)

print("samesies", samesies)

with open('integer_dissimilarity.csv', 'w', newline='') as csvfile:
    csvwriter = csv.writer(csvfile)
    csvwriter.writerow(column_names)
    for i in range(len(column_names)):
      inner_list = []
      for j in range(len(column_names)):
        a = whats_the_difference(i, j)
        inner_list.append(a)
      csvwriter.writerow(inner_list)

col_names_per_row = []

for row in m.index:
    # Get column names where the value in the current row is greater than 0
    relevant_column_names = m.loc[row, m.loc[row] > 0].index
    col_names_per_row.append(set(relevant_column_names))

length = len(col_names_per_row)
for i in range(length):
  print(i, row_names[i], col_names_per_row[i],'\n')
