use SMS;

show tables;
table Class;
create table Admin
(
    Admin_ID  varchar(10)  not null
        primary key,
    User_name varchar(10)  not null unique ,
    Password  varchar(10)  not null,
    AdminType varchar(255) not null
);

INSERT INTO SMS.Admin (Admin_ID, User_name, Password, AdminType)
VALUES ('A001', '123', '123', 'SuperAdmin');
INSERT INTO SMS.Admin (Admin_ID, User_name, Password, AdminType)
VALUES ('A002', 'Yasiru', '000', 'SuperAdmin');
INSERT INTO SMS.Admin (Admin_ID, User_name, Password, AdminType)
VALUES ('A004', 'mgs', '24', 'Admin');
INSERT INTO SMS.Admin (Admin_ID, User_name, Password, AdminType)
VALUES ('A006', 'Isuru', '********', 'SuperAdmin');
INSERT INTO SMS.Admin (Admin_ID, User_name, Password, AdminType)
VALUES ('A007', 'df', '********', 'SuperAdmin');
INSERT INTO SMS.Admin (Admin_ID, User_name, Password, AdminType)
VALUES ('A008', 'fg', '12345', 'Admin');
INSERT INTO SMS.Admin (Admin_ID, User_name, Password, AdminType)
VALUES ('A009', 'sudeera', '123', 'SuperAdmin');
INSERT INTO SMS.Admin (Admin_ID, User_name, Password, AdminType)
VALUES ('A010', 'sudarshani', '1988', 'ADMIN');


create table Attendance_Stu
(
    Attend_ID  varchar(10) not null
        primary key,
    Date       date        not null,
    Admin_ID   varchar(10) null,
    Student_ID varchar(10) null,
    Class_ID   varchar(20) null,
    Status     tinyint(1)  not null,
    constraint Attendance_Stu_ibfk_1
        foreign key (Admin_ID) references Admin (Admin_ID),
    constraint Attendance_Stu_ibfk_2
        foreign key (Student_ID) references Student (Student_ID)
            on delete cascade
);

create index Admin_ID
    on Attendance_Stu (Admin_ID);

INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT003', '2025-05-14', 'A001', 'S005', 'C003', 0);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT004', '2025-05-16', 'A001', 'S005', 'C003', 0);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT006', '2025-05-16', 'A001', 'S005', 'C003', 1);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT007', '2025-05-16', 'A001', 'S005', 'C002', 1);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT008', '2025-05-18', 'A001', 'S005', 'C002', 1);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT009', '2025-05-18', 'A001', 'S005', 'C002', 1);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT014', '2025-05-28', 'A001', 'S004', 'C003', 0);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT019', '2025-06-03', 'A001', 'S005', 'C002', 1);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT020', '2025-06-03', 'A001', 'S010', 'C002', 1);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT021', '2025-06-04', 'A001', 'S004', 'C003', 1);
INSERT INTO SMS.Attendance_Stu (Attend_ID, Date, Admin_ID, Student_ID, Class_ID, Status) VALUES ('AT022', '2025-06-06', 'A001', 'S004', 'C011', 1);


create table Attendance_Tea
(
    Attend_ID  varchar(10) not null
        primary key,
    Date       date        not null,
    Admin_ID   varchar(10) null,
    Teacher_ID varchar(10) null,
    Status     tinyint(1)  not null,
    Class_ID   varchar(20) null,
    constraint Attendance_Tea_ibfk_1
        foreign key (Admin_ID) references Admin (Admin_ID)
);

create index Admin_ID
    on Attendance_Tea (Admin_ID);

create index Teacher_ID
    on Attendance_Tea (Teacher_ID);

INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT006', '2025-05-22', 'A001', 'T005', 1, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT008', '2025-05-22', 'A001', 'T005', 1, 'C012');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT009', '2025-05-22', 'A001', 'T006', 0, 'C001');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT010', '2025-05-22', 'A001', 'T006', 1, 'C003');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT011', '2025-05-22', 'A001', 'T006', 1, 'C012');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT012', '2025-05-22', 'A001', 'T005', 1, 'C009');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT013', '2025-05-22', 'A001', 'T005', 1, 'C003');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT014', '2025-05-22', 'A001', 'T005', 0, 'C024');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT015', '2025-05-22', 'A001', 'T006', 1, 'C024');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT016', '2025-05-22', 'A001', 'T006', 1, 'C011');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT017', '2025-05-22', 'A001', 'T006', 1, 'C022');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT018', '2025-05-27', 'A001', 'T003', 1, 'C008');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT019', '2025-05-27', 'A001', 'T007', 1, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT020', '2025-05-27', 'A001', 'T008', 1, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT021', '2025-05-27', 'A001', 'T031', 1, 'C012');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT022', '2025-05-28', 'A001', 'T020', 0, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT023', '2025-05-28', 'A001', 'T004', 0, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT024', '2025-05-28', 'A001', 'T009', 0, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT025', '2025-05-28', 'A001', 'T010', 0, 'C009');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT026', '2025-05-28', 'A001', 'T021', 0, 'C011');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT027', '2025-05-28', 'A001', 'T022', 0, 'C012');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT030', '2025-05-30', 'A001', 'T025', 0, 'C008');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT031', '2025-05-30', 'A001', 'T026', 0, 'C003');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT032', '2025-06-01', 'A001', 'T029', 0, 'C010');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT033', '2025-06-01', 'A001', 'T030', 0, 'C001');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT036', '2025-06-03', 'A001', 'T003', 0, 'C009');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT037', '2025-06-03', 'A001', 'T007', 0, 'C008');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT038', '2025-06-03', 'A001', 'T008', 0, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT039', '2025-06-03', 'A001', 'T031', 1, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT040', '2025-06-04', 'A001', 'T020', 0, 'C008');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT041', '2025-06-04', 'A001', 'T004', 0, 'C009');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT042', '2025-06-04', 'A001', 'T009', 0, 'C008');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT043', '2025-06-04', 'A001', 'T010', 0, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT044', '2025-06-04', 'A001', 'T021', 0, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT045', '2025-06-04', 'A001', 'T022', 1, 'C002');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT046', '2025-06-05', 'A001', 'T005', 0, 'C021');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT047', '2025-06-05', 'A001', 'T012', 1, 'C019');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT048', '2025-06-05', 'A001', 'T018', 0, 'C019');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT049', '2025-06-05', 'A001', 'T023', 0, 'C019');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT050', '2025-06-05', 'A001', 'T013', 0, 'C020');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT051', '2025-06-05', 'A001', 'T015', 1, 'C021');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT052', '2025-06-05', 'A001', 'T006', 1, 'C016');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT053', '2025-06-05', 'A001', 'T017', 1, 'C010');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT054', '2025-06-05', 'A001', 'T019', 1, 'C024');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT055', '2025-06-05', 'A001', 'T024', 1, 'C013');
INSERT INTO SMS.Attendance_Tea (Attend_ID, Date, Admin_ID, Teacher_ID, Status, Class_ID) VALUES ('AT057', '2025-06-06', 'A001', 'T025', 1, 'C017');

create table Class
(
    Class_ID      varchar(10) not null
        primary key,
    Grade         int         not null,
    Time_Table_ID varchar(10) null,
    Subject_ID    varchar(10) null
);

create index Time_Table_ID
    on Class (Time_Table_ID);

INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C002', 10, 'T005', 'S004');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C003', 11, 'T009', 'S005');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C004', 11, 'T013', 'S007');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C005', 12, 'T017', 'S010');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C006', 11, 'T011', 'S001');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C007', 12, 'T013', 'S005');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C008', 10, 'T001', 'S001');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C009', 11, 'T002', 'S014');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C010', 11, 'T005', 'S001');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C011', 12, 'T006', 'S016');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C012', 10, 'T008', 'S002');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C013', 11, 'T009', 'S004');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C014', 12, 'T010', 'S010');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C015', 10, 'T011', 'S011');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C016', 11, 'T013', 'S020');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C017', 12, 'T014', 'S017');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C018', 10, 'T016', 'S007');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C019', 11, 'T017', 'S019');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C020', 12, 'T018', 'S008');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C021', 10, 'T020', 'S013');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C022', 11, 'T022', 'S005');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C023', 9, 'T009', 'S001');
INSERT INTO SMS.Class (Class_ID, Grade, Time_Table_ID, Subject_ID) VALUES ('C024', 4, 'T008', 'S001');


create table Exam
(
    Exam_ID    varchar(10) not null
        primary key,
    Exam_Date  date        not null,
    Marks      int         null,
    Subject_ID varchar(10) null,
    Teacher_ID varchar(10) null,
    Student_ID varchar(10) null
);

INSERT INTO SMS.Exam (Exam_ID, Exam_Date, Marks, Subject_ID, Teacher_ID, Student_ID) VALUES ('EX003', '2024-05-01', 90, 'S004', 'T002', 'S005');
INSERT INTO SMS.Exam (Exam_ID, Exam_Date, Marks, Subject_ID, Teacher_ID, Student_ID) VALUES ('EX004', '2024-05-01', 88, 'S004', 'T002', 'S005');
INSERT INTO SMS.Exam (Exam_ID, Exam_Date, Marks, Subject_ID, Teacher_ID, Student_ID) VALUES ('EX005', '2025-06-27', 20, 'S005', 'T002', 'S001');
INSERT INTO SMS.Exam (Exam_ID, Exam_Date, Marks, Subject_ID, Teacher_ID, Student_ID) VALUES ('EX006', '2025-06-21', 50, 'S002', 'T003', 'S001');

table Student;

create table Student
(
    Student_ID varchar(10) not null
        primary key,
    Tel_No     int         not null,
    Class_ID   varchar(10) null,
    Name       varchar(20) not null,
    Grade      int         not null,
    Address    varchar(20) not null,
        unique (Tel_No),
        foreign key (Class_ID) references Class (Class_ID)
);

