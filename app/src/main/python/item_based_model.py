import numpy as np
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
import os
def main(isbn):
  current_dir = os.path.dirname(os.path.abspath(__file__))
  csv_file_path = os.path.join(current_dir, 'pivot_table.csv')
  pivot_table = pd.read_csv(csv_file_path, dtype=np.int8)
  target_column = pivot_table[isbn]

  other_columns = pivot_table.drop(columns=isbn)
  similarities = cosine_similarity(target_column.values.reshape(1, -1), other_columns.values.T)
  similarities_series = pd.Series(similarities[0], index=other_columns.columns)
  top_10_books = similarities_series.sort_values(ascending=False).head(10)

  book_list = []
  for id, similarity_score in top_10_books.items():
    book_list.append(id)

  return book_list

isbn = '0002190915'
main(isbn)