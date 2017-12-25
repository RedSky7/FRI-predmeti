using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace FriAplikacija.DataAccess {
    public class IzvajalecDataAccess {
        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static Izvajalec getIzvajalec(int  izvajalecID) {
            DataTable data = new DataTable("Izvajalec");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Izvajalec Where izvajalecID = @izvajalecID", connection)) {
                    command.Parameters.Add(new SqlParameter("izvajalecID", izvajalecID));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count == 1) {
                Izvajalec uporabnik = rowToIzvajalec(data.Rows[0]);
                return uporabnik;
            } else {
                return null;
            }
        }

        private static Izvajalec rowToIzvajalec(DataRow row) {
            Izvajalec izvajalec = new Izvajalec();
            izvajalec.izvajalecID = Int32.Parse(row["IzvajalecID"].ToString());
            izvajalec.ime = row["ime"].ToString();
            izvajalec.priimek = row["priimek"].ToString();
            izvajalec.slika = row["slika"].ToString();
            izvajalec.opis = row["opis"].ToString();
            izvajalec.naziv = row["naziv"].ToString();
            return izvajalec;
        }
    }
}