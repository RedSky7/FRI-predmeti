using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table
{
    public class Izvajalec
    {
        public Izvajalec() {
        }

        public Izvajalec(int izvajalec) {
            this.izvajalecID = izvajalec;
        }
   
        public int izvajalecID { get; set; }
        public String email { get; set; }
        public String ime { get; set; }
        public String priimek { get; set; }
        public String slika { get; set; }
        public Decimal splosnaOcena { get; set; }
        public String opis { get; set; }
        public String naziv { get; set; }
    }
}