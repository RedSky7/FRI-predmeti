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

        public static IzvajalecZStevilomKomentarjev getIzvajalec(int  izvajalecID) {
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
                IzvajalecZStevilomKomentarjev izvajalec = rowToIzvajalecWithNumberOfComent(data.Rows[0]);
                izvajalec.steviloKomentarjev = OcenaIzvajalcaDataAccess.getSteviloOcenIzvajalca(izvajalecID);
                return izvajalec;
            } else {
                return null;
            }
        }

        internal static List<Izvajalec> getAllIzvajalci() {
            DataTable data = new DataTable("Izvajalec");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Izvajalec", connection)) {
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count >= 1) {
                return rowsToIzvajalci(data);
            }
            return null;
        }

        internal static List<Izvajalec> getIzvajalciForOznaka(int oznakaID) {
            DataTable data = new DataTable("Izvajalec");
            using(SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using(SqlCommand command = new SqlCommand("SELECT * FROM Izvajalec i JOIN OznacujeIzvajalca o ON i.izvajalecID = o.izvajalecID JOIN Oznaka oz ON o.oznakaID = oz.oznakaID WHERE o.oznakaID = @oznakaID", connection)) {
                    command.Parameters.Add(new SqlParameter("oznakaID", oznakaID));
                    using(SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if(data.Rows.Count >= 1) {
                return rowsToIzvajalci(data);
            }
            return null;
        }

        internal static List<Izvajalec> getIzvajalciForPodrocje(int podrocjeID) {
            DataTable data = new DataTable("Izvajalec");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("select DISTINCT  iz.* from Izvajalec iz join Izvaja i on (iz.IzvajalecID = i.IzvajalecID) join Predmet p on (p.PredmetID = i.PredmetID) join JeIzPodrocja jp on (jp.PredmetID = p.PredmetID) join Podrocje po on(jp.PodrocjeID=po.PodrocjeID) where po.PodrocjeID = @podrocjeID", connection)) {
                    command.Parameters.Add(new SqlParameter("podrocjeID", podrocjeID));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count >= 1) {
                return rowsToIzvajalci(data);
            }
            return null;
        }

        private static List<Izvajalec> rowsToIzvajalci(DataTable data) {
            List<Izvajalec> izvajalci = new List<Izvajalec>();
            foreach (DataRow row in data.Rows) {
                izvajalci.Add(rowToIzvajalec(row));
            }
            return izvajalci;
        }

        private static Izvajalec rowToIzvajalec(DataRow row) {
            Izvajalec izvajalec = new Izvajalec();
            izvajalec.izvajalecID = Int32.Parse(row["IzvajalecID"].ToString());
            izvajalec.ime = row["ime"].ToString();
            izvajalec.priimek = row["priimek"].ToString();
            izvajalec.slika = row["slika"].ToString();
            izvajalec.opis = row["opis"].ToString();
            izvajalec.naziv = row["naziv"].ToString();
            izvajalec.email = row["email"].ToString();
            izvajalec.splosnaOcena = Decimal.Parse(row["splosnaOcena"].ToString(), System.Globalization.NumberStyles.AllowDecimalPoint);
            return izvajalec;
        }

        private static IzvajalecZStevilomKomentarjev rowToIzvajalecWithNumberOfComent(DataRow row) {
            IzvajalecZStevilomKomentarjev izvajalec = new IzvajalecZStevilomKomentarjev();
            izvajalec.izvajalecID = Int32.Parse(row["IzvajalecID"].ToString());
            izvajalec.ime = row["ime"].ToString();
            izvajalec.priimek = row["priimek"].ToString();
            izvajalec.slika = row["slika"].ToString();
            izvajalec.opis = row["opis"].ToString();
            izvajalec.naziv = row["naziv"].ToString();
            izvajalec.email = row["email"].ToString();
            izvajalec.splosnaOcena = Decimal.Parse(row["splosnaOcena"].ToString(), System.Globalization.NumberStyles.AllowDecimalPoint);
            return izvajalec;
        }
    }
}