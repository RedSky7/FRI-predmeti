using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace FriAplikacija.DataAccess {
    public class PodrocjeDataAccess {
        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static Podrocje getPodrocje(int podrocjeID) {
            DataTable data = new DataTable("Podrocje");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Podrocje Where podrocjeID = @podrocjeID", connection)) {
                    command.Parameters.Add(new SqlParameter("podrocjeID", podrocjeID));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count == 1) {
                return rowToPodrocje(data.Rows[0]);
            } else {
                return null;
            }
        }

        internal static List<Podrocje> getAllPodrocja() {
            DataTable data = new DataTable("Podrocje");
            using(SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using(SqlCommand command = new SqlCommand("SELECT * FROM Podrocje", connection)) {
                    using(SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if(data.Rows.Count >= 1) {
                return rowsToPodrocja(data);
            }
            return null;
        }

        public static List<Podrocje> getPodrocjeForIzvajalec(int izvajalecID) {
            DataTable data = new DataTable("Podrocje");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("select po.* from Izvaja i join Predmet p on (p.PredmetID = i.PredmetID) join JeIzPodrocja jp on (jp.PredmetID = p.PredmetID) join Podrocje po on(jp.PodrocjeID=po.PodrocjeID) where i.IzvajalecID = @izvajalecID", connection)) {
                    command.Parameters.Add(new SqlParameter("izvajalecID", izvajalecID));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count >= 1) {
                return rowsToPodrocja(data);
            }
            return null;
        }

        private static List<Podrocje> rowsToPodrocja(DataTable data) {
            List<Podrocje> podrocja = new List<Podrocje>();
            foreach(DataRow row in data.Rows) {
                podrocja.Add(rowToPodrocje(row));
            }
            return podrocja;
        }

        private static Podrocje rowToPodrocje(DataRow row) {
            Podrocje podrocje = new Podrocje();
            podrocje.podrocjeID = Int32.Parse(row["podrocjeID"].ToString());
            podrocje.imePodrocja = row["imePodrocja"].ToString();
            return podrocje;
        }
    }
}
