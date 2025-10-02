# Kerago Hotel Booking System - Backend

A comprehensive Spring Boot REST API for a hotel booking system that provides functionality for hotel management, room booking, user authentication, and more.

## 🚀 Features

### Core Functionality
- **Hotel Management**: Register, view, update, and delete hotels
- **Room Management**: Multiple room types (Luxury, Deluxe, Normal, Family) with availability tracking
- **User Management**: User registration, authentication, and profile management
- **Booking System**: Create bookings, manage availability, and cancel reservations
- **Image Management**: Upload and manage hotel images via Cloudinary integration
- **Email Notifications**: Automated booking confirmation emails with Thymeleaf templates

### Security & Authentication
- **JWT Authentication**: Secure token-based authentication system
- **Role-based Access Control**: Support for Admin, User, and Hotel roles
- **Spring Security**: Comprehensive security configuration with CORS support
- **Password Encryption**: BCrypt password hashing

### Additional Features
- **API Documentation**: Integrated Swagger/OpenAPI documentation
- **Email Templates**: HTML email templates for booking confirmations
- **Database Integration**: PostgreSQL with JPA/Hibernate
- **Input Validation**: Comprehensive request validation
- **Exception Handling**: Global exception handling with custom error responses

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.5
- **Language**: Java 24
- **Database**: PostgreSQL
- **ORM**: Hibernate/JPA
- **Security**: Spring Security + JWT
- **Build Tool**: Gradle
- **Email**: Spring Mail with Thymeleaf templates
- **Cloud Storage**: Cloudinary for image management
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Additional Libraries**: 
  - Lombok for boilerplate reduction
  - Jackson for JSON processing
  - Validation API for input validation

## 📋 Prerequisites

- Java 24 or higher
- PostgreSQL database
- Gradle 7.x or higher
- Cloudinary account (for image uploads)
- Gmail account (for email notifications)

## ⚙️ Configuration

### Database Setup
Create a PostgreSQL database named `keragodb` and update the connection details in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/keragodb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Email Configuration
Configure your Gmail credentials for email notifications:

```properties
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
```

### Cloudinary Setup
Add your Cloudinary configuration in the `CloudinaryConfig.java` file or environment variables.

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd keragoBackend
```

### 2. Configure Application Properties
Update `src/main/resources/application.properties` with your database and email credentials.

### 3. Build the Project
```bash
./gradlew build
```

### 4. Run the Application
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

Once the application is running, access the Swagger UI documentation at:
```
http://localhost:8080/swagger-ui/index.html
```

## 🔗 API Endpoints

### Authentication
- `POST /v1/auth/login` - User login

### Users
- `POST /v1/users/register` - User registration
- `GET /v1/users` - Get all users (authenticated)
- `GET /v1/users/{id}` - Get user by ID (authenticated)
- `PUT /v1/users/{id}` - Update user (authenticated)
- `DELETE /v1/users/{id}` - Delete user (authenticated)

### Hotels
- `POST /v1/hotels/register` - Register new hotel (with image upload)
- `GET /v1/hotels` - Get all hotels (public)
- `GET /v1/hotels/{id}` - Get hotel by ID (public)
- `PUT /v1/hotels/{id}` - Update hotel (authenticated)
- `DELETE /v1/hotels/{id}` - Delete hotel (authenticated)

### Bookings
- `POST /v1/booking` - Create new booking (authenticated)
- `PUT /v1/booking/cancel/{bookingId}` - Cancel booking (authenticated)

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/org/kerago/keragobackend/
│   │   ├── config/           # Configuration classes
│   │   ├── controller/       # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── enums/           # Enum definitions
│   │   ├── exception/       # Exception handling
│   │   ├── model/           # JPA entities
│   │   ├── repository/      # Data access layer
│   │   ├── security/        # Security utilities
│   │   └── service/         # Business logic
│   └── resources/
│       ├── application.properties
│       ├── static/          # Static resources
│       └── templates/       # Email templates
└── test/                    # Test classes
```

## 🏨 Data Models

### Core Entities
- **Users**: User accounts with role-based access
- **Hotels**: Hotel information with location and description
- **Rooms**: Room inventory with types and pricing
- **Bookings**: Reservation records with status tracking
- **Images**: Hotel image management

### Relationships
- One Hotel → Many Rooms
- One Hotel → Many Bookings
- One Hotel → Many Images
- One User → Many Bookings
- One Booking → Many Rooms

## 🔒 Security Features

- JWT token-based authentication
- Role-based authorization (ADMIN, USER, HOTEL)
- CORS configuration for frontend integration
- Secure password storage with BCrypt
- Protected endpoints based on user roles

## 📧 Email System

The system includes automated email notifications for:
- Booking confirmations with detailed information
- HTML templates using Thymeleaf
- Responsive email design

## 🌟 Room Types

The system supports four room categories:
- **LUXURY**: Premium rooms with highest pricing
- **DELUXE**: High-end rooms with enhanced amenities
- **NORMAL**: Standard rooms for regular stays
- **FAMILY**: Spacious rooms designed for families

## 📊 Booking Status

Bookings can have the following statuses:
- **PENDING**: Initial booking state
- **CONFIRMED**: Booking approved and confirmed
- **CANCELLED**: Booking cancelled by user or admin

## 🔧 Development

### Running Tests
```bash
./gradlew test
```

### Building for Production
```bash
./gradlew build
```

## 📝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Support

For support and questions, please contact the development team or create an issue in the repository.

---

**Note**: Make sure to update all sensitive configuration values (database credentials, email passwords, API keys) before deploying to production. Consider using environment variables or secure configuration management for production deployments.
