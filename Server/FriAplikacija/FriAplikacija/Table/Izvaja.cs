using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table
{
    public class Izvaja
    {
        public Boolean profesor { get; set; }
        public Izvajalec izvajalec { get; set; }
        public Predmet predmet { get; set; }
    }
}