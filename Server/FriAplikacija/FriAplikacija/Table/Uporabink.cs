using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text;
using System.Runtime.Serialization;

namespace FriAplikacija.Table {

    [DataContract]
    public class Uporabnik
    {
        public Uporabnik() {
        }

        public Uporabnik(string email, string username, String geslo, string verificationCode) {
            this.email = email;
            this.username = username;
            this.geslo = geslo;
            this.verificationCode = verificationCode;
        }
        [DataMember]
        public String email {get;set;}
        [DataMember]
        public String username { get; set; }
        [DataMember]
        public String geslo { get; set; }
        [DataMember]
        public String verificationCode { get; set; }
    }
}