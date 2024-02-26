/*
 * Assignment 1
 * Tyler Lapiana - tjl9096
 * 
 * This project will cover the code portions of questions 2, 4, and 5.
 * Uncomment the section that you want to run that section of the code.
 * 
 * To get the code to run in the first place, I had to create a java project
 * and put this in the src folder of the project and add the "postgresql-42.7.1.jar"
 * file in the lib folder of the project. Since I was using VSCode, there was also a
 * .vscode folder that had a settings.json file that I did not modify.
 * 
 * Before each section of code will be a more detailed comment blcok that will
 * describe the necessary conditions to be met for the code to work.
 * 
 * I did realize that I have everything in one really long main function.
 * Not sure what compelled me to do this, but at the point of me turning
 * this in, I was not changing it.
 */

import java.sql.*;
import java.util.HashMap;
import java.io.*;

public class App {
    public static void main(String[] args) throws SQLException {

        // This is necessary code to run every part of the program, please leave as is

        Connection con = null;
        PreparedStatement st = null;
        PreparedStatement st2 = null;
        ResultSet rs = null;
    
        String url = "jdbc:postgresql://localhost/assignment1";
        String user = "postgres";
        String pwd = "postgrespassword";
        con = DriverManager.getConnection(url, user, pwd);
        con.setAutoCommit(false);


        /*

        // This section fulfils question 2's code.

        // This only thing this section of code needs is for the information above to be correct.
        // So to run this section, make sure the url, username, and password you want to use are all right.

        // Post Table
        st = con.prepareStatement("CREATE TABLE Post ( " +
            "postId INTEGER, postTypeId INTEGER NOT NULL, " +
            "acceptedAnswer INTEGER, parentId INTEGER, " +
            "creationDate TIMESTAMP NOT NULL, " +
            "score INTEGER NOT NULL, viewCount INTEGER, " +
            "body TEXT NOT NULL, " +
            "lastEditorUserId INTEGER, " +
            "lastEditDate TIMESTAMP NOT NULL, " +
            "lastActivityDate TIMESTAMP NOT NULL, " +
            "title VARCHAR(300), answerCount INTEGER, " +
            "commentCount INTEGER NOT NULL, " +
            "contentLicense VARCHAR(25) NOT NULL, " +
            "favoriteCount INTEGER, closedDate TIMESTAMP, " +
            "PRIMARY KEY (postId) " +
            "FOREIGN KEY (lastEditorUserId) REFERENCES Youser(youserId) "
            ")");
        st.execute();

        // User Table
        st = con.prepareStatement("CREATE TABLE Youser ( " +        // postgres doesn't like naming a table "user"
            "youserId INTEGER, reputation INTEGER NOT NULL, " +
            "displayName VARCHAR(100) NOT NULL, " +
            "creationDate TIMESTAMP NOT NULL, " + 
            "lastAccessDate TIMESTAMP NOT NULL, " + 
            "websiteURL VARCHAR(75), location VARCHAR(300), " +
            "aboutMe TEXT, viewCount INTEGER NOT NULL, " +
            "upvotes INTEGER NOT NULL, " +
            "downvotes INTEGER NOT NULL, " +
            "accountId INTEGER, " +
            "PRIMARY KEY (youserId) " +
            ")");
        st.execute();
        
        // Badge Table
        st = con.prepareStatement("CREATE TABLE Badge ( " + 
            "badgeId INTEGER, name VARCHAR(50) NOT NULL, " +
            "dateAwarded TIMESTAMP NOT NULL, " +
            "class INTEGER NOT NULL, " +
            "tagBased BOOLEAN NOT NULL, " +
            "PRIMARY KEY (badgeId), " +
            ")");
        st.execute();

        // Awarded Table
        st = con.prepareStatement("CREATE TABLE Awarded ( " +
            "badgeId INTEGER, youserId INTEGER, " +
            "PRIMARY KEY (badgeId, youserId), " +
            "FOREIGN KEY (badgeId) REFERENCES Badge(badgeId), " +
            "FOREIGN KEY (youser)")

        // Tag Table
        st = con.prepareStatement("CREATE TABLE Tag ( " +
            "tagId INTEGER, tagName VARCHAR(50) NOT NULL, " +
            "count INTEGER NOT NULL, " +
            "excerptPostId INTEGER NOT NULL, " +
            "wikiPostId INTEGER NOT NULL, " +
            "PRIMARY KEY (tagId), " +
            "FOREIGN KEY (excerptPostId) REFERENCES Post(postId), " +
            "FOREIGN KEY (wikiPostId) REFERENCES Post(postId) " +
            ")");
        st.execute();

        // Comment Table 
        st = con.prepareStatement("CREATE TABLE Comment ( " +
            "commentId INTEGER, " +
            "score INTEGER NOT NULL, " +
            "text TEXT NOT NULL, " +
            "creationDate TIMESTAMP NOT NULL, " +
            "contentLicense VARCHAR(25) NOT NULL, " +
            "PRIMARY KEY (commentId) " +
            ")");
        st.execute();

        // PostLink Table
        st = con.prepareStatement("CREATE TABLE PostLink ( " +
            "postLinkId INTEGER, " +
            "creationDate TIMESTAMP NOT NULL, " +
            "relatedPostId INTEGER, " +
            "linkType INTEGER NOT NULL, " +
            "PRIMARY KEY (postLinkId), " +
            "FOREIGN KEY (relatedPostId) REFERENCES Post(postId) " +
            ")");
        st.execute();

        // PostHistory Table
        st = con.prepareStatement("CREATE TABLE PostHistory ( " +
            "postHistoryId INTEGER, " +
            "postHistoryTypeId INTEGER NOT NULL, " +
            "revisionGUID VARCHAR(50) NOT NULL, " +
            "creationDate TIMESTAMP NOT NULL, " +
            "text TEXT, " +
            "contentLicense VARCHAR(25), " +
            "comment TEXT, "
            "PRIMARY KEY (postHistoryId) " +
            ")");
        st.execute();

        // Vote Table
        st = con.prepareStatement("CREATE TABLE Vote ( " +
            "voteId INTEGER, voteTypeId INTEGER NOT NULL, " +
            "creationDate TIMESTAMP, " +
            "PRIMARY KEY (voteId)" +
            ")");
        st.execute();

        // Posted Table
        st = con.prepareStatement("CREATE TABLE Posted ( " +
            "youserId INTEGER, postId INTEGER," +
            "PRIMARY KEY (youserId, postId)," +
            "FOREIGN KEY (youserId) REFERENCES Youser(youserId), " +
            "FOREIGN KEY (postId) REFERENCES Post(postId) " +
            ")");
        st.execute();

        // UserComment Table
        st = con.prepareStatement("CREATE TABLE UserComment ( " +
            "youserId INTEGER, commentId INTEGER," +
            "PRIMARY KEY (youserId, commentId)," +
            "FOREIGN KEY (youserId) REFERENCES Youser(youserId), " +
            "FOREIGN KEY (commentId) REFERENCES Comment(commentId) " +
            ")");
        st.execute();

        // Edit Table
        st = con.prepareStatement("CREATE TABLE Edit ( " +
            "youserId INTEGER, postHistoryId INTEGER," +
            "PRIMARY KEY (youserId, postHistoryId)," +
            "FOREIGN KEY (youserId) REFERENCES Youser(youserId), " +
            "FOREIGN KEY (postHistoryId) REFERENCES PostHistory(postHistoryId) " +
            ")");
        st.execute();

        // PostComment Table
        st = con.prepareStatement("CREATE TABLE PostComment ( " +
            "commentId INTEGER, postId INTEGER," +
            "PRIMARY KEY (commentId, postId)," +
            "FOREIGN KEY (commentId) REFERENCES Comment(commentId), " +
            "FOREIGN KEY (postId) REFERENCES Post(postId) " +
            ")");
        st.execute();

        // Taged Table
        st = con.prepareStatement("CREATE TABLE Taged ( " +
            "tagId INTEGER, postId INTEGER," +
            "PRIMARY KEY (tagId, postId)," +
            "FOREIGN KEY (tagId) REFERENCES Tag(tagId), " +
            "FOREIGN KEY (postId) REFERENCES Post(postId) " +
            ")");
        st.execute();

        // Linked Table
        st = con.prepareStatement("CREATE TABLE Linked ( " +
            "postLinkId INTEGER, postId INTEGER," +
            "PRIMARY KEY (postLinkId, postId)," +
            "FOREIGN KEY (postLinkId) REFERENCES PostLink(postLinkId), " +
            "FOREIGN KEY (postId) REFERENCES Post(postId) " +
            ")");
        st.execute();

        // Predecessor Table
        st = con.prepareStatement("CREATE TABLE Predecessor ( " +
            "postHistoryId INTEGER, postId INTEGER," +
            "PRIMARY KEY (postHistoryId, postId)," +
            "FOREIGN KEY (postHistoryId) REFERENCES PostHistory(postHistoryId), " +
            "FOREIGN KEY (postId) REFERENCES Post(postId) " +
            ")");
        st.execute();

        // PostVote Table
        st = con.prepareStatement("CREATE TABLE PostVote ( " +
            "voteId INTEGER, postId INTEGER," +
            "PRIMARY KEY (voteId, postId)," +
            "FOREIGN KEY (voteId) REFERENCES Vote(voteId), " +
            "FOREIGN KEY (postId) REFERENCES Post(postId) " +
            ")");
        st.execute();

        */

        /*

         // This section fulfils question 4's code

         // For this section to work, the database and all tables in it must be created and empty.
         // To do this, you should just be able to run the code for question 2, but I would run it seperate times
         // to make sure the database is made properly first.

         // You must also change the location of all the data files. For testing on my end, I had them routed via the
         // absolute path to the files so this would not work on another machine. We talked after class Friday about this
         // and you said just making this note to tell you to change the paths was adequate.

         // Additionally, as I stated in the report, I almost guarantee that this section will not finish within 4 hours.
         // I have tested each section of code individually as I was writing it, and they should all work, but together they
         // just take too long. If you want to test a section, you can always leave the Users and Posts sections uncommented,
         // but comment everything else that you don't want to test. This is because the other tables/sections all rely on 
         // either the user or post table for foreign keys.

         // Also, i found a weird issue where the code would get stuck randomly in the "Posts" section when testing. If this happens,
         // re-running it has solved it nearly every time. If it takes more than 10-15 minutes and there still isn't a message after "Posts",
         // then it is most likely stuck. 

         // Lastly, I have this as one large transaction, so no changes will be made to the database if any errors are made or if the program is
         // forcibly stopped.

         // I know this section is weird, so if you have any questions, feel free to reach out to me - tjl9096@rit.edu

         Boolean error = false;

         // Users.xml
         HashMap<Integer, String> userMap = new HashMap<>();

         System.out.println("Users");

        try {
            st = con.prepareStatement("INSERT INTO Youser(" + 
            "youserId, reputation, displayName, creationDate, lastAccessDate, websiteURL, " +
            "location, aboutMe, viewCount, upvotes, downvotes, accountId) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            File usersFile = new File("C:\\Users\\Tyler\\CSCI620\\Assignment 1\\askubuntu\\Users.xml");
            BufferedReader usersReader = new BufferedReader(new FileReader(usersFile));
            String usersString = usersReader.readLine(); // get rid of xml header
            usersString = usersReader.readLine();       // get rid of "<users>"
            usersString = usersReader.readLine();
            while (usersString.indexOf("Id") != -1) {
                int start = -1;
                int end = -1;
                int keywordIndex = -1;

                Integer youserId = null;
                Integer reputation = null;
                String displayName = null;
                Timestamp creationDate = null;
                Timestamp lastAccessDate = null;
                String websiteURL = null;
                String location = null;
                String aboutMe = null;
                Integer views = null;
                Integer upvotes = null;
                Integer downvotes = null;
                Integer accountId = null;

                keywordIndex = usersString.indexOf(" Id=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 5;
                    end = usersString.indexOf("\"", start);
                    youserId = Integer.valueOf(usersString.substring(start, end));
                }
                st.setInt(1, youserId);

                keywordIndex = usersString.indexOf("Reputation=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 12;
                    end = usersString.indexOf("\"", start);
                    reputation = Integer.valueOf(usersString.substring(start, end));
                }
                st.setInt(2, reputation);

                keywordIndex = usersString.indexOf("DisplayName=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 13;
                    end = usersString.indexOf("\"", start);
                    displayName = usersString.substring(start, end);
                }
                st.setString(3, displayName);

                keywordIndex = usersString.indexOf("CreationDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 14;
                    end = usersString.indexOf("\"", start);
                    creationDate = Timestamp.valueOf(usersString.substring(start, start + 10) + " " + usersString.substring(start + 11, end));
                }
                st.setTimestamp(4, creationDate);

                keywordIndex = usersString.indexOf("LastAccessDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 16;
                    end = usersString.indexOf("\"", start);
                    lastAccessDate = Timestamp.valueOf(usersString.substring(start, start + 10) + " " + usersString.substring(start + 11, end));
                }
                st.setTimestamp(5, lastAccessDate);

                keywordIndex = usersString.indexOf("WebsiteURL=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 12;
                    end = usersString.indexOf("\"", start);
                    websiteURL = usersString.substring(start, end);
                }
                if (websiteURL != null)
                    st.setString(6, websiteURL);
                else {
                    st.setNull(6, java.sql.Types.VARCHAR);
                }

                keywordIndex = usersString.indexOf("Location=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 10;
                    end = usersString.indexOf("\"", start);
                    location = usersString.substring(start, end);
                }
                if (location != null)
                    st.setString(7, location);
                else {
                    st.setNull(7, java.sql.Types.VARCHAR);
                }
                
                keywordIndex = usersString.indexOf("AboutMe=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 9;
                    end = usersString.indexOf("\"", start);
                    aboutMe = usersString.substring(start, end);
                }
                if (aboutMe != null)
                    st.setString(8, aboutMe);
                else {
                    st.setNull(8, java.sql.Types.VARCHAR);
                }

                keywordIndex = usersString.indexOf("Views=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 7;
                    end = usersString.indexOf("\"", start);
                    views = Integer.valueOf(usersString.substring(start, end));
                }
                st.setInt(9, views);

                keywordIndex = usersString.indexOf("UpVotes=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 9;
                    end = usersString.indexOf("\"", start);
                    upvotes = Integer.valueOf(usersString.substring(start, end));
                }
                st.setInt(10, upvotes);

                keywordIndex = usersString.indexOf("DownVotes=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 11;
                    end = usersString.indexOf("\"", start);
                    downvotes = Integer.valueOf(usersString.substring(start, end));
                }
                st.setInt(11, downvotes);

                keywordIndex = usersString.indexOf("AccountId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 11;
                    end = usersString.indexOf("\"", start);
                    accountId = Integer.valueOf(usersString.substring(start, end));
                }
                if (accountId != null)
                    st.setInt(12, accountId);
                else {
                    st.setNull(12, java.sql.Types.INTEGER);
                }

                userMap.put(youserId, usersString);

                st.executeUpdate();
                usersString = usersReader.readLine();
            }
            usersReader.close();
        }
        catch (Exception oops) {
            error = true;
            oops.printStackTrace();
        }

        // Posts.xml
        HashMap<Integer, String> postsMap = new HashMap<>();

        System.out.println("Posts");

        try {
            st = con.prepareStatement("INSERT INTO Post(" +
            "postId, postTypeId, acceptedAnswer, parentId, creationDate, score, viewCount, " +
            "body, lastEditorUserId, lastEditDate, lastActivityDate, title, answerCount, " +
            "commentCount, contentLicense, favoriteCount, closedDate) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            File postsFile = new File("C:\\Users\\Tyler\\CSCI620\\Assignment 1\\askubuntu\\Posts.xml");
            BufferedReader postsReader = new BufferedReader(new FileReader(postsFile));
            String postsString = postsReader.readLine();   // get rid of xml header
            postsString = postsReader.readLine();      // get rid of "<posts>"
            postsString = postsReader.readLine();
            while (postsString.indexOf("Id") != -1) {
                int start = -1;
                int end = -1;
                int keywordIndex = -1;

                Integer postId = null;
                Integer postTypeId = null;
                Integer acceptedAnswer = null;
                Integer parentId = null;
                Timestamp creationDate = null;
                Integer score = null;
                Integer viewCount = null;
                String body = null;
                Integer lastEditor = null;
                Timestamp lastEditDate = null;
                Timestamp lastActivityDate = null;
                String title = null;
                Integer answerCount = null;
                Integer commentCount = null;
                String contentLicense = null;
                Integer favoriteCount = null;
                Timestamp closedDate = null;

                keywordIndex = postsString.indexOf(" Id=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 5;
                    end = postsString.indexOf("\"", start);
                    postId = Integer.valueOf(postsString.substring(start, end));
                }
                st.setInt(1, postId);

                keywordIndex = postsString.indexOf("PostTypeId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 12;
                    end = postsString.indexOf("\"", start);
                    postTypeId = Integer.valueOf(postsString.substring(start, end));
                }
                st.setInt(2, postTypeId);

                keywordIndex = postsString.indexOf("AcceptedAnswerId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 18;
                    end = postsString.indexOf("\"", start);
                    acceptedAnswer = Integer.valueOf(postsString.substring(start, end));
                }
                if (acceptedAnswer != null)
                    st.setInt(3, acceptedAnswer);
                else
                    st.setNull(3, java.sql.Types.INTEGER);

                keywordIndex = postsString.indexOf("ParentId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 10;
                    end = postsString.indexOf("\"", start);
                    parentId = Integer.valueOf(postsString.substring(start, end));
                }
                if (parentId != null)
                    st.setInt(4, parentId);
                else
                    st.setNull(4, java.sql.Types.INTEGER);

                keywordIndex = postsString.indexOf("CreationDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 14;
                    end = postsString.indexOf("\"", start);
                    creationDate = Timestamp.valueOf(postsString.substring(start, start + 10) + " " + postsString.substring(start + 11, end));
                }
                st.setTimestamp(5, creationDate);

                keywordIndex = postsString.indexOf("Score=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 7;
                    end = postsString.indexOf("\"", start);
                    score = Integer.valueOf(postsString.substring(start, end));
                }
                st.setInt(6, score);

                keywordIndex = postsString.indexOf("ViewCount=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 11;
                    end = postsString.indexOf("\"", start);
                    viewCount = Integer.valueOf(postsString.substring(start, end));
                }
                if (viewCount != null)
                    st.setInt(7, viewCount);
                else
                    st.setNull(7, java.sql.Types.INTEGER);

                keywordIndex = postsString.indexOf("Body=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 6;
                    end = postsString.indexOf("\"", start);
                    body = postsString.substring(start, end);
                }
                st.setString(8, body);

                keywordIndex = postsString.indexOf("LastEditorUserId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 18;
                    end = postsString.indexOf("\"", start);
                    lastEditor = Integer.valueOf(postsString.substring(start, end));
                }
                if (lastEditor != null)
                    st.setInt(9, lastEditor);
                else
                    st.setNull(9, java.sql.Types.INTEGER);

                keywordIndex = postsString.indexOf("LastEditDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 14;
                    end = postsString.indexOf("\"", start);
                    lastEditDate = Timestamp.valueOf(postsString.substring(start, start + 10) + " " + postsString.substring(start + 11, end));
                }
                if (lastEditDate != null)
                    st.setTimestamp(10, lastEditDate);
                else
                    st.setNull(10, java.sql.Types.TIMESTAMP);

                keywordIndex = postsString.indexOf("LastActivityDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 18;
                    end = postsString.indexOf("\"", start);
                    lastActivityDate = Timestamp.valueOf(postsString.substring(start, start + 10) + " " + postsString.substring(start + 11, end));
                }
                st.setTimestamp(11, lastActivityDate);

                keywordIndex = postsString.indexOf("Title=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 7;
                    end = postsString.indexOf("\"", start);
                    title = postsString.substring(start, end);
                }
                if (title != null)
                    st.setString(12, title);
                else
                    st.setNull(12, java.sql.Types.VARCHAR);

                keywordIndex = postsString.indexOf("AnswerCount=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 13;
                    end = postsString.indexOf("\"", start);
                    answerCount = Integer.valueOf(postsString.substring(start, end));
                }
                if (answerCount != null)
                    st.setInt(13, answerCount);
                else
                    st.setNull(13, java.sql.Types.INTEGER);

                keywordIndex = postsString.indexOf("CommentCount=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 14;
                    end = postsString.indexOf("\"", start);
                    commentCount = Integer.valueOf(postsString.substring(start, end));
                }
                st.setInt(14, commentCount);

                keywordIndex = postsString.indexOf("ContentLicense=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 16;
                    end = postsString.indexOf("\"", start);
                    contentLicense = postsString.substring(start, end);
                }
                st.setString(15, contentLicense);

                keywordIndex = postsString.indexOf("FavoriteCount=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 15;
                    end = postsString.indexOf("\"", start);
                    favoriteCount = Integer.valueOf(postsString.substring(start, end));
                }
                if (favoriteCount != null)
                    st.setInt(16, favoriteCount);
                else
                    st.setNull(16, java.sql.Types.INTEGER);

                keywordIndex = postsString.indexOf("ClosedDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 12;
                    end = postsString.indexOf("\"", start);
                    closedDate = Timestamp.valueOf(postsString.substring(start, start + 10) + " " + postsString.substring(start + 11, end));
                }
                if (closedDate != null)
                    st.setTimestamp(17, closedDate);
                else
                    st.setNull(17, java.sql.Types.TIMESTAMP);

                postsMap.put(postId, postsString);

                st.executeUpdate();
                
                st2 = con.prepareStatement("INSERT INTO Posted(" + 
                "youserId, postId) " +
                "VALUES (?, ?)");
                int posterId = -1;
                keywordIndex = postsString.indexOf("OwnerUserId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 13;
                    end = postsString.indexOf("\"", start);
                    posterId = Integer.valueOf(postsString.substring(start, end));
                }
                if (userMap.containsKey(posterId)) {
                    st2.setInt(1, posterId);
                    st2.setInt(2, postId);
                    st2.executeUpdate();
                }

                postsString = postsReader.readLine();
            }
            postsReader.close();
        }
        catch (Exception oops) {
            error = true;
            oops.printStackTrace();
        }

        // Tags.xml
        HashMap<String, Integer> tagMap = new HashMap<>();

        System.out.println("Tags");

        try {            
            st = con.prepareStatement("INSERT INTO Tag(" + 
            "tagId, tagName, count, excerptPostId, wikiPostId) " +
            "VALUES (?, ?, ?, ?, ?)");

            File tagFile = new File("C:\\Users\\Tyler\\CSCI620\\Assignment 1\\askubuntu\\Tags.xml");
            BufferedReader tagReader = new BufferedReader(new FileReader(tagFile));
            String tagString = tagReader.readLine(); // get rid of xml header
            tagString = tagReader.readLine();       // get rid of "<tags>"
            tagString = tagReader.readLine();

            while (tagString.indexOf("Id") != -1) {
                int start = -1;
                int end = -1;
                int keywordIndex = -1;

                Integer tagId = null;
                String tagName = null;
                Integer count = null;
                Integer excerptPostId = null;
                Integer wikiPostId = null;

                keywordIndex = tagString.indexOf(" Id=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 5;
                    end = tagString.indexOf("\"", start);
                    tagId = Integer.valueOf(tagString.substring(start, end));
                }
                st.setInt(1, tagId);

                keywordIndex = tagString.indexOf("TagName=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 9;
                    end = tagString.indexOf("\"", start);
                    tagName = tagString.substring(start, end);
                }
                st.setString(2, tagName);

                keywordIndex = tagString.indexOf("Count=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 7;
                    end = tagString.indexOf("\"", start);
                    count = Integer.valueOf(tagString.substring(start, end));
                }
                st.setInt(3, count);

                keywordIndex = tagString.indexOf("ExcerptPostId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 15;
                    end = tagString.indexOf("\"", start);
                    excerptPostId = Integer.valueOf(tagString.substring(start, end));
                }
                if (excerptPostId != null)
                    st.setInt(4, excerptPostId);
                else {
                    st.setNull(4, java.sql.Types.INTEGER);
                }

                keywordIndex = tagString.indexOf("WikiPostId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 12;
                    end = tagString.indexOf("\"", start);
                    wikiPostId = Integer.valueOf(tagString.substring(start, end));
                }
                if (wikiPostId != null)
                    st.setInt(5, wikiPostId);
                else {
                    st.setNull(5, java.sql.Types.INTEGER);
                }

                tagMap.put(tagName, tagId);

                st.executeUpdate();
                tagString = tagReader.readLine();
            }
            tagReader.close();

            System.out.println("Taged");

            for (Integer tempPostId : postsMap.keySet()) {
                String postsString = postsMap.get(tempPostId);
                int tagStart = postsString.indexOf("Tags=\"");
                if (tagStart != -1 ) {
                    int tagEnd = postsString.indexOf("\"", tagStart + 6);
                    String postTags = postsString.substring(tagStart + 6, tagEnd);
                    postTags = postTags.replace("&lt;", "");
                    postTags = postTags.replace("&gt;", ",");
                    st2 = con.prepareStatement("INSERT INTO Taged(" + 
                    "tagId, postId) " +
                    "VALUES (?, ?)");
                    while (postTags != "") {
                        int nextTag = postTags.indexOf(",");
                        if (nextTag != -1) {
                            String tagToEnter = postTags.substring(0, nextTag);

                            if (tagMap.containsKey(tagToEnter)) {
                                st2.setInt(1, tagMap.get(tagToEnter));
                                st2.setInt(2, tempPostId);
                                st2.executeUpdate();  
                            }
                        }   
                        postTags = postTags.substring(nextTag + 1, postTags.length());
                    }
                }
            }   
            tagMap = null;         
        }
        catch (Exception oops) {
            error = true;
            oops.printStackTrace();
        }

        // Badges.xml
        System.out.println("Badges");

        try {
            st = con.prepareStatement("INSERT INTO Badge(" + 
            "badgeId, name, dateAwarded, class, tagBased) " +
            "VALUES (?, ?, ?, ?, ?)");

            File badgeFile = new File("C:\\Users\\Tyler\\CSCI620\\Assignment 1\\askubuntu\\Badges.xml");
            BufferedReader badgeReader = new BufferedReader(new FileReader(badgeFile));
            String badgeString = badgeReader.readLine();   // get rid of xml header
            badgeString = badgeReader.readLine();      // get rid of "<posts>"
            badgeString = badgeReader.readLine();

            while (badgeString.indexOf("Id") != -1) {
                int start = -1;
                int end = -1;
                int keywordIndex = -1;

                Integer badgeId = null;
                String name = null;
                Timestamp dateAwarded = null;
                Integer classNum = null;
                Boolean tagBased = null;

                keywordIndex = badgeString.indexOf(" Id=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 5;
                    end = badgeString.indexOf("\"", start);
                    badgeId = Integer.valueOf(badgeString.substring(start, end));
                }
                st.setInt(1, badgeId);

                keywordIndex = badgeString.indexOf("Name=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 6;
                    end = badgeString.indexOf("\"", start);
                    name = badgeString.substring(start, end);
                }
                st.setString(2, name);

                keywordIndex = badgeString.indexOf("Date=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 6;
                    end = badgeString.indexOf("\"", start);
                    dateAwarded = Timestamp.valueOf(badgeString.substring(start, start + 10) + " " + badgeString.substring(start + 11, end));
                }
                st.setTimestamp(3, dateAwarded);

                keywordIndex = badgeString.indexOf("Class=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 7;
                    end = badgeString.indexOf("\"", start);
                    classNum = Integer.valueOf(badgeString.substring(start, end));
                }
                st.setInt(4, classNum);

                keywordIndex = badgeString.indexOf("TagBased=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 10;
                    end = badgeString.indexOf("\"", start);
                    String temp = badgeString.substring(start, end);
                    tagBased = false;
                    if (temp == "True") {
                        tagBased = true;
                    }
                }
                st.setBoolean(5, tagBased);

                st.executeUpdate();

                st2 = con.prepareStatement("INSERT INTO Awarded(" +
                "badgeId, youserId) " +
                "VALUES (?, ?)");

                Integer youserId = null;

                keywordIndex = badgeString.indexOf("UserId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 8;
                    end = badgeString.indexOf("\"", start);
                    youserId = Integer.valueOf(badgeString.substring(start, end));
                }

                if (userMap.containsKey(youserId)) {
                    st2.setInt(1, badgeId);
                    st2.setInt(2, youserId);
                    st2.executeUpdate();
                }

                badgeString = badgeReader.readLine();
            }
            badgeReader.close();
        }
        catch (Exception oops) {
            error = true;
            oops.printStackTrace();
        }

        // PostLinks.xml
        System.out.println("PostLinks");
        try {
            st = con.prepareStatement("INSERT INTO PostLink(" +
            "postLinkId, creationDate, relatedPostId, linkType) " +
            "VALUES (?, ?, ?, ?)");

            File postLinkFile = new File("C:\\Users\\Tyler\\CSCI620\\Assignment 1\\askubuntu\\PostLinks.xml");
            BufferedReader postLinkReader = new BufferedReader(new FileReader(postLinkFile));
            String postLinkString = postLinkReader.readLine();   // get rid of xml header
            postLinkString = postLinkReader.readLine();      // get rid of "<postlinks>"
            postLinkString = postLinkReader.readLine();

            while (postLinkString.indexOf("Id") != -1 ) {
                int start = -1;
                int end = -1;
                int keywordIndex = -1;

                Integer postLinkId = null;
                Timestamp creationDate = null;
                Integer relatedPostId = null;
                Integer linkType = null;

                keywordIndex = postLinkString.indexOf(" Id=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 5;
                    end = postLinkString.indexOf("\"", start);
                    postLinkId = Integer.valueOf(postLinkString.substring(start, end));
                }
                st.setInt(1, postLinkId);
                
                keywordIndex = postLinkString.indexOf("CreationDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 14;
                    end = postLinkString.indexOf("\"", start);
                    creationDate = Timestamp.valueOf(postLinkString.substring(start, start + 10) + " " + postLinkString.substring(start + 11, end));
                }
                st.setTimestamp(2, creationDate);

                keywordIndex = postLinkString.indexOf("RelatedPostId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 15;
                    end = postLinkString.indexOf("\"", start);
                    relatedPostId = Integer.valueOf(postLinkString.substring(start, end));
                }
                if (postsMap.containsKey(relatedPostId)) {
                    st.setInt(3, relatedPostId);
                }
                else {
                    st.setNull(3, java.sql.Types.INTEGER);
                }

                keywordIndex = postLinkString.indexOf("LinkTypeId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 12;
                    end = postLinkString.indexOf("\"", start);
                    linkType = Integer.valueOf(postLinkString.substring(start, end));
                }
                st.setInt(4, linkType);

                st.executeUpdate();

                st2 = con.prepareStatement("INSERT INTO Linked(" +
                "postLinkId, postId) " +
                "VALUES (?, ?)");

                Integer postId = null;

                keywordIndex = postLinkString.indexOf("PostId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 8;
                    end = postLinkString.indexOf("\"", start);
                    postId = Integer.valueOf(postLinkString.substring(start, end));
                }

                if (postsMap.containsKey(postId)) {
                    st2.setInt(1, postLinkId);
                    st2.setInt(2, postId);
                    st2.executeUpdate();
                }

                postLinkString = postLinkReader.readLine();
            }
            postLinkReader.close();
        }
        catch (Exception oops) {
            error = true;
            oops.printStackTrace();
        }

        // Votes.xml
        System.out.println("Votes");
        
        try {
            st = con.prepareStatement("INSERT INTO Vote(" +
            "voteId, voteTypeId, creationDate)" +
            "VALUES (?, ?, ?)");

            File voteFile = new File("C:\\Users\\Tyler\\CSCI620\\Assignment 1\\askubuntu\\Votes.xml");
            BufferedReader voteReader = new BufferedReader(new FileReader(voteFile));
            String voteString = voteReader.readLine();   // get rid of xml header
            voteString = voteReader.readLine();      // get rid of "<votes>"
            voteString = voteReader.readLine();

            while (voteString.indexOf("Id") != -1) {
                int start = -1;
                int end = -1;
                int keywordIndex = -1;

                Integer voteId = null;
                Integer voteTypeId = null;
                Timestamp creationDate = null;

                keywordIndex = voteString.indexOf(" Id=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 5;
                    end = voteString.indexOf("\"", start);
                    voteId = Integer.valueOf(voteString.substring(start, end));
                }
                st.setInt(1, voteId);

                keywordIndex = voteString.indexOf("VoteTypeId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 12;
                    end = voteString.indexOf("\"", start);
                    voteTypeId = Integer.valueOf(voteString.substring(start, end));
                }
                st.setInt(2, voteTypeId);

                keywordIndex = voteString.indexOf("creationDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 14;
                    end = voteString.indexOf("\"", start);
                    creationDate = Timestamp.valueOf(voteString.substring(start, start + 10) + " " + voteString.substring(start + 11, end));
                }
                st.setTimestamp(3, creationDate);

                st.executeUpdate();

                st2 = con.prepareStatement("INSERT INTO PostVote(" +
                "voteId, postId)" +
                "VALUES(?, ?)");

                Integer postId = null;

                keywordIndex = voteString.indexOf("PostId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 8;
                    end = voteString.indexOf("\"", start);
                    postId = Integer.valueOf(voteString.substring(start, end));
                }

                if (postsMap.containsKey(postId)) {
                    st2.setInt(1, voteId);
                    st2.setInt(2, postId);
                    st2.executeUpdate();
                }

                voteString = voteReader.readLine();
            }
            voteReader.close();
        }
        catch (Exception oops) {
            error = true;
            oops.printStackTrace();
        }

        // Comments.xml
        System.out.println("Comments");

        try {
            st = con.prepareStatement("INSERT INTO Comment(" + 
            "commentId, score, text, creationDate, contentLicense) " +
            "VALUES (?, ?, ?, ?, ?)");

            File commentFile = new File("C:\\Users\\Tyler\\CSCI620\\Assignment 1\\askubuntu\\Comments.xml");
            BufferedReader commentReader = new BufferedReader(new FileReader(commentFile));
            String commentString = commentReader.readLine();   // get rid of xml header
            commentString = commentReader.readLine();      // get rid of "<posts>"
            commentString = commentReader.readLine();

            while (commentString.indexOf("Id") != -1) {
                int start = -1;
                int end = -1;
                int keywordIndex = -1;

                Integer commentId = null;
                Integer score = null;
                String text = null;
                Timestamp creationDate = null;
                String contentLicense = null;

                keywordIndex = commentString.indexOf(" Id=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 5;
                    end = commentString.indexOf("\"", start);
                    commentId = Integer.valueOf(commentString.substring(start, end));
                }
                st.setInt(1, commentId);

                keywordIndex = commentString.indexOf("Score=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 7;
                    end = commentString.indexOf("\"", start);
                    score = Integer.valueOf(commentString.substring(start, end));
                }
                st.setInt(2, score);

                keywordIndex = commentString.indexOf("Text=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 6;
                    end = commentString.indexOf("\"", start);
                    text = commentString.substring(start, end);
                }
                st.setString(3, text);

                keywordIndex = commentString.indexOf("CreationDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 14;
                    end = commentString.indexOf("\"", start);
                    creationDate = Timestamp.valueOf(commentString.substring(start, start + 10) + " " + commentString.substring(start + 11, end));
                }
                st.setTimestamp(4, creationDate);

                keywordIndex = commentString.indexOf("ContentLicense=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 16;
                    end = commentString.indexOf("\"", start);
                    contentLicense = commentString.substring(start, end);
                }
                st.setString(5, contentLicense);

                st.executeUpdate();
                
                st2 = con.prepareStatement("INSERT INTO UserComment(" +
                "commentId, youserId) " +
                "VALUES (?, ?)");

                Integer youserId = null;

                keywordIndex = commentString.indexOf("UserId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 8;
                    end = commentString.indexOf("\"", start);
                    youserId = Integer.valueOf(commentString.substring(start, end));
                }

                if (userMap.containsKey(youserId)) {
                    st2.setInt(1, commentId);
                    st2.setInt(2, youserId);
                    st2.executeUpdate();
                }

                st2 = con.prepareStatement("INSERT INTO PostComment(" +
                "commentId, postId) " +
                "VALUES (?, ?)");

                Integer postId = null;

                keywordIndex = commentString.indexOf("PostId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 8;
                    end = commentString.indexOf("\"", start);
                    postId = Integer.valueOf(commentString.substring(start, end));
                }

                if (postsMap.containsKey(postId)) {
                    st2.setInt(1, commentId);
                    st2.setInt(2, postId);
                    st2.executeUpdate();
                }

                commentString = commentReader.readLine();
            }
            commentReader.close();
        }
        catch (Exception oops) {
            error = true;
            oops.printStackTrace();
        }

        // PostHistory.xml
        System.out.println("PostHistory");

        try {
            st = con.prepareStatement("INSERT INTO PostHistory( " +
            "postHistoryId, postHistoryTypeId, revisionGUID, creationDate, text, contentLicense, comment) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)");

            File postHistoryFile = new File("C:\\Users\\Tyler\\CSCI620\\Assignment 1\\askubuntu\\PostHistory.xml");
            BufferedReader postHistoryReader = new BufferedReader(new FileReader(postHistoryFile));
            String postHistoryString = postHistoryReader.readLine();   // get rid of xml header
            postHistoryString = postHistoryReader.readLine();      // get rid of "<posthistory>"
            postHistoryString = postHistoryReader.readLine();

            while (postHistoryString.indexOf("Id") != -1) {
                int start = -1;
                int end = -1;
                int keywordIndex = -1;

                Integer postHistoryId = null;
                Integer postHistoryTypeId = null;
                String revisionGUID = null;
                Timestamp creationDate = null;
                String text = null;
                String contentLicense = null;
                String comment = null;

                keywordIndex = postHistoryString.indexOf(" Id=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 5;
                    end = postHistoryString.indexOf("\"", start);
                    postHistoryId = Integer.valueOf(postHistoryString.substring(start, end));
                }
                st.setInt(1, postHistoryId);

                keywordIndex = postHistoryString.indexOf("PostHistoryTypeId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 19;
                    end = postHistoryString.indexOf("\"", start);
                    postHistoryTypeId = Integer.valueOf(postHistoryString.substring(start, end));
                }
                st.setInt(2, postHistoryTypeId);

                keywordIndex = postHistoryString.indexOf("RevisionGUID=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 14;
                    end = postHistoryString.indexOf("\"", start);
                    revisionGUID = postHistoryString.substring(start, end);
                }
                st.setString(3, revisionGUID);

                keywordIndex = postHistoryString.indexOf("CreationDate=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 14;
                    end = postHistoryString.indexOf("\"", start);
                    creationDate = Timestamp.valueOf(postHistoryString.substring(start, start + 10) + " " + postHistoryString.substring(start + 11, end));
                }
                st.setTimestamp(4, creationDate);

                keywordIndex = postHistoryString.indexOf("Text=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 6;
                    end = postHistoryString.indexOf("\"", start);
                    text = postHistoryString.substring(start, end);
                }
                st.setString(5, text);

                keywordIndex = postHistoryString.indexOf("ContentLicense=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 16;
                    end = postHistoryString.indexOf("\"", start);
                    contentLicense = postHistoryString.substring(start, end);
                }
                st.setString(6, contentLicense);

                keywordIndex = postHistoryString.indexOf("Comment=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 9;
                    end = postHistoryString.indexOf("\"", start);
                    comment = postHistoryString.substring(start, end);
                }
                if (comment != null) {
                    st.setString(7, comment);
                }
                else {
                    st.setNull(7, java.sql.Types.VARCHAR);
                }
                
                st.executeUpdate();

                st2 = con.prepareStatement("INSERT INTO Edit( " +
                "youserId, postHistoryId )" +
                "VALUES(?, ?)");

                Integer youserId = null;

                keywordIndex = postHistoryString.indexOf("UserId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 8;
                    end = postHistoryString.indexOf("\"", start);
                    youserId = Integer.valueOf(postHistoryString.substring(start, end));
                }

                if (userMap.containsKey(youserId)) {
                    st2.setInt(1, youserId);
                    st2.setInt(2, postHistoryId);
                    st2.executeUpdate();
                }

                st2 = con.prepareStatement("INSERT INTO Predecessor( " +
                "postHistoryId, postId )" +
                "VALUES(?, ?)");

                Integer postId = null;

                keywordIndex = postHistoryString.indexOf("PostId=\"");
                if (keywordIndex != -1) {
                    start = keywordIndex + 8;
                    end = postHistoryString.indexOf("\"", start);
                    postId = Integer.valueOf(postHistoryString.substring(start, end));
                }

                if (postsMap.containsKey(postId)) {
                    st2.setInt(1, postHistoryId);
                    st2.setInt(2, postId);
                    st2.executeUpdate();
                }

                postHistoryString = postHistoryReader.readLine();

            }
            postHistoryReader.close();
        }
        catch (Exception oops) {
            error = true;
            oops.printStackTrace();
        }

        if (!error) {
            System.out.println("No ERRORS --- YIPPIE!!!\n This means database was updated");
            con.commit();
        }

        */

        /*

        // This section fulfils question 5's code.

        // This section of code only require that the database tables be created, and not even all of them, technically
        // just the tag table because that is the table I am using to test.

        // Additionally, since I set the con.setAutoCommit to false already, I don't do it again here

        try {
            st = con.prepareStatement("SELECT tagId FROM Tag WHERE tagId > 40000");
            rs = st.executeQuery();

            if (!rs.next()){
                System.out.println("There was no data returned from the query");
            }
            else {
                System.out.println("Should not be seeing this");
            }

            st = con.prepareStatement("INSERT INTO Tag( " +
            "tagId, tagName, count )" +
            "VALUES(?, ?, ?)");

            // This is row 1, it is perfectly fine
            st.setInt(1, 50000);
            st.setString(2, "this-is-a-test-tag");
            st.setInt(3, 1);
            st.executeUpdate();

            // This is row 2, it fails due to the setting of a "NOT NULL" value to null
            st.setInt(1, 50001);
            st.setNull(2, java.sql.Types.VARCHAR); // this is the error, tagName can't be null
            st.setInt(3, 1);
            st.executeUpdate();     // this line tht throws the SQL error

            // This is row 3, it should never make it here due to the error
            st.setInt(1, 50002);
            st.setString(2, "this-should-not-exist");
            st.setInt(3, 1);
            st.executeUpdate();

        }
        catch (SQLException oops) {
            System.out.println("This should print when the error is made");
            // Not doing rollback to test if the 1st row was entered
        }
        finally {
            con.commit(); // or else if there would be no change if somehow the error was not caught

            try {
                st = con.prepareStatement("SELECT tagId FROM Tag WHERE tagId > 40000");
                rs = st.executeQuery();
    
                if (!rs.next()){
                    System.out.println("This message means no data was returned, meaning transaction was successfully aborted");
                }
                else {
                    System.out.println("This means the transaction still went through somehow, not good");
                }
            }
            catch (Exception oops) {
                System.out.println("Idk how this happened");
            }
        }

        */

        // This is code cleanup, please leave as it is
        try {
            if (con != null)
                con.close();
            if (st != null)
                st.close();
            if (st2 != null)
                st2.close();
            if (rs != null)
                rs.close();
        }
        catch (SQLException oops) {
            System.out.println("Something went REALLY wrong");
        }
    }
}
 