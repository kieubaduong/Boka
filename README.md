# Book Recommendation App

This is an Android application built using Jetpack Compose and follows the principles of Clean Architecture. The app provides a user-friendly interface for browsing and discovering books, as well as personalized book recommendations based on user preferences.

## Features

- User registration and authentication
- Book search and browsing
- Personalized book recommendations using content-based and collaborative filtering algorithm
- Book details with images, tags, descriptions, ratings
- Bookshelf for managing favorite books

## Technologies Used

- Android Jetpack Compose
- Modern app architecture
- Chaquopy (Python SDK)
- [Book Recommendation Algorithm](https://github.com/kieubaduong/Book-Recommendation-Algorithms)

## Project Structure

```
├── core
├── data
│   ├── model
│   ├── network
│   │   ├── api
│   │   ├── auth
│   │   │   ├── body
│   │   │   └── result
│   └── repository
├── navigation
├── ui
│   ├── MainActivity.kt
│   ├── features
│   ├── common
│   └── theme
└── util
```

The project follows the Clean Architecture principles, which separates the codebase into layers:

- `core`: Contains all type of class using across the application.
- `data`: Implements the data access layer + domain layer, including remote data sources, repositories and the business logic.
  - `model`: Contains data class for the ui consume.
  - `body`: Contains data class for the body HTTP request.
  - `result`: Contains data class for the body HTTP result.
- `ui`: Implements the presentation layer, including view models and UI screens.
- `navigation`: Implements the navigation graph.
- `util`: Contains helper method and extensions.

## Screenshots

|                                       |                                        |                                         |
| :-----------------------------------: | :------------------------------------: | :-------------------------------------: |
|             Splash Screen             |                Sign In                 |               Home Screen               |
|  ![](.github/screenshots/splash.jpg)  |   ![](.github/screenshots/login.jpg)   |  ![](.github/screenshots/home-page.jpg) |
|               Book Detail             |              Rated Books               |                 Saved Books                  |
|![](.github/screenshots/book-detail.jpg) |  ![](.github/screenshots/rated-books.jpg)   | ![](.github/screenshots/favorite-book.jpg) |
|               Profile                         
|   ![](.github/screenshots/profile.jpg)     

## Prerequisites

- Android Studio Hedgehog Canary 1 (2023.1.1.1) or higher
- Kotlin 1.8.10 or higher
- Android minSdk 30
- Python 3.8

### Todo

- Dependency injection

## Contributors✨

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/kieubaduong">
        <img src="https://avatars.githubusercontent.com/u/75083331?v=4" width="100px;" alt=""/>
        <br />
        <sub><b>Kieu Ba Duong</b></sub>
      </a>
      <br />
      <sub>Mobile developer</sub>
      <br />
      <sub>ML researcher</sub>
    </td>
  </tr>
</table>

