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
using System.Collections.Specialized;


namespace FriAplikacija
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service.svc or Service.svc.cs at the Solution Explorer and start debugging.
    public class Service : IService {
        public Boolean CheckEmail() {
            WebOperationContext ctx = WebOperationContext.Current;
            String email = ctx.IncomingRequest.Headers["email"].ToString();
            return UporabinkDataAccess.getEmail(email);
        }
        public Uporabnik Login() {
            WebOperationContext ctx = WebOperationContext.Current;
            String email = ctx.IncomingRequest.Headers["email"].ToString();
            String geslo = ctx.IncomingRequest.Headers["geslo"].ToString();
            return UporabinkDataAccess.login(email, geslo);
        }

        public Uporabnik Register() {
            WebOperationContext ctx = WebOperationContext.Current;
            String email = ctx.IncomingRequest.Headers["email"].ToString();
            String geslo = ctx.IncomingRequest.Headers["geslo"].ToString();
            String uporabniskoIme = ctx.IncomingRequest.Headers["uporabniskoIme"].ToString();
            return UporabinkDataAccess.register(email,geslo,uporabniskoIme);
        }
    
        public Uporabnik Verify() {
            WebOperationContext ctx = WebOperationContext.Current;
            String email = ctx.IncomingRequest.Headers["email"].ToString();
            String verCode = ctx.IncomingRequest.Headers["verCode"].ToString();
            return UporabinkDataAccess.accountVerification(email, verCode);
        }

        public void Verify1() {
            NameValueCollection queryStringCol = WebOperationContext.Current.IncomingRequest.UriTemplateMatch.QueryParameters;
            if(queryStringCol.Count == 2) {
                String email = queryStringCol[0];
                String verCode = queryStringCol[1];
                UporabinkDataAccess.accountVerification(email, verCode);
            }
        }

        public IzvajalecZStevilomKomentarjev GetIzvajalec() {
            WebOperationContext ctx = WebOperationContext.Current;
            int izvajalecID = Int32.Parse(ctx.IncomingRequest.Headers["izvajalecID"].ToString());
            return IzvajalecDataAccess.getIzvajalec(izvajalecID);
        }

        public List<Izvajalec> AllIzvajalci() {
            WebOperationContext ctx = WebOperationContext.Current;
            return IzvajalecDataAccess.getAllIzvajalci();
        }

        public List<IzvajalecPredmeta> IzvajalecForPredmet() {
            WebOperationContext ctx = WebOperationContext.Current;
            int komentarID = Int32.Parse(ctx.IncomingRequest.Headers["predmetID"]);
            return IzvajalecPredmetaDataAccess.getPredmetiForIzvajalec(komentarID);
        }

        public List<Izvajalec> IzvajalciForOznaka() {
            WebOperationContext ctx = WebOperationContext.Current;
            int oznakaID = Int32.Parse(ctx.IncomingRequest.Headers["oznakaID"]);
            return IzvajalecDataAccess.getIzvajalciForOznaka(oznakaID);
        }

        public List<IzvajalecPodrocje> AllIzvajalciPodrocje() {
            WebOperationContext ctx = WebOperationContext.Current;
            return IzvajalecForPodrocjeDataAccess.getAllIzvajalciPodrocje();
        }

        public List<Izvajalec> AllIzvajalciForPodrocje() {
            WebOperationContext ctx = WebOperationContext.Current;
            int podrocjeID = Int32.Parse(ctx.IncomingRequest.Headers["podrocjeID"]);
            return IzvajalecDataAccess.getIzvajalciForPodrocje(podrocjeID);
        }

        public List<OcenaIzvajalca> GetKomentarIzvajalec() {
            WebOperationContext ctx = WebOperationContext.Current;
            int izvajalecID = Int32.Parse(ctx.IncomingRequest.Headers["izvajalecID"].ToString());
            String sort = ctx.IncomingRequest.Headers["sort"].ToString();
            return OcenaIzvajalcaDataAccess.getOceneIzvajalca(izvajalecID, sort);
        }

        public OcenaIzvajalca SetKomentarIzvajalec() {
            WebOperationContext ctx = WebOperationContext.Current;
            int izvajalecID = Int32.Parse(ctx.IncomingRequest.Headers["izvajalecID"].ToString());
            String komentar = "";
            if (ctx.IncomingRequest.Headers["komentar"] != null) {
                komentar = ctx.IncomingRequest.Headers["komentar"].ToString();
            }
            int splosnaOcena = Int32.Parse(ctx.IncomingRequest.Headers["splosnaOcena"].ToString());
            String email = ctx.IncomingRequest.Headers["email"].ToString();
            return OcenaIzvajalcaDataAccess.addOcenaIzvajalca(izvajalecID, email, komentar, splosnaOcena);
        }

        public Predmet GetPredmet() {
            WebOperationContext ctx = WebOperationContext.Current;
            int predmetID = Int32.Parse(ctx.IncomingRequest.Headers["predmetID"]);
            return PredmetDataAccess.getPredmet(predmetID);
        }
        public PredmetWithOznaka GetPredmetWithOznaka() {
            WebOperationContext ctx = WebOperationContext.Current;
            int predmetID = Int32.Parse(ctx.IncomingRequest.Headers["predmetID"]);
            return PredmetDataAccess.getPredmetWithOznaka(predmetID);
        }

        public List<Predmet> AllPredmeti() {
            WebOperationContext ctx = WebOperationContext.Current;
            return PredmetDataAccess.getAllPredmeti();
        }

        public List<Predmet> PredmetiForIzvajalec() {
            WebOperationContext ctx = WebOperationContext.Current;
            int izvajalecID = Int32.Parse(ctx.IncomingRequest.Headers["izvajalecID"]);
            return PredmetDataAccess.getPredmetiForIzvajalec(izvajalecID);
        }

        public List<Predmet> PredmetiForPodrocje() {
            WebOperationContext ctx = WebOperationContext.Current;
            int podrocjeID = Int32.Parse(ctx.IncomingRequest.Headers["podrocjeID"]);
            return PredmetDataAccess.getPredmetiForPodrocje(podrocjeID);
        }

        public List<Predmet> PredmetiForOznaka() {
            WebOperationContext ctx = WebOperationContext.Current;
            int oznakaID = Int32.Parse(ctx.IncomingRequest.Headers["oznakaID"]);
            return PredmetDataAccess.getPredmetiForOznaka(oznakaID);
        }

        public List<OcenaPredmeta> GetKomentarPredmet() {
            WebOperationContext ctx = WebOperationContext.Current;
            int predmetID = Int32.Parse(ctx.IncomingRequest.Headers["predmetID"].ToString());
            String sort = ctx.IncomingRequest.Headers["sort"].ToString();
            return OcenaPredmetaDataAccess.getOcenePredmeta(predmetID, sort);
        }

        public OcenaPredmeta SetKomentarPredmet() {
            WebOperationContext ctx = WebOperationContext.Current;
            int predmetID = Int32.Parse(ctx.IncomingRequest.Headers["predmetID"].ToString());
            String komentar = "";
            if (ctx.IncomingRequest.Headers["komentar"] != null) {
                komentar = ctx.IncomingRequest.Headers["komentar"].ToString();
            }
            int splosnaOcena = Int32.Parse(ctx.IncomingRequest.Headers["splosnaOcena"].ToString());
            int uporabnostOcena = Int32.Parse(ctx.IncomingRequest.Headers["uporabnostOcena"].ToString());
            int tezavnostOcena = Int32.Parse(ctx.IncomingRequest.Headers["tezavnostOcena"].ToString());
            int zanimivostOcena = Int32.Parse(ctx.IncomingRequest.Headers["zanimivostOcena"].ToString());
            String email = ctx.IncomingRequest.Headers["email"].ToString();
            return OcenaPredmetaDataAccess.addOcenaPredmeta(predmetID, email, komentar, splosnaOcena, uporabnostOcena, tezavnostOcena, zanimivostOcena);
        }

        public Komentar AddOcenaKomentar() {
            WebOperationContext ctx = WebOperationContext.Current;
            String email = ctx.IncomingRequest.Headers["email"].ToString();
            int komentarID = Int32.Parse(ctx.IncomingRequest.Headers["komentarID"]);
            bool positive = Boolean.Parse(ctx.IncomingRequest.Headers["positive"]);
            return OcenaKomentarDataAccess.addKomentar(email, komentarID, positive);
        }

        public Podrocje GetPodrocje() {
            WebOperationContext ctx = WebOperationContext.Current;
            int podrocjeID = Int32.Parse(ctx.IncomingRequest.Headers["podrocjeID"]);
            return PodrocjeDataAccess.getPodrocje(podrocjeID);
        }

        public List<Podrocje> AllPodrocja() {
            WebOperationContext ctx = WebOperationContext.Current;
            return PodrocjeDataAccess.getAllPodrocja();
        }

        public Oznaka GetOznaka() {
            WebOperationContext ctx = WebOperationContext.Current;
            int oznakaID = Int32.Parse(ctx.IncomingRequest.Headers["oznakaID"]);
            return OznakaDataAccess.getOznaka(oznakaID);
        }

        public List<Oznaka> AllOznake() {
            WebOperationContext ctx = WebOperationContext.Current;
            return OznakaDataAccess.getAllOznake();
        }
    }
}
