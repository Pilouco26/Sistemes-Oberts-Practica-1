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
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (300, 'Final Fantasy VII', 'RPG', 'PlayStation', 15, 'images/final_fantasy_vii.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (400, 'The Elder Scrolls V: Skyrim', 'Action RPG', 'Xbox', 8, 'images/skyrim.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (500, 'Grand Theft Auto V', 'Action-Adventure', 'PlayStation', 12, 'images/gta_v.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (600, 'Minecraft', 'Sandbox', 'PC', 20, 'images/minecraft.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (700, 'Red Dead Redemption 2', 'Action-Adventure', 'Xbox', 10, 'images/red_dead_redemption_2.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (800, 'The Witcher 3: Wild Hunt', 'Action RPG', 'PlayStation', 18, 'images/witcher_3.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (900, 'Super Smash Bros. Ultimate', 'Fighting', 'Nintendo Switch', 14, 'images/smash_bros_ultimate.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1000, 'Fortnite', 'Battle Royale', 'PC', 25, 'images/fortnite.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1100, 'Call of Duty: Warzone', 'Battle Royale', 'Xbox', 8, 'images/cod_warzone.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1200, 'Overwatch', 'FPS', 'PlayStation', 15, 'images/overwatch.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1300, 'Animal Crossing: New Horizons', 'Life Simulation', 'Nintendo Switch', 20, 'images/animal_crossing.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1400, 'FIFA 22', 'Sports', 'Xbox', 12, 'images/fifa_22.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1500, 'Assassins Creed Valhalla', 'Action RPG', 'PlayStation', 10, 'images/assassins_creed_valhalla.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1600, 'Cyberpunk 2077', 'Action RPG', 'PC', 15, 'images/cyberpunk_2077.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1700, 'Splatoon 2', 'Third-Person Shooter', 'Nintendo Switch', 18, 'images/splatoon_2.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1800, 'Star Wars Jedi: Fallen Order', 'Action-Adventure', 'Xbox', 10, 'images/star_wars_jedi_fallen_order.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (1900, 'Doom Eternal', 'First-Person Shooter', 'PlayStation', 12, 'images/doom_eternal.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (2000, 'Among Us', 'Social Deduction', 'PC', 15, 'images/among_us.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (2100, 'Rocket League', 'Sports', 'Nintendo Switch', 20, 'images/rocket_league.jpg')",
    "INSERT INTO Game (id, name, type, console, stock, pathImage) VALUES (2200, 'Halo Infinite', 'First-Person Shooter', 'Xbox', 10, 'images/halo_infinite.jpg')"
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
