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

        public static Uporabnik login(String email, String geslo) {
            DataTable data = new DataTable("Uporabnik");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Uporabnik Where email = @email and geslo = @geslo and VerCode = \"\"", connection)) {
                    command.Parameters.Add(new SqlParameter("email", email));
                    command.Parameters.Add(new SqlParameter("geslo", geslo));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count == 1) {
                Uporabnik uporabnik = rowToUporabnik(data.Rows[0]);
                return uporabnik;
            } else {
                return null;
            }
        }

        public static Uporabnik register(String email, String geslo, String uporabniskoIme) {
            if (getEmail(email)) {
                return null;
            }
            try {
                String verificationCode = createVerification();
                String hashPassword = BCrypt.Net.BCrypt.HashPassword(geslo + "^Y8~JJ", BCrypt.Net.BCrypt.GenerateSalt());
                Uporabnik uporabink = new Uporabnik(email, uporabniskoIme, hashPassword, verificationCode);
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
            } catch (SaltParseException e) {
                Console.WriteLine("BCrypt exception:" + e);
            } catch(Exception e) {
                Console.WriteLine(e.Message);
            }
            return null;
        }

        public static Uporabnik accountVerification(String email, String verCode) {
            if (accountVerCodeVerification(email,verCode)) {
                return null;
            }
            try {
                using (SqlConnection connection = new SqlConnection(SOURCE)) {
                    connection.Open();
                    using (SqlCommand command = new SqlCommand("Update Uporabnik SET VerCode = \"\" WHERE email = @email", connection)) {
                        command.Parameters.Add(new SqlParameter("email", email));
                        command.ExecuteNonQuery();
                    }
                    connection.Close();
                }
                return getUporabnik(email);
            } catch (SaltParseException e) {
                Console.WriteLine("BCrypt exception:" + e);
            } catch (Exception e) {
                Console.WriteLine(e.Message);
            }
            return null;
        }

        private static Boolean accountVerCodeVerification(String email, String verCode) {
            DataTable data = new DataTable("Uporabnik");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Uporabnik Where Email = @email and VerCode = @verCode", connection)) {
                    command.Parameters.Add(new SqlParameter("email", email));
                    command.Parameters.Add(new SqlParameter("verCode", verCode));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count > 0) {
                return true;
            }
            return false;
        }

        private static Uporabnik getUporabnik(String email) {
            DataTable data = new DataTable("Uporabnik");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Uporabnik Where email = @email", connection)) {
                    command.Parameters.Add(new SqlParameter("email", email));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count == 1) {
                Uporabnik uporabnik = rowToUporabnik(data.Rows[0]);
                return uporabnik;
            } else {
                return null;
            }
        }

        private static Boolean getEmail(String email) {
            DataTable data = new DataTable("Uporabnik");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Uporabnik Where Email = @email and VerCode = \"\"", connection)) {
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

        private static Uporabnik rowToUporabnik(DataRow row) {
            Uporabnik uporabnik = new Uporabnik();
            uporabnik.email = row["Email"].ToString();
            uporabnik.username = row["Username"].ToString();
            uporabnik.verificationCode = row["VerCode"].ToString();
            return uporabnik;
        }

        private static String createVerification() {
            Random random = new Random();
            StringBuilder verificationCode = new StringBuilder();
            const string chars = "abcdefghijklmnopqrstuvwyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            return new string(Enumerable.Repeat(chars, 32).Select(s => s[random.Next(s.Length)]).ToArray());
        }
    }
}