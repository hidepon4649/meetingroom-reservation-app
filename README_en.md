# Meeting Room Reservation System

## Overview

This application is a meeting room reservation system built using Spring Boot, Thymeleaf, and RDB (MySQL).

It is a simple web application for managing internal meeting room reservations.  
The screen layout switches between PC and smartphone displays.

Main Features:

- Login
- Meeting Room Reservation
  - Create Reservation
  - Update Reservation
  - Cancel Reservation
  - Display Reservation Calendar
- Administration Features (for admin users only)
  - Register User
  - Update User
  - Delete User
  - Register Meeting Room
  - Update Meeting Room
  - Delete Meeting Room

It implements the following elements that are frequently used in real-world development environments,  
making it well-suited as an assignment program for new employee training:

- Authentication and Authorization
- Validation of data entered from the screen
- Create/Update/Delete operations on the database
- Image Upload
- File Upload
- Calendar UI

## Technologies Used

- **Frontend**: Thymeleaf, Bootstrap
- **Backend**: Spring Boot, Gradle
- **Database**: MySQL
- **Infrastructure**: Docker (containerization), Docker Compose

## Setup Instructions

```bash
# Clone the repository
git clone https://github.com/hidepon4649/meetingroom-reservation-app.git

# Move to the docker-compose directory and execute the following two commands:
docker compose build
docker compose up -d

# Access the following URL from your browser:
http://localhost:8080/login

# Admin user id/pw
admin@example.com/password

# General user id/pw
user1@example.com/password