INSERT INTO SMS.Student (Student_ID, Tel_No, Class_ID, Name, Grade, Address) VALUES ('S004', 774567890, 'C003', 'Tharindu Jayasuriya', 12, 'Matara');
INSERT INTO SMS.Student (Student_ID, Tel_No, Class_ID, Name, Grade, Address) VALUES ('S005', 775678901, 'C002', 'Hasini Wijesinghe', 11, 'Negombo');
INSERT INTO SMS.Student (Student_ID, Tel_No, Class_ID, Name, Grade, Address) VALUES ('S010', 773456789, 'C002', 'Kamal Fernando', 11, 'Kandy');


show tables ;
table Teacher;

create table Subject
(
    Subject_ID varchar(10) not null
        primary key,
    Name       varchar(30) null
);

INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S001', 'Mathematics');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S002', 'Science');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S004', 'History');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S005', 'Geography');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S007', 'Art');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S008', 'Music');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S010', 'Sinhala');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S011', 'Tamil');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S013', 'Christianity');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S014', 'Physics');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S016', 'Biology');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S017', 'Commerce');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S019', 'Drama');
INSERT INTO SMS.Subject (Subject_ID, Name) VALUES ('S020', 'hgh');

create table Teacher
(
    Teacher_ID      varchar(10) not null
        primary key,
    Subject_ID      varchar(10) null,
    Name            varchar(20) not null,
    Class_ID        varchar(10) null,
    Grades_Assigned varchar(20) not null,
        foreign key (Class_ID) references Class (Class_ID)
);

INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T003', 'SUB05', 'Mrs. Anoma', 'C002', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T004', 'SUB12', 'Mr. Jude', 'C003', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T005', 'S001', 'Yasiru', 'C006', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T006', 'S001', 'sahan', 'C007', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T007', 'S014', 'Ms. Harshani', 'C002', '10');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T008', 'S016', 'Mr. Weerakoon', 'C002', '10');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T009', 'S002', 'Mrs. Kamani', 'C003', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T010', 'S004', 'Mr. Nimal', 'C003', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T011', 'S010', 'Ms. Udeshika', 'C005', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T012', 'S011', 'Mr. Aravinda', 'C006', '1');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T013', 'S020', 'Mrs. Pathmini', 'C004', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T014', 'S017', 'Mr. Perera', 'C005', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T015', 'S007', 'Ms. Chathurika', 'C004', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T016', 'S019', 'Mr. Sanjaya', 'C005', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T017', 'S008', 'Ms. Thilini', 'C007', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T018', 'S013', 'Mr. Ranil', 'C006', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T019', 'S005', 'Mrs. Hemamali', 'C007', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T020', 'S002', 'C. Herath', 'C012', '10');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T021', 'S004', 'K. Senanayake', 'C013', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T022', 'S010', 'R. Madushanka', 'C014', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T023', 'S011', 'N. Silva', 'C015', '10');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T024', 'S020', 'I. Dissanayake', 'C016', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T025', 'S017', 'T. Wijesinghe', 'C017', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T026', 'S007', 'S. Gunawardena', 'C018', '10');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T027', 'S019', 'H. Rathnayake', 'C019', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T028', 'S008', 'J. Fernando', 'C020', '12');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T029', 'S013', 'D. Wickramasinghe', 'C021', '10');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T030', 'S005', 'M. Perera', 'C022', '11');
INSERT INTO SMS.Teacher (Teacher_ID, Subject_ID, Name, Class_ID, Grades_Assigned) VALUES ('T031', 'S016', 'N. Abeysekara', 'C011', '12');


show tables ;
table Time_Table;

create table Time_Table
(
    Time_Table_ID varchar(10) not null
        primary key,
    Subject_ID    varchar(10) null,
    Start_Time    varchar(10) null,
    End_Time      varchar(10) null,
    day_of_week   varchar(10) not null,
        foreign key (Subject_ID) references Subject (Subject_ID)
);

INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T002', 'S014', '09:45:00', '11:15:00', 'Monday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T005', 'S001', '08:30:00', '10:00:00', 'Tuesday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T006', 'S016', '10:15:00', '11:45:00', 'Tuesday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T008', 'S002', '08:00:00', '09:30:00', 'Wednesday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T009', 'S004', '10:00:00', '11:30:00', 'Wednesday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T010', 'S010', '13:00:00', '14:30:00', 'Wednesday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T011', 'S011', '9:00', '10:30', 'Thursday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T013', 'S020', '14:00:00', '15:30:00', 'Thursday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T014', 'S017', '08:30:00', '10:00:00', 'Friday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T016', 'S007', '12:00', '1:00', 'Monday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T017', 'S019', '09:00:00', '10:30:00', 'Saturday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T018', 'S008', '11:00:00', '12:30:00', 'Saturday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T020', 'S013', '09:00:00', '10:30:00', 'Sunday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T022', 'S005', '14:00:00', '15:30:00', 'Sunday');
INSERT INTO SMS.Time_Table (Time_Table_ID, Subject_ID, Start_Time, End_Time, day_of_week) VALUES ('T023', 'S001', '8:00', '3:00', 'Monday');

