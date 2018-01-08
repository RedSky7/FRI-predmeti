using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace FriAplikacija.DataAccess {
    public class IzvajalecForPodrocjeDataAccess {
        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        internal static List<IzvajalecPodrocje> getAllIzvajalciPodrocje() {
            DataTable data = new DataTable("Izvajalec");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Izvajalec i", connection)) {
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

        private static List<IzvajalecPodrocje> rowsToIzvajalci(DataTable data) {
            List<IzvajalecPodrocje> izvajalci = new List<IzvajalecPodrocje>();
            foreach (DataRow row in data.Rows) {
                IzvajalecPodrocje izvajalecForPodrocje = rowToIzvajalec(row);
                izvajalecForPodrocje.podrocja = PodrocjeDataAccess.getPodrocjeForIzvajalec(izvajalecForPodrocje.izvajalecID);
                izvajalci.Add(izvajalecForPodrocje);
            }
            return izvajalci;
        }

        private static IzvajalecPodrocje rowToIzvajalec(DataRow row) {
            IzvajalecPodrocje izvajalec = new IzvajalecPodrocje();
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