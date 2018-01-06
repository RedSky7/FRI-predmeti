using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace FriAplikacija.DataAccess {
    public class IzvajalecPredmetaDataAccess {
        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        internal static List<IzvajalecPredmeta> getPredmetiForIzvajalec(int predmetID) {
            DataTable data = new DataTable("Izvajalec");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT i.*, iz.* FROM Predmet p JOIN Izvaja i ON p.predmetID = i.predmetID JOIN Izvajalec iz ON iz.izvajalecID = i.izvajalecID WHERE p.predmetID = @predmetID", connection)) {
                    command.Parameters.Add(new SqlParameter("predmetID", predmetID));
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

        private static List<IzvajalecPredmeta> rowsToIzvajalci(DataTable data) {
            List<IzvajalecPredmeta> izvajalci = new List<IzvajalecPredmeta>();
            foreach (DataRow row in data.Rows) {
                izvajalci.Add(rowToIzvajalec(row));
            }
            return izvajalci;
        }

        private static IzvajalecPredmeta rowToIzvajalec(DataRow row) {
            IzvajalecPredmeta izvajalec = new IzvajalecPredmeta();
            izvajalec.izvajalecID = Int32.Parse(row["IzvajalecID"].ToString());
            izvajalec.ime = row["ime"].ToString();
            izvajalec.priimek = row["priimek"].ToString();
            izvajalec.slika = row["slika"].ToString();
            izvajalec.opis = row["opis"].ToString();
            izvajalec.naziv = row["naziv"].ToString();
            izvajalec.email = row["email"].ToString();
            izvajalec.splosnaOcena = Decimal.Parse(row["SplosnaOcena"].ToString());
            izvajalec.profesor = Boolean.Parse(row["Profesor"].ToString());
            return izvajalec;
        }
    }
}