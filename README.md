# CS425-DynamicClassSchedule

This web application was a college course assigned project.

Running this web application requires Java, Apache Tomcat server, and a MySQL database server. 

This web application is written in HTML, CSS, JavaScript, and JQuery on the frontend. The backend is handled by Ajax, Java (JSP, servlets, JavaBean, database object), and SQL.

## How the web application works:

1. The user logs on to the site and is forwarded to an index page that has a drop down menu to select the year. 
<img width="822" alt="Screen Shot 2022-05-25 at 8 06 22 AM" src="https://user-images.githubusercontent.com/26610251/170268908-09f80793-61bf-4e79-abba-f33a080379d7.png">



2. When the user selects the year, the current page is forwarded to a search page. The user then has the choice to register for a course or search for courses.  
<img width="822" alt="Screen Shot 2022-05-25 at 8 08 58 AM" src="https://user-images.githubusercontent.com/26610251/170269521-67ac85e2-ab5b-4c17-95e0-02279e5b64d2.png">


3. If the user chooses to regsiter, the current page is forwarded to a registration page. 
<img width="1281" alt="Screen Shot 2022-05-25 at 8 10 26 AM" src="https://user-images.githubusercontent.com/26610251/170269775-ae5affd9-06e7-4ebe-b632-2837a51bf8a3.png">


4. If the user instead chooses to search for courses, the  user can fill out the search form with search criteria and submit the form, and then the current page is forwarded to a search results page in the form of a table.
<img width="1680" alt="Screen Shot 2022-05-25 at 8 11 59 AM" src="https://user-images.githubusercontent.com/26610251/170270070-bb82326c-5117-43c0-b6d5-3a2c727e4152.png">

## Some improvements that I would like to make to this application:
1. Better formatting of search results page with CSS styling
2. Registration logic to prevent scheduling conflicts
3. A downloadable and printable PDF of the student's registration information 

## Directory Structure: 
.
├── build
│   ├── empty
│   ├── generated-sources
│   │   └── ap-source-output
│   └── web
│       ├── META-INF
│       │   ├── MANIFEST.MF
│       │   └── context.xml
│       ├── WEB-INF
│       │   ├── classes
│       │   │   └── edu
│       │   │       └── jsu
│       │   │           └── mcis
│       │   │               └── cs425
│       │   │                   └── lab6
│       │   │                       ├── DropServlet.class
│       │   │                       ├── ParameterBean.class
│       │   │                       ├── RegisterServlet.class
│       │   │                       ├── RegistrationDatabase.class
│       │   │                       └── SearchServlet.class
│       │   ├── lib
│       │   │   ├── json-simple-1.1.1.jar
│       │   │   └── mysql-connector-java-8.0.19.jar
│       │   └── web.xml
│       ├── login.jsp
│       └── secure
│           ├── index.jsp
│           ├── logout.jsp
│           ├── register.jsp
│           ├── schedulelisting.jsp
│           ├── schedulesearch.jsp
│           └── scripts
│               ├── ScheduleSearch.js
│               ├── ajaxrequests.js
│               └── jquery-3.6.0.min.js
├── build.xml
├── dist
│   └── CS425Lab6.war
├── nbproject
│   ├── ant-deploy.xml
│   ├── build-impl.xml
│   ├── genfiles.properties
│   ├── private
│   │   ├── private.properties
│   │   └── private.xml
│   ├── project.properties
│   └── project.xml
├── src
│   ├── conf
│   │   └── MANIFEST.MF
│   └── java
│       └── edu
│           └── jsu
│               └── mcis
│                   └── cs425
│                       └── lab6
│                           ├── DropServlet.java
│                           ├── ParameterBean.java
│                           ├── RegisterServlet.java
│                           ├── RegistrationDatabase.java
│                           └── SearchServlet.java
├── test
└── web
    ├── META-INF
    │   └── context.xml
    ├── WEB-INF
    │   └── web.xml
    ├── login.jsp
    └── secure
        ├── index.jsp
        ├── logout.jsp
        ├── register.jsp
        ├── schedulelisting.jsp
        ├── schedulesearch.jsp
        └── scripts
            ├── ScheduleSearch.js
            ├── ajaxrequests.js
            └── jquery-3.6.0.min.js

