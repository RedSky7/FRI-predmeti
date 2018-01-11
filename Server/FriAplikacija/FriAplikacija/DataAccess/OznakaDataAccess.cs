using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace FriAplikacija.DataAccess {
    public class OznakaDataAccess {
        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static Oznaka getOznaka(int oznakaID) {
            DataTable data = new DataTable("Oznaka");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM Oznaka Where oznakaID = @oznakaID", connection)) {
                    command.Parameters.Add(new SqlParameter("oznakaID", oznakaID));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count == 1) {
                return rowToOznaka(data.Rows[0]);
            } else {
                return null;
            }
        }

        internal static List<Oznaka> getAllOznake() {
            DataTable data = new DataTable("Oznaka");
            using(SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using(SqlCommand command = new SqlCommand("SELECT * FROM Oznaka", connection)) {
                    using(SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if(data.Rows.Count >= 1) {
                return rowsToOznake(data);
            }
            return null;
        }

        internal static List<Oznaka> getOznakeForPredmet(int predmetID) {
            DataTable data = new DataTable("Oznaka");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("select * from Oznaka o join OznacujePredmet op on (o.oznakaID = op.oznakaID) where predmetID = @predmetID", connection)) {
                    command.Parameters.Add(new SqlParameter("predmetID", predmetID));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count >= 1) {
                return rowsToOznake(data);
            }
            return null;
        }

        private static List<Oznaka> rowsToOznake(DataTable data) {
            List<Oznaka> oznake = new List<Oznaka>();
            foreach(DataRow row in data.Rows) {
                oznake.Add(rowToOznaka(row));
            }
            return oznake;
        }

        private static Oznaka rowToOznaka(DataRow row) {
            Oznaka oznaka = new Oznaka();
            oznaka.OznakaID = Int32.Parse(row["oznakaID"].ToString());
            oznaka.Ime = row["ime"].ToString();
            return oznaka;
        }
    }
}