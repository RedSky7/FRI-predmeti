using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table
{
    public class OcenaIzvajalca
    {
        public OcenaIzvajalca() {
        }

        public OcenaIzvajalca(int izvajalec, string email, int splosnaOcena, DateTime date) {
            this.izvajalec = new Izvajalec(izvajalec);
            this.uporabnik = new Uporabnik(email);
            this.splosnaOcena = splosnaOcena;
            this.date = date.ToString();
        }

        public int ocenaIzvajalcaID { get; set; }
        public int splosnaOcena { get; set; }
        public String date { get; set; }
        public Komentar komentar { get; set; }
        public Uporabnik uporabnik { get; set; }
        public Izvajalec izvajalec { get; set; }
    }
}