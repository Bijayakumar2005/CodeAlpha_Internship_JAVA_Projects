# Student Grade Management System


A robust Java application for managing student grades with both console and GUI interfaces.

## Features

📊 **Grade Management**
- Add/update student grades
- Automatic average calculation
- Identify highest/lowest scores
- Pass/Fail status determination

👨‍🎓 **Student Management**
- Add new students
- View all student records
- Search students by ID

📚 **Course Management**
- Preloaded sample courses
- Flexible course structure
- Credit hour tracking

📑 **Reporting**
- Individual student reports
- Class summary statistics
- Well-formatted console/GUI outputs

## Installation

1. **Prerequisites**:
   - Java JDK 17 or later
   - Git (optional)

# Compile all source files
javac -d bin src/*.java src/model/*.java src/view/*.java src/controller/*.java

# Run the application (GUI version)
java -cp bin Main

Usage
GUI Version (Recommended)
Launch the application

Navigate through tabs:

Student Management: Add/view students

Grade Entry: Select student and enter grades

Reports: Generate student/class reports

Sample workflow:

Add students in Student Management tab

Enter grades in Grade Entry tab

View results in Reports tab

Console Version
Uncomment console interface in Main.java

Follow the menu-driven interface

Features mirror the GUI version

Project Structure

student-grade-system/
├── src/
│   ├── model/          # Data models
│   │   ├── Student.java
│   │   ├── Course.java
│   │   └── GradeManager.java
│   ├── view/           # User interfaces
│   │   ├── ConsoleUI.java
│   │   └── GUI.java
│   ├── controller/     # Business logic
│   │   └── GradeController.java
│   └── Main.java       # Entry point
├── bin/                # Compiled classes
└── README.md
Customization
Add more courses:
Edit the initializeSampleCourses() method in Main.java

Change grading policy:
Modify the pass/fail threshold in Student.java

Extend functionality:

Add new report types

Implement data persistence

Add user authentication



Support
For questions or issues, please open an issue or contact the developer.

Developed with ❤️ by Bijaya kumar rout

Gmail :- bijayakumarrout2005@gmail.com