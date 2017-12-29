using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;


namespace FriAplikacija.DataAccess {
    public class KomentarDataAccess {

        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static Komentar addKomentar(String komentarString, DateTime dateTime) {
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                Komentar komentar = new Komentar(komentarString, 0, dateTime);
                using (SqlCommand command = new SqlCommand("INSERT INTO Komentar (Komentar,OcenaKomentarja,Datum) VALUES (@komentar,@ocenaKomentar,@date)", connection)) {
                    command.Parameters.Add(new SqlParameter("komentar", komentar.komentar));
                    command.Parameters.Add(new SqlParameter("ocenaKomentar", komentar.ocenaKomentar));
                    command.Parameters.Add(new SqlParameter("date", komentar.datum.ToString()));
                    command.ExecuteNonQuery();
                    komentar.komentarID = getKomentarID(komentarString, dateTime);
                    return komentar;
                }
            }
        }

        public static int getKomentarID(String komentar, DateTime date) {
            DataTable data = new DataTable("Komentar");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT KomentarID FROM Komentar WHERE Datum=@date and komentar=@komentar", connection)) {
                    command.Parameters.Add(new SqlParameter("komentar", komentar));
                    command.Parameters.Add(new SqlParameter("date", date.ToString()));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count > 0) {
                return Int32.Parse(data.Rows[0][0].ToString());
            }
            return -1;
        }
    }
}