using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using FriAplikacija.Table;
using FriAplikacija.DataAccess;
using System.ServiceModel.Web;

namespace FriAplikacija
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service.svc or Service.svc.cs at the Solution Explorer and start debugging.
    public class Service : IService {
        public Uporabnik Login() {
            WebOperationContext ctx = WebOperationContext.Current;
            String email = ctx.IncomingRequest.Headers["email"]; ;
            String geslo = ctx.IncomingRequest.Headers["geslo"];
            Uporabnik uporabnik = UporabinkDataAccess.login(email, geslo);
            if (uporabnik.verificationCode.Length == 0) {
                return uporabnik;
            }
            return null;
        }

        public Uporabnik Register() {
            WebOperationContext ctx = WebOperationContext.Current;
            String email = ctx.IncomingRequest.Headers["email"]; ;
            String geslo = ctx.IncomingRequest.Headers["geslo"];
            String uporabniskoIme = ctx.IncomingRequest.Headers["uporabniskoIme"];
            Uporabnik uporanik = UporabinkDataAccess.register(email,geslo,uporabniskoIme);
            return uporanik;
        }
    
        public Uporabnik Verify() {
            WebOperationContext ctx = WebOperationContext.Current;
            String email = ctx.IncomingRequest.Headers["email"]; ;
            String verCode = ctx.IncomingRequest.Headers["verCode"];
            Uporabnik uporanik = UporabinkDataAccess.accountVerification(email, verCode);
            return uporanik;
        }
    }
}
