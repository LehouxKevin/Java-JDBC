import java.sql.Connection;
import java.sql.*;

public class Bdd {
    private String bdd;
    private String url;
    private String user;
    private String password;

    private Connection maConn;
    private Statement myStmt;


    public Bdd(String bdd, String url, String user, String password) {
        this.bdd = bdd;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void setBdd(String bdd) {
        this.bdd = bdd;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void getConn()
    {
        try {
            maConn = DriverManager.getConnection(url+bdd,user,password);
            myStmt = maConn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void executeUpdate(String query)
    {
        try {
            myStmt.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query)
    {
        ResultSet rs = null;
        try {
            rs = myStmt.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }

    public void closeConn()
    {
        try {
            maConn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUserWithName(String query,String name)
    {
        try {
            PreparedStatement preparedStmt = maConn.prepareStatement(query);
            preparedStmt.setString(1,name);
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public String[] getListePersonnesWithScrollableResultSet()
    {
        String sql = "SELECT * FROM intra_cesi.personne";
        String[] tab = {};
        try {
            Statement st = maConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("nom_personne"));
            }

            rs.last();
            tab[0] = rs.getString("nom_personne");
            rs.absolute(3);
            tab[1] = rs.getString("nom_personne");
            rs.last();
            tab[2] = rs.getString("nom_personne");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return tab;
    }
}
