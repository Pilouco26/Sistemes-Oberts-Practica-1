<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import = "java.sql.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Database SQL Load</title>
    </head>
    <style>
        .error {
            color: red;
        }
        pre {
            color: green;
        }
    </style>
    <body>
        <h2>Database SQL Load</h2>
        <%
            /* How to customize:
             * 1. Update the database name on dbname.
             * 2. Create the list of tables, under tablenames[].
             * 3. Create the list of table definition, under tables[].
             * 4. Create the data into the above table, under data[]. 
             * 
             * If there is any problem, it will exit at the very first error.
             */
            String dbname = "homework1";
            String schema = "ROOT";
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            /* this will generate database if not exist */
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbname, "root", "root");
            Statement stmt = con.createStatement();

            /* inserting data */
 /* you have to exclude the id autogenerated from the list of columns if you have use it. */
            String data[] = new String[]{
                "INSERT INTO Customer (id, name, email, password) VALUES (1, 'John', 'john@gmail.com', 'a231344'),"
                + "(2, 'Mary', 'mary@gmail.com', 'a231345'),"
                + "(3, 'Peter', 'peter@gmail.com', 'a231346'),"
                + "(4,'Susan', 'susan@gmail.com', 'a231347'),"
                + "(5,'David', 'david@gmail.com', 'a231348'),"
                + "(6,'Sarah', 'sarah@gmail.com', 'a231349'),"
                + "(7,'Paul', 'paul@gmail.com', 'a231350'),"
                + "(8,'Margaret', 'margaret@gmail.com', 'a231351'),"
                + "(9,'William', 'william@gmail.com', 'a231352'),"
                + "(10,'Patricia', 'patricia@gmail.com', 'a231353'),"
                + "(11,'Michael', 'michael@gmail.com', 'a231354'),"
                + "(12,'Linda', 'linda@gmail.com', 'a231355'),"
                + "(13,'James', 'james@gmail.com', 'a231356'),"
                + "(14,'Barbara', 'barbara@gmail.com', 'a231357'),"
                + "(15,'Richard', 'richard@gmail.com', 'a231358'),"
                + "(16,'Elizabeth', 'elizabeth@gmail.com', 'a231359'),"
                + "(17,'Donald', 'donald@gmail.com', 'a231360'),"
                + "(18,'Helen', 'helen@gmail.com', 'a231361'),"
                + "(19,'Kenneth', 'kenneth@gmail.com', 'a231362')"
            };

            for (String datum : data) {
                if (stmt.executeUpdate(datum) <= 0) {
                    out.println("<span class='error'>Error inserting data: " + datum + "</span>");
                    return;
                }
                out.println("<pre> -> " + datum + "<pre>");
            }
        %>
        <button onclick="window.location = '<%=request.getSession().getServletContext().getContextPath()%>'">Go home</button>
    </body>
</html>
