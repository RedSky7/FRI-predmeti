using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table
{
    public class OcenaPredmeta
    {
        public OcenaPredmeta() {
        }

        public OcenaPredmeta(int predmet, string email, int splosnaOcena, int tezavnostOcena, int zanimivostOcena, int uporabnostOcena, DateTime date) {
            this.predmet = new Predmet(predmet);
            this.uporabnik = new Uporabnik(email);
            this.splosnaOcena = splosnaOcena;
            this.tezavnostOcena = tezavnostOcena;
            this.zanimivostOcena = zanimivostOcena;
            this.uporabnostOcena = uporabnostOcena;
            this.date = date.ToString();
        }

        public int ocenaPredmetaID { get; set; }
        public int splosnaOcena{ get; set; }
        public int tezavnostOcena { get; set; }
        public int zanimivostOcena { get; set; }
        public int uporabnostOcena { get; set; }
        public String date { get; set; }
        public Komentar komentar { get; set; }
        public Uporabnik uporabnik { get; set; }
        public Predmet predmet { get; set; }
    }
}