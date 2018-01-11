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
                }
                connection.Close();
                return komentar;
            }
        }

        public static Komentar updateOcenaToKomentar(int komentarID, bool positive) {
            Komentar komentar = getKomentar(komentarID);
            komentar.ocenaKomentar += (positive? 1: -1);
            try {
                using (SqlConnection connection = new SqlConnection(SOURCE)) {
                    connection.Open();
                    using (SqlCommand command = new SqlCommand("Update Komentar SET ocenaKomentarja = @ocenaKomentar WHERE komentarID = @komentarID", connection)) {
                        command.Parameters.Add(new SqlParameter("komentarID", komentarID));
                        command.Parameters.Add(new SqlParameter("ocenaKomentar", komentar.ocenaKomentar));
                        command.ExecuteNonQuery();
                    }
                    connection.Close();
                    return komentar;
                }
            }catch(Exception e) {
                return null;
            }
        }

        private static int getKomentarID(String komentar, DateTime date) {
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

        public static Komentar getKomentar(int komentarID) {
            DataTable data = new DataTable("Komentar");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Komentar WHERE komentarID=@komentarID", connection)) {
                    command.Parameters.Add(new SqlParameter("komentarID", komentarID));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count > 0) {
                return rowToKomentar(data.Rows[0]);
            }
            return null;
        }

        private static Komentar rowToKomentar(DataRow row) {
            Komentar komentar = new Komentar();
            komentar.komentarID = Int32.Parse(row["komentarID"].ToString());
            komentar.komentar = row["Komentar"].ToString();
            komentar.ocenaKomentar = Int32.Parse(row["ocenaKomentarja"].ToString());
            komentar.datum = row["datum"].ToString();
            return komentar;
        }
    }
}