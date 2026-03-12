# Medicine Reminder App
## Project Overview
The Medicine Reminder App is an Android mobile application developed to help users manage their daily medications efficiently. The application allows users to register, log in securely, and maintain a personalized list of medicines.

Users can add medicines, view their medicine list, edit medicine details, delete medicines, and set reminders for taking their medicines. The system ensures that each logged-in user can only view and manage their own medicine records.

This project demonstrates Android application development using Java, SQLite database integration, authentication mechanisms, and version control using GitHub.

---

## Main Features

### User Authentication
- User Registration
- User Login
- User Logout
- Secure password storage

### Dashboard
- Central navigation screen
- Access to main application features

### Medicine Management
- Add Medicine
- View Medicine List
- Edit Medicine Details
- Delete Medicine

### Reminder System
- Users can set reminder times for medicines
- Notifications remind users when it is time to take medication

### Navigation
- Bottom navigation bar for easy navigation between screens

---

## Database Implementation

The application uses **SQLite Database** with two main tables.

### Users Table
Stores user account details.

| Field | Type |
|------|------|
| id | INTEGER (Primary Key) |
| username | TEXT |
| email | TEXT |
| password | TEXT |

### Medicines Table
Stores medicine details for each user.

| Field | Type |
|------|------|
| id | INTEGER (Primary Key) |
| user_id | INTEGER (Foreign Key) |
| name | TEXT |
| dosage | TEXT |
| description | TEXT |
| reminder_time | TEXT |

Each medicine record is linked to a specific user using the **user_id** field.

---

## Technologies Used

- Android Studio
- Java
- SQLite Database
- XML Layout Design
- Git & GitHub Version Control
- Android Notifications

---

## Project Structure

Main screens implemented in the application:

- Login Screen
- Registration Screen
- Dashboard Screen
- Add Medicine Screen
- Medicine List Screen
- Edit/Delete Medicine Screen
- Reminder Setup

---

## How to Run the Project

1. Clone the GitHub repository.
2. Open the project in **Android Studio**.
3. Allow Gradle to sync.
4. Run the application on an **Android Emulator or Physical Android Device**.

---

## Team Members

| Name | Registration No | Index No | Contribution |
|-----|-----|-----|-----|
| S.D. Yatiwella | ICT/2022/010 | 5618 | User Authentication (Add Medicine screen, Reminder setup functionality|
| M.T. Rathnayake | ICT/2022/114 | 5716 | User Authentication (Login, Registration), Session handling |
| J.M.D.T. Abesingha | ICT/2022/116 | 5718 | Dashboard development, View/Edit/Delete Medicine screen, Navigation bar implementation |

---

## Learning Outcomes

This project demonstrates:

- Android Activity development
- SQLite database integration
- CRUD operations
- User authentication
- Notification-based reminder system
- GitHub collaboration and version control

---
