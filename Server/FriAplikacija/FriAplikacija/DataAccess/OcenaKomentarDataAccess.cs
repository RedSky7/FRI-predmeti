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
            int value = allreadyExists(email, komentarID, positive);
            if (value == -1) {
                return KomentarDataAccess.getKomentar(komentarID);
            } else {
                if (value == 0) {
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
                        }
                    } catch (Exception e) {
                        return null;
                    }
                } else {
                    try {
                        using (SqlConnection connection = new SqlConnection(SOURCE)) {
                            connection.Open();
                            using (SqlCommand command = new SqlCommand("UPDATE OcenaKomentar SET ocena = @Ocena WHERE komentarID=@komentarID and email=@email", connection)) {
                                command.Parameters.Add(new SqlParameter("komentarID", komentarID));
                                command.Parameters.Add(new SqlParameter("email", email));
                                command.Parameters.Add(new SqlParameter("ocena", positive));
                                command.ExecuteNonQuery();
                            }
                            connection.Close();
                        }
                    } catch (Exception e) {
                        return null;
                    }
                }
                return KomentarDataAccess.updateOcenaToKomentar(komentarID, positive);
            }
        }

        private static int allreadyExists(String email, int komentarID, bool positive) {
            DataTable data = new DataTable("OcenaKomenta");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM OcenaKomentar WHERE komentarID=@komentarID and email=@email", connection)) {
                    command.Parameters.Add(new SqlParameter("komentarID", komentarID));
                    command.Parameters.Add(new SqlParameter("email", email));
                    command.ExecuteNonQuery();
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
                if (data.Rows.Count > 0) {
                    if(Boolean.Parse(data.Rows[0]["Ocena"].ToString()) == positive) {
                        return -1;
                    }
                    return 1;
                }
            }
            return 0;
        }
    }
}