using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FriAplikacija.Table;
using System.Data;
using System.Data.SqlClient;

namespace FriAplikacija.DataAccess {
    public class OcenaPredmetaDataAccess {
        private static String SOURCE = "Server=tcp:friaplikacija.database.windows.net,1433;Initial Catalog=friAplikacija;Persist Security Info=False;User ID=user;Password=friAplikacija1;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

        public static List<OcenaPredmeta> getOcenePredmeta(int predmetID, String sort) {
            switch (sort) {
                case "datum":
                    sort = "k.Datum";
                    break;
                case "splosnaOcena":
                    sort = "o.splosna";
                    break;
                case "uporabnostOcena":
                    sort = "o.uporabnost";
                    break;
                case "tezavnostOcena":
                    sort = "o.tezavnost";
                    break;
                case "zanimivostOcena":
                    sort = "o.zanimivost";
                    break;
                case "OcenaKomentarja":
                    sort = "k.OcenaKomentarja";
                    break;
                default:
                    sort = "o.OcenaPredmetaID";
                    break;
            }
            DataTable data = new DataTable("OcenaPredmeta");
            using (SqlConnection connection = new SqlConnection(SOURCE)) {
                connection.Open();
                using (SqlCommand command = new SqlCommand("SELECT * FROM OcenaPredmeta o join komentar k on (k.KomentarID = o.KomentarID) join Uporabnik u on (u.email = o.email) Where predmetID = @predmetID ORDER BY " + sort + " DESC", connection)) {
                    command.Parameters.Add(new SqlParameter("predmetID", predmetID));
                    command.Parameters.Add(new SqlParameter("sort", sort));
                    using (SqlDataAdapter da = new SqlDataAdapter(command))
                        da.Fill(data);
                }
                connection.Close();
            }
            if (data.Rows.Count >= 1) {
                return rowsToOcene(data);
            }
            return null;
        }

        public static OcenaPredmeta addOcenaPredmeta(int predmetID, String email, String komentar, int splosnaOcena, int uporabnostOcena, int tezavnostOcena, int zanimivostOcena) {
            if (!UporabinkDataAccess.getEmail(email)) {
                return null;
            }
            try {
                using (SqlConnection connection = new SqlConnection(SOURCE)) {
                    connection.Open();
                    DateTime dateTime = DateTime.UtcNow;
                    OcenaPredmeta ocenaPredmeta = new OcenaPredmeta(predmetID, email, splosnaOcena,tezavnostOcena,zanimivostOcena,uporabnostOcena, dateTime);
                    if (komentar.Length > 0) {
                        ocenaPredmeta.komentar = KomentarDataAccess.addKomentar(komentar, dateTime);
                    }
                    using (SqlCommand command = new SqlCommand("INSERT INTO OcenaPredmeta (Email,Splosna,Uporabnost,Tezavnost,Zanimivost,predmetID,komentarID,datum) VALUES (@email,@splosnaOcena,@uporabnostOcena,@tezavnostOcena,@zanimivostOcena,@predmetID,@komentar,@date)", connection)) {
                        command.Parameters.Add(new SqlParameter("email", email));
                        command.Parameters.Add(new SqlParameter("splosnaOcena", ocenaPredmeta.splosnaOcena));
                        command.Parameters.Add(new SqlParameter("uporabnostOcena", ocenaPredmeta.uporabnostOcena));
                        command.Parameters.Add(new SqlParameter("tezavnostOcena", ocenaPredmeta.tezavnostOcena));
                        command.Parameters.Add(new SqlParameter("zanimivostOcena", ocenaPredmeta.zanimivostOcena));
                        command.Parameters.Add(new SqlParameter("predmetID", ocenaPredmeta.predmet.predmetID));
                        command.Parameters.Add(new SqlParameter("date", ocenaPredmeta.date.ToString()));
                        if (ocenaPredmeta.komentar != null) {
                            command.Parameters.Add(new SqlParameter("komentar", ocenaPredmeta.komentar.komentarID));
                        } else {
                            command.Parameters.Add(new SqlParameter("komentar", DBNull.Value));
                        }
                        command.ExecuteNonQuery();
                    }
                    connection.Close();
                    return ocenaPredmeta;
                }
            } catch (Exception e) {
                Console.WriteLine(e.Message);
            }
            return null;
        }

        public static long getSteviloOcenPredmeta(int predmetID) {
            try {
                DataTable data = new DataTable("SteviloOcen");
                using (SqlConnection connection = new SqlConnection(SOURCE)) {
                    connection.Open();
                    using (SqlCommand command = new SqlCommand("SELECT COUNT(*) from OcenaPredmeta where predmetID = @predmetID and KomentarID IS  NOT NULL ", connection)) {
                        command.Parameters.Add(new SqlParameter("predmetID", predmetID));
                        command.ExecuteNonQuery();
                        using (SqlDataAdapter da = new SqlDataAdapter(command))
                            da.Fill(data);
                    }
                    connection.Close();
                    return Int64.Parse(data.Rows[0][0].ToString());
                }
            } catch (Exception e) {
                Console.WriteLine(e.Message);
            }
            return 0;
        }

        private static List<OcenaPredmeta> rowsToOcene(DataTable data) {
            List<OcenaPredmeta> ocene = new List<OcenaPredmeta>();
            foreach (DataRow row in data.Rows) {
                ocene.Add(rowToOcena(row));
            }
            return ocene;
        }

        private static OcenaPredmeta rowToOcena(DataRow row) {
            Uporabnik uporabnik = new Uporabnik();
            Komentar komentar = new Komentar();
            OcenaPredmeta ocena = new OcenaPredmeta();
            uporabnik.email = row["Email"].ToString();
            uporabnik.username = row["Username"].ToString();
            komentar.komentarID = Int32.Parse(row["komentarID"].ToString());
            komentar.komentar = row["Komentar"].ToString();
            komentar.datum = row["Datum"].ToString();
            komentar.ocenaKomentar = Int32.Parse(row["OcenaKomentarja"].ToString());
            ocena.ocenaPredmetaID = Int32.Parse(row["ocenaPredmetaID"].ToString());
            ocena.splosnaOcena = Int32.Parse(row["Splosna"].ToString());
            ocena.uporabnostOcena = Int32.Parse(row["Uporabnost"].ToString());
            ocena.tezavnostOcena = Int32.Parse(row["Tezavnost"].ToString());
            ocena.zanimivostOcena = Int32.Parse(row["Zanimivost"].ToString());
            ocena.uporabnik = uporabnik;
            ocena.komentar = komentar;
            return ocena;
        }
}
}