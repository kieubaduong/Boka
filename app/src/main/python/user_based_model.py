import numpy as np
import pandas as pd
import json
import warnings
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.preprocessing import normalize
import os

warnings.filterwarnings("ignore", category=DeprecationWarning)

def extract_user_features(ratings, df_book, book_features_v2):
    def convert_to_vector(x):
        x = x.strip().rstrip("\\n")
        return np.fromstring(x[1:-1], sep=' ')

    book_features_v2['feature'] = book_features_v2['feature'].apply(convert_to_vector)

    book_features = []
    default_features = np.zeros((768,))

    if not ratings:
        return default_features

    for isbn, rating in ratings.items():
        book_info = df_book[df_book['isbn'] == isbn]
        if not book_info.empty:
            features = book_features_v2[book_features_v2['isbn'] == isbn]['feature'].values[0] * (rating - 3)
            book_features.append(features)

    if not book_features:
        return default_features

    book_features_array = np.array(book_features)
    user_features = np.prod(book_features_array, axis=0)
    return user_features

def get_recommended_books(input_vector, df_user_features, df_rating, df_book):
    df_user_features = df_user_features.drop_duplicates(subset='feature', keep='first')
    user_features = np.array([np.fromstring(features[1:-1], dtype=float, sep=' ') for features in df_user_features['feature']])
    user_ids = df_user_features['user-id'].values

    normalized_user_features = normalize(user_features)

    normalized_input_vector = normalize(input_vector.reshape(1, -1))

    cosine_similarities = cosine_similarity(normalized_input_vector, normalized_user_features)

    sorted_indices = np.argsort(cosine_similarities)[0][::-1]
    nearest_users = user_ids[sorted_indices]

    recommended_books = []
    book_seen = set()

    for user_id in nearest_users:
        user_ratings = df_rating.loc[(df_rating['user-id'] == user_id) & (~df_rating['isbn'].isin(book_seen)), 'isbn']
        liked_books = user_ratings.tolist()

        for book_id in liked_books:
            if book_id not in book_seen and len(recommended_books) < 10:
                recommended_books.append(book_id)
                book_seen.add(book_id)

        if len(recommended_books) >= 10:
            break

    recommended_books_info = []
    for book_id in recommended_books:
        book_info = df_book[df_book['isbn'] == book_id]
        if not book_info.empty:
            book_info_dict = book_info.iloc[0]['isbn']
            recommended_books_info.append(book_info_dict)

    return recommended_books_info

def get_user_based_books(input_data):
    current_dir = os.path.dirname(os.path.abspath(__file__))
    csv_file_path = os.path.join(current_dir, 'book_features_v2.csv')
    book_features_v2 = pd.read_csv(csv_file_path)
    csv_file_path = os.path.join(current_dir, 'books.csv')
    df_book = pd.read_csv(csv_file_path)
    csv_file_path = os.path.join(current_dir, 'ratings.csv')
    df_rating = pd.read_csv(csv_file_path)
    csv_file_path = os.path.join(current_dir, 'user_features_v2.csv')
    df_user_features = pd.read_csv(csv_file_path)

    input_data = json.loads(input_data)
    output = extract_user_features(input_data, df_book, book_features_v2)
    output = get_recommended_books(output, df_user_features, df_rating, df_book)

    return json.dumps(output)