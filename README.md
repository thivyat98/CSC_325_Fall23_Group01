## Introduction

The JobGrabber application is designed to simplify the job search and recruitment process. It facilitates the interaction between job seekers and employers, allowing users to create profiles, search for jobs, and post job listings.

## Features

- **User Authentication:** Secure user authentication using Firebase for a seamless login and registration process.
- **User Profiles:** Personalized profiles for both job seekers and employers, containing relevant information.
- **Job Postings:** Employers can post job listings with details such as job title, location, salary, and description.
- **Job Search:** Job seekers can search for available jobs based on keywords, location, and other criteria.
- **Application Tracking:** Employers can view and manage job applications received for their posted jobs.
- **User Sessions:** Efficient management of user sessions to provide a personalized experience.

## Technologies Used

- **Java:** The core programming language used for the application.
- **JavaFX:** A Java library for creating rich graphical user interfaces.
- **Firebase:** Used for user authentication and Firestore for storing and retrieving data.
- **Google Cloud Firestore:** A NoSQL cloud database for storing user profiles, job postings, and application data.
- **Spring Security:** Utilized for password hashing and securing user authentication.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK):**
  - Ensure that you have JDK 8 or later installed on your system.
  - Download the latest version of JDK from [Oracle's website](https://www.oracle.com/java/technologies/javase-downloads.html) or use an open-source distribution like [OpenJDK](https://openjdk.java.net/).

- **JavaFX:**
  - JavaFX is used for the graphical user interface (GUI) of the application.
  - Download and install JavaFX from [Gluon's website](https://gluonhq.com/products/javafx/) or use a package manager like [Maven](https://openjfx.io/openjfx-docs/#maven) or [Gradle](https://openjfx.io/openjfx-docs/#gradle).

- **Firebase Account:**
  - The application uses Firebase for authentication and data storage.
  - Create a Firebase project and obtain the configuration file (`privatekey.json`) for your project.

### Installation

1. **Clone the Repository:**
   - Clone this repository to your local machine using the following command:
     ```bash
     git clone https://github.com/your-username/job-finder.git
     cd job-finder
     ```

2. **Import Project:**
   - Open your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
   - Import the project by selecting the root directory of the cloned repository.

3. **Configure Firebase:**
   - Replace the placeholder `privatekey.json` in the `src/main/resources/com/example/csc325/csc325/` directory with your Firebase project's configuration file.

4. **Configure JavaFX:**
   - Ensure you have JavaFX configured in your development environment.

5. **Build and Run:**
   - Build and run the application using your preferred Java IDE.

6. **Explore the Application:**
   - Navigate through the application, create profiles, and test the job posting and search functionalities.

### Project Structure

- `src/com/example/csc325/csc325/` contains the Java source code organized into packages.
  - `Controllers`: Contains JavaFX controllers for different application scenes.
  - `Posts`: Classes for different types of posts (e.g., job postings).
  - `Users`: Classes representing different user types (e.g., Employee, Employer).
  - `UserSessionManager`: Manages user sessions and authentication.

Stay tuned for updates and improvements to the application. Happy job hunting!


## Usage

1. **Login or Register:**
   - Users can log in with existing accounts or register as new users. Users may choose an Employee Account or an Employer Account depending on their needs.

2. **Create Profile:**
   - Job seekers and employers can create detailed profiles with relevant information.

3. **Job Search:**
   - Job seekers can search for available jobs based on various criteria.

4. **Post a Job:**
   - Employers can post job listings, providing necessary details.

5. **Application Tracking:**
   - Employers can track and manage job applications received for their posted jobs.

## Contributing

Contributions to the Job Finder application are welcome! Feel free to open issues for bug reports or feature requests.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- Special thanks to the Java and JavaFX communities for their valuable resources and documentation.

## Disclaimer

This project was developed as part of a coursework assignment and may not be actively maintained or updated. Feel free to fork and modify it for your own use.

## Project Structure

- `src/com/example/csc325/csc325/` contains the Java source code organized into packages.
  - `Controllers`: Contains JavaFX controllers for different application scenes.
  - `Posts`: Classes for different types of posts (e.g., job postings).
  - `Users`: Classes representing different user types (e.g., Employee, Employer).
  - `UserSessionManager`: Manages user sessions and authentication.

Stay tuned for updates and improvements to the application. Happy job hunting!
