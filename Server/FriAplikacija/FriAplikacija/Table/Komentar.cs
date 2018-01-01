using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table
{
    public class Komentar
    {
        public Komentar() {
        }

        public Komentar(string komentar, int ocenaKomentar, DateTime datum) {
            this.komentar = komentar;
            this.ocenaKomentar = ocenaKomentar;
            this.datum = datum.ToString();
        }

        public int komentarID { get; set; }

        public String komentar { get; set; }

        public int ocenaKomentar { get; set; }

        public String datum { get; set; }
    }
}