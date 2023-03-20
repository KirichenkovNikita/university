DROP TABLE IF EXISTS professorsCourses, studentsCourses, classrooms, courses, groups, internets,lessons,professors,studentTypes,students, credentials;

CREATE TABLE IF NOT EXISTS credentials(
    id          SERIAL PRIMARY KEY,
    login        VARCHAR(150)  NOT NULL,
    password VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS classrooms(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(250) NOT NULL,
    locationLink VARCHAR(250) NOT NULL,
    number VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    description VARCHAR(400) NOT NULL
);

CREATE TABLE IF NOT EXISTS groups(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS internets(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS lessons(
    id SERIAL PRIMARY KEY,
    courseId   INT,
    startTime TIMESTAMP,
    endTime TIMESTAMP,
    locationId   INT
);

CREATE TABLE IF NOT EXISTS professors(
    id SERIAL PRIMARY KEY,
    credentialsid INT,
    login VARCHAR(150) NOT NULL,
    password  VARCHAR(150) NOT NULL,
    firstName VARCHAR(150) NOT NULL,
    lastName  VARCHAR(150) NOT NULL,
    avatar VARCHAR(150),
    linkedin  VARCHAR(150),
    avatar_approved boolean default false
);

CREATE TABLE IF NOT EXISTS studentTypes(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS students(
    id SERIAL PRIMARY KEY,
    credentialsid INT,
    login VARCHAR(150) NOT NULL,
    password  VARCHAR(150) NOT NULL,
    firstName VARCHAR(150) NOT NULL,
    lastName  VARCHAR(150) NOT NULL,
    avatar VARCHAR(150),
    groupId   INT,
    type   INT,
    avatar_approved boolean default false
);

CREATE TABLE IF NOT EXISTS studentsCourses(
    student_id INT NOT NULL,
    course_id  INT NOT NULL,
    CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_student_course_id FOREIGN KEY (course_id) REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT student_course UNIQUE (student_id, course_id)
);

CREATE TABLE IF NOT EXISTS professorsCourses(
    professor_id INT NOT NULL,
    course_id  INT NOT NULL,
    CONSTRAINT fk_professor_id FOREIGN KEY (professor_id) REFERENCES professors (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_professor_course_id FOREIGN KEY (course_id) REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT professors_Courses UNIQUE (professor_id, course_id)
);