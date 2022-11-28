package com.linc.model

data class Book(
    val id: String,
    val image: String,
    val price: String,
    val averageRating: Int,
    val ratingsCount: Int,
    val subtitle: String,
    val title: String,
    val url: String
)

val mockBooks: List<Book> = listOf(
    Book(
        id = "9781491954249",
        title = "Designing Across Senses",
        subtitle = "A Multimodal Approach to Product Design",
        price = "\$27.59",
        image = "https://books.google.com/books/content?id=7JKw5FYA4GgC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
        url = "https://itbook.store/books/9781491954249",
        averageRating = 5,
        ratingsCount = 1
    ),
    Book(
        id = "9781491985571",
        title = "Web Scraping with Python, 2nd Edition",
        subtitle = "Collecting More Data from the Modern Web",
        price = "\$33.99",
        image = "https://books.google.com/books/content?id=o9jCywEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
        url = "https://itbook.store/books/9781491985571",
        averageRating = 4,
        ratingsCount = 1
    ),
    Book(
        id = "9781491954249",
        title = "Programming iOS 11",
        subtitle = "Dive Deep into Views, View Controllers, and Frameworks",
        price = "\$59.17",
        image = "https://books.google.com/books/content?id=p9oItAEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
        url = "https://itbook.store/books/9781491999226",
        averageRating = 0,
        ratingsCount = 0
    )
)