package server;

import database.*;
import org.postgresql.util.PSQLException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Postgre {
    private Connection connection;
    private String login;
    private String password;
    private User user;

    public Postgre(Connection connection, String login, String password){
        this.connection = connection;
        this.login = login;

        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void insert(SpaceMarine spaceMarine) {
        try {
            String request = "insert into \"SpaceMarineCollection\" VALUES (nextval('sYOURCODE_sequence'),?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(request);
            statement.setString(1, "postgre");
            statement.setLong(13, spaceMarine.getKey());
            statement.setString(2, spaceMarine.getName());
            statement.setDouble(3, spaceMarine.getCoordinates().getX());
            statement.setLong(4, spaceMarine.getCoordinates().getY());
            statement.setString(5, spaceMarine.getCreationDate());
            statement.setDouble(6, spaceMarine.getHealth());
            statement.setString(7, spaceMarine.getCategory().toString());
            statement.setString(8, spaceMarine.getWeaponType().toString());
            statement.setString(9, spaceMarine.getMeleeWeapon().toString());
            statement.setString(10, spaceMarine.getChapter().getName());
            statement.setString(11, spaceMarine.getChapter().getParentLegion());
            statement.setInt(12, spaceMarine.getChapter().getMarinesCount());
            statement.executeUpdate();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(SpaceMarine spaceMarine) {
        try {
            String request = "insert into \"SpaceMarineCollection\" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1, spaceMarine.getId());
            statement.setString(2, "postgre");
            statement.setLong(14, spaceMarine.getKey());
            statement.setString(3, spaceMarine.getName());
            statement.setDouble(4, spaceMarine.getCoordinates().getX());
            statement.setLong(5, spaceMarine.getCoordinates().getY());
            statement.setString(6, spaceMarine.getCreationDate());
            statement.setDouble(7, spaceMarine.getHealth());
            statement.setString(8, spaceMarine.getCategory().toString());
            statement.setString(9, spaceMarine.getWeaponType().toString());
            statement.setString(10, spaceMarine.getMeleeWeapon().toString());
            statement.setString(11, spaceMarine.getChapter().getName());
            statement.setString(12, spaceMarine.getChapter().getParentLegion());
            statement.setInt(13, spaceMarine.getChapter().getMarinesCount());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void remove(long key){
        String request  = "DELETE from \"SpaceMarineCollection\" WHERE key=?";
        try {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(1,key);
            statement.executeUpdate();
            statement.close();
        } catch (PSQLException e){
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void clear(){
        String request  = "TRUNCATE TABLE \"SpaceMarineCollection\"";
        try {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.executeUpdate();
            statement.close();
        } catch (PSQLException e){
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(long key, SpaceMarine spaceMarine){
        String request  = "update \"SpaceMarineCollection\" SET id=nextval('sYOURCODE_sequence'), name=?, x=?, y=?, health=?, AstartesCategory=?, Weapon=?, MeleeWeapon=?,ChapterName=?, ChapterLegion=?, MarinesCount=? WHERE key=?";
        try {
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setLong(11, key);
            statement.setString(2, spaceMarine.getName());
            statement.setDouble(3, spaceMarine.getCoordinates().getX());
            statement.setLong(4, spaceMarine.getCoordinates().getY());
            statement.setDouble(5, spaceMarine.getHealth());
            statement.setString(6, spaceMarine.getCategory().toString());
            statement.setString(7, spaceMarine.getWeaponType().toString());
            statement.setString(8, spaceMarine.getMeleeWeapon().toString());
            statement.setString(8, spaceMarine.getChapter().getName());
            statement.setString(9, spaceMarine.getChapter().getParentLegion());
            statement.setInt(10, spaceMarine.getChapter().getMarinesCount());
            statement.executeUpdate();
            statement.close();
        } catch (PSQLException e){
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void fillCollection(SpaceMarineCollection collection) {
        String request = "SELECT*FROM  \"SpaceMarineCollection\"";
        try {
            PreparedStatement statement = connection.prepareStatement(request);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                SpaceMarine element = getElement(result);
                String key = getKey(result);
                collection.insert(key, element);
            }
            statement.close();
            System.out.println("Collection was successfully filled from DB");
        } catch (SQLException throwables) {
            System.out.println("Something went wrong with filling collection from DB");
            System.exit(-1);
        }
    }
    private SpaceMarine getElement(ResultSet result) throws SQLException {
        long id = result.getLong(1);
        String name  = result.getString(3);
        double x = result.getDouble(4);
        double y = result.getDouble(5);
        Coordinates coordinates = new Coordinates(x,(long)y);
        String creationDate = result.getString(6);
        Double health = result.getDouble(7);
        AstartesCategory category = AstartesCategory.valueOf(result.getString(8));
        Weapon weapon = Weapon.valueOf(result.getString(9));
        MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(result.getString(10));
        String chapterName = result.getString(11);
        String chapterLegion = result.getString(12);
        int marinesCount = result.getInt(13);
        Chapter chapter = new Chapter(chapterName, chapterLegion, marinesCount);
        SpaceMarine element = new SpaceMarine(name, coordinates, creationDate, health, category, weapon, meleeWeapon, chapter);
        element.setId(id);

        return element;
    }
    private String getKey(ResultSet result) throws SQLException {
        String key = result.getString(14);
        return key;
    }
    private Long getID(ResultSet result) throws SQLException {
        long id = (Long) result.getLong(1);
        return id;
    }
    public String addNewUser(User user) {
        String request  = "SELECT*FROM  \"Users\"";
        this.user = user;
        try {
            PreparedStatement statement = connection.prepareStatement(request);
            ResultSet result = statement.executeQuery();
            ArrayList<String> usernames = new ArrayList<>();
            ArrayList<String> passwords = new ArrayList<>();

            while (result.next()){
                String name  = result.getString(1);
                String password = result.getString(2);
                usernames.add(name);
                passwords.add(password);
            }
            System.out.println(user.getPassword());
            String[] pas = new String[passwords.size()];
            passwords.toArray(pas);
            if (usernames.contains(user.getUsername())){
                if (toMD5(user.getPassword()).equals(pas[usernames.indexOf(user.getUsername())])) {
                    return "User signed in successfully";
                }else {
                    return "Incorrect password";
                }
            }else{
                String requestForNewUser = "insert into \"Users\" VALUES (?,?)";
                PreparedStatement statementForNewUser = connection.prepareStatement(requestForNewUser);
                statementForNewUser.setString(1,user.getUsername());
                String passwordMD5 = toMD5(user.getPassword());
                statementForNewUser.setString(2,passwordMD5);
                statementForNewUser.executeUpdate();
                return "User was signed";
            }
        } catch (SQLException throwables) {
            return "Something went wrong with authorization";
        }
    }
    private String toMD5(String password) {
        try{
            MessageDigest md  = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger numRepresentation = new BigInteger(1,digest);
            String hashedString  = numRepresentation.toString(16);
            while (hashedString.length()<32){
                hashedString = "0"+hashedString;
            }
            return hashedString;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm");
        }
        return null;
    }
}
