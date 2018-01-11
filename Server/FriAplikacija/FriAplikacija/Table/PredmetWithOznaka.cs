using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FriAplikacija.Table {
    public class PredmetWithOznaka : Predmet{
        public List<Oznaka> oznaka { set; get; }
    }
}