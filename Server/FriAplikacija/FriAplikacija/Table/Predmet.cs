using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table
{
    public class Predmet
    {
        public Predmet() {
        }

        public Predmet(int predmet) {
            this.predmetID = predmet;
        }

        public int predmetID { get; set; }
        public String ime { get; set; }
        public decimal splosnaOcena { get; set; }
        public decimal tezavnostOcena { get; set; }
        public decimal zanimivostOcena { get; set; }
        public decimal uporabnostOcena { get; set; }
        public String ocena { get; set; }
        public List<IzvajalecPredmeta> izvajalci { get; set; }
        public List<Predmet> predpogoj { get; set; }
        public long steviloKomentarjev { get; set; }
    }
}