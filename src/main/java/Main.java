import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Properties prop = readPropertiesFile("src\\main\\resources\\config.properties");
        Bdd bdd = new Bdd( prop.getProperty("bdd"),prop.getProperty("url"),prop.getProperty("username"),prop.getProperty("password"));
        bdd.getConn();

        int choix = 0;
        while(choix != 9) {

            choix = 9;
            Scanner sc = new Scanner(System.in);

            System.out.println("Liste des personnes :");

            try {
                String sql = "select * from intra_cesi.personne";
                ResultSet rs = bdd.executeQuery(sql);

                if(rs != null)
                {
                    while(rs.next()) {
                        System.out.println(rs.getString("prenom_personne") + " " + rs.getString("nom_personne"));
                    }
                }

                rs.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            System.out.print( "Que souhaitez-vous faire ?\n" );
            System.out.print( "1 - Insérer une personne\n" );
            System.out.print( "2 - Supprimer une personne\n" );
            System.out.print( "3 - Afficher la liste des personnes\n" );
            System.out.print( "9 - Quitter\n" );

            choix = sc.nextInt();
            sc.nextLine();


            if(choix == 1) {

                System.out.print( "Prénom\n" );
                String prenom = sc.nextLine();
                System.out.print( "Nom\n" );
                String nom = sc.nextLine();

                String sql = "INSERT INTO intra_cesi.personne (nom_personne , prenom_personne, e_mail_personne, dateNaissance_personne, password_personne, avatar_personne, description_personne,centres_interets_personne,sexe_personne,id_role,id_promotion,adresse_ip_personne,lienFacebook_personne,lienTwitter_personne,lienLinkIn_personne,lienInstagram_personne,telephone_personne,fond_ecran_profil_personne) VALUES ('"+nom+"','"+prenom+"','Entrée Par Java','01/01/1970','','','','','','1','1','','','','','','','');";
                bdd.executeUpdate(sql);
            }
            else if (choix == 2) {
                System.out.print( "Nom de la personne à supprimer\n" );
                String nom = sc.nextLine();
                String sql = "DELETE FROM intra_cesi.personne WHERE nom_personne = ?";
                bdd.deleteUserWithName(sql,nom);

            }
            else if (choix == 3) {
                String[] tab = bdd.getListePersonnesWithScrollableResultSet();
                System.out.println(tab);
            }
            else {
                System.out.println("Choissez une option par un chiffre (ex : 1)");
            }
        }
        bdd.closeConn();
    }

    private static Properties readPropertiesFile(String fileName) {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }
}
