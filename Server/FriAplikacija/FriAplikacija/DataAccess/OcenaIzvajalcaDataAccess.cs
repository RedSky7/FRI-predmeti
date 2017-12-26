using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;

namespace FriAplikacija.DataAccess {
    public class OcenaIzvajalcaDataAccess {

        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static List<OcenaIzvajalca> getOceneIzvajalca(int izvajalecID) {
            DataTable data = new DataTable("OcenaIzvajalca");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM OcenaIzvajalca o join komentar k on (k.KomentarID = o.KomentarID) join Uporabnik u on (u.email = o.email) Where izvajalecID = @izvajalecID", connection)) {
                    command.Parameters.Add(new SqlParameter("izvajalecID", izvajalecID));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count >= 1) {
                List<OcenaIzvajalca> ocene = rowsToOcene(data);
                return ocene;
            }
            return null;
        }

        public static OcenaIzvajalca addOcenaIzvajalca(int izvajalecID, String email, String komentar, int splosnaOcena) {
            if (UporabinkDataAccess.getEmail(email)) {
                return null;
            }
            try {

                using (SqlConnection connection = new SqlConnection(SOURCE)) {
                    connection.Open();
                    OcenaIzvajalca ocenaIzvajalca = new OcenaIzvajalca(izvajalecID, email, splosnaOcena, null);
                    if (komentar.Length > 0) {
                        Komentar komentarS = new Komentar(komentar, 0, DateTime.Now);
                        using (SqlCommand command = new SqlCommand("INSERT INTO Komentar (Komentar,OcenaKomentar,Datum) VALUES (@komentar,@ocenaKomentar,@date)", connection)) {
                            command.Parameters.Add(new SqlParameter("komentar", komentarS.komentar));
                            command.Parameters.Add(new SqlParameter("ocenaKomentar", komentarS.ocenaKomentar));
                            command.Parameters.Add(new SqlParameter("date", komentarS.datum));
                            command.ExecuteNonQuery();
                            ocenaIzvajalca.komentar = komentarS;
                        }
                    }
                    using (SqlCommand command = new SqlCommand("INSERT INTO OcenaIzvajalca (Email,SplosnaOcena,IzvajalecID,komentar) VALUES (@email,@splosnaOcena,@izvajalecID,@komentar)", connection)) {
                        command.Parameters.Add(new SqlParameter("email", email));
                        command.Parameters.Add(new SqlParameter("splosnaOcena", ocenaIzvajalca.splosnaOcena));
                        command.Parameters.Add(new SqlParameter("izvajalecID", ocenaIzvajalca.izvajalec));
                        command.Parameters.Add(new SqlParameter("komentar", ocenaIzvajalca.komentar != null));
                        command.ExecuteNonQuery();
                    }
                    connection.Close();
                    return ocenaIzvajalca;
                }
            } catch (Exception e) {
                Console.WriteLine(e.Message);
            }
            return null;
        }

        private static List<OcenaIzvajalca> rowsToOcene(DataTable data) {
            List<OcenaIzvajalca> ocene = new List<OcenaIzvajalca>();

            foreach(DataRow row in data.Rows){
                ocene.Add(rowToOcena(row));
            }

            return ocene;
        }

        private static OcenaIzvajalca rowToOcena(DataRow row) {
            Uporabnik uporabnik = new Uporabnik();
            Komentar komentar = new Komentar();
            OcenaIzvajalca ocena = new OcenaIzvajalca();
            uporabnik.email = row["Email"].ToString();
            uporabnik.username = row["Username"].ToString();
            komentar.komentar = row["Komentar"].ToString();
            komentar.datum = DateTime.Parse(row["Datum"].ToString());
            ocena.splosnaOcena = Int32.Parse(row["SplosnaOcena"].ToString());
            ocena.uporabnik = uporabnik;
            ocena.komentar = komentar;
            return ocena;
        }
    }
}