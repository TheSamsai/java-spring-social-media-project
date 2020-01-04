# Lärvipamfletti, simple social media site made with Java Spring

## About

This project is a Java Spring web application which aims to be a simple social
media website. It was originally written for University of Helsinki's "Web Server
Programming in Java" course as a course project. It has been slightly modified
since to use Gradle instead of Maven and its dependencies have been updated from
the original project template.

The application allows users to register new profiles, log into them, post
messages on their walls and upload images into their personal photo albums, from
which a profile picture can also be selected. It also allows users to find other
users, send friend requests and friends can post on each other's walls and
comment on each other's posts.

## Running

The application can be started using Gradle:
```bash
    $ gradle bootRun
```

The application will then be available via http://localhost:8080

The application uses a local H2 database file to store application data. This
file will be located in the project folder as "database.mv.db". It can be
deleted after the application has been stopped to reset the data. A new database
file will be created when the program is executed next time.
