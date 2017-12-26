using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table
{
    public class OcenaIzvajalca
    {
        public OcenaIzvajalca(int izvajalec, string email, int splosnaOcena, Komentar komentar) {
            this.izvajalec.izvajalecID = izvajalec;
            this.uporabnik.email = email;
            this.splosnaOcena = splosnaOcena;
            this.komentar = komentar;
        }

        public int splosnaOcena { get; set; }

        public Komentar komentar { get; set; }

        public Uporabnik uporabnik { get; set; }

        public Izvajalec izvajalec { get; set; }
    }
}