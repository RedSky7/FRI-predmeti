using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;
using System.Text;
using BCrypt.Net;

namespace FriAplikacija.DataAccess
{
    public class UporabinkDataAccess
    {
        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static Uporabink register(String email, String geslo, String uporabniskoIme) {
            if (getEmail(email)) {
                return null;
            }
            try {
                String verificationCode = createVerification();
                String hashPassword = BCrypt.Net.BCrypt.HashPassword(geslo + "^Y8~JJ", BCrypt.Net.BCrypt.GenerateSalt());
                Uporabink uporabink = new Uporabink(email, uporabniskoIme, hashPassword, verificationCode);
                using (SqlConnection connection = new SqlConnection(SOURCE)) {
                    connection.Open();
                    using (SqlCommand command = new SqlCommand("INSERT INTO Uporabnik (Email,Username,Geslo,VerCode) VALUES (@email,@username,@geslo,@verCode)", connection)) {
                        command.Parameters.Add(new SqlParameter("email", uporabink.email));
                        command.Parameters.Add(new SqlParameter("username", uporabink.username));
                        command.Parameters.Add(new SqlParameter("geslo", uporabink.geslo));
                        command.Parameters.Add(new SqlParameter("verCode", uporabink.verificationCode));
                        command.ExecuteNonQuery();
                    }
                    connection.Close();
                }
                return uporabink;
            } catch (BCrypt.Net.SaltParseException e) {
                Console.WriteLine("BCrypt exception:" + e);
            }
            return null;
        }

        public static Boolean getEmail(String email) {
            DataTable data = new DataTable("Uporabnik");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Uporabnik Where Email = @email", connection)) {
                    command.Parameters.Add(new SqlParameter("email", email));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if(data.Rows.Count > 0) {
                return true;
            }
            return false;
        }

        private static String createVerification() {
            Random random = new Random();
            StringBuilder verificationCode = new StringBuilder();
            const string chars = "abcdefghijklmnopqrstuvwyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            return new string(Enumerable.Repeat(chars, 32).Select(s => s[random.Next(s.Length)]).ToArray());
        }
    }
}