using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table
{
    public class Komentar
    {
        public Komentar(string komentar, int ocenaKomentar, DateTime now) {
            this.komentar = komentar;
            this.ocenaKomentar = ocenaKomentar;
            this.datum = datum;
        }

        public String komentar { get; set; }

        public int ocenaKomentar { get; set; }

        public DateTime datum { get; set; } 
    }
}