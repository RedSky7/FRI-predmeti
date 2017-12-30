using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace FriAplikacija.DataAccess {
    public class PredmetDataAccess {
        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static Predmet getPredmet(int predmetID) {
            DataTable data = new DataTable("Predmet");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Predmet Where predmetID = @predmetID", connection)) {
                    command.Parameters.Add(new SqlParameter("predmetID", predmetID));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count == 1) {
                Predmet predmet = rowToPredmet(data.Rows[0]);
                return predmet;
            } else {
                return null;
            }
        }

        internal static List<Predmet> getAllPredmeti() {
            DataTable data = new DataTable("Predmet");
            using(SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using(SqlCommand command = new SqlCommand("SELECT * FROM Predmet", connection)) {
                    using(SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if(data.Rows.Count >= 1) {
                return rowsToPredmeti(data);
            }
            return null;
        }

        private static List<Predmet> rowsToPredmeti(DataTable data) {
            List<Predmet> predmeti = new List<Predmet>();
            foreach(DataRow row in data.Rows) {
                predmeti.Add(rowToPredmet(row));
            }
            return predmeti;
        }

        private static Predmet rowToPredmet(DataRow row) {
            Predmet predmet = new Predmet();
            predmet.predmetID = Int32.Parse(row["predmetID"].ToString());
            predmet.ime = row["ime"].ToString();
            predmet.splosnaOcena = Decimal.Parse(row["splosnaOcena"].ToString());
            predmet.tezavnostOcena = Decimal.Parse(row["tezavnostOcena"].ToString());
            predmet.zanimivostOcena = Decimal.Parse(row["zanimivostOcena"].ToString());
            predmet.uporabnostOcena = Decimal.Parse(row["uporabnostOcena"].ToString());
            predmet.ocena = row["opis"].ToString();
            return predmet;
        }
    }
}