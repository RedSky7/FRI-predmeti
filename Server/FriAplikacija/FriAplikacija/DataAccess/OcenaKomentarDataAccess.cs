using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;
using System.Text;


namespace FriAplikacija.DataAccess {
    public class OcenaKomentarDataAccess {

        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static Komentar addKomentar(String email, int komentarID, bool positive) {
            try {
                using (SqlConnection connection = new SqlConnection(SOURCE)) {
                    connection.Open();
                    using (SqlCommand command = new SqlCommand("INSERT INTO OcenaKomentar (KomentarID,Email,Ocena) VALUES (@komentarID,@email,@ocena)", connection)) {
                        command.Parameters.Add(new SqlParameter("komentarID", komentarID));
                        command.Parameters.Add(new SqlParameter("email", email));
                        command.Parameters.Add(new SqlParameter("ocena", positive));
                        command.ExecuteNonQuery();
                    }
                    connection.Close();
                    return KomentarDataAccess.updateOcenaToKomentar(komentarID, positive);
                }
            }
            catch(Exception e) {
                return null;
            }
        }
    }
}