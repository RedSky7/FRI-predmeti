using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table
{
    public class Predmet
    {
        public String ime { get; set; }
        public decimal splosnaOcena { get; set; }
        public decimal tezavnostOcena { get; set; }
        public decimal zanimivostOcena { get; set; }
        public decimal uporabnostOcena { get; set; }
        public String ocena { get; set; }
    }
}