using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using FriAplikacija.Table;

namespace FriAplikacija
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService" in both code and config file together.
    [ServiceContract]
    public interface IService
    {
        //Uporabnik
        [OperationContract]
        [WebGet(UriTemplate = "Login", ResponseFormat = WebMessageFormat.Json)]
        Uporabnik Login();

        [OperationContract]
        [WebGet(UriTemplate = "Register", ResponseFormat = WebMessageFormat.Json)]
        Uporabnik Register();

        [OperationContract]
        [WebGet(UriTemplate = "Verify", ResponseFormat = WebMessageFormat.Json)]
        Uporabnik Verify();

        //Izvajalec
        [OperationContract]
        [WebGet(UriTemplate = "Izvajalec", ResponseFormat = WebMessageFormat.Json)]
        Izvajalec GetIzvajalec();

        [OperationContract]
        [WebGet(UriTemplate = "AllIzvajalci", ResponseFormat = WebMessageFormat.Json)]
        List<Izvajalec> AllIzvajalci();

        [OperationContract]
        [WebGet(UriTemplate = "IzvajalecForPredmet", ResponseFormat = WebMessageFormat.Json)]
        List<IzvajalecPredmeta> IzvajalecForPredmet();

        //OcenaIzvajalec
        [OperationContract]
        [WebGet(UriTemplate = "GetKomentarIzvajalec", ResponseFormat = WebMessageFormat.Json)]
        List<OcenaIzvajalca> GetKomentarIzvajalec();

        [OperationContract]
        [WebGet(UriTemplate = "KomentirajIzvaj", ResponseFormat = WebMessageFormat.Json)]
        OcenaIzvajalca SetKomentarIzvajalec();

        //Predmet
        [OperationContract]
        [WebGet(UriTemplate = "Predmet", ResponseFormat = WebMessageFormat.Json)]
        Predmet GetPredmet();

        [OperationContract]
        [WebGet(UriTemplate = "AllPredmeti", ResponseFormat = WebMessageFormat.Json)]
        List<Predmet> AllPredmeti();

        [OperationContract]
        [WebGet(UriTemplate = "PredmetiForIzvajalec", ResponseFormat = WebMessageFormat.Json)]
        List<Predmet> PredmetiForIzvajalec();


        //OcenaPredmet
        [OperationContract]
        [WebGet(UriTemplate = "GetKomentarPredmet", ResponseFormat = WebMessageFormat.Json)]
        List<OcenaPredmeta> GetKomentarPredmet();

        [OperationContract]
        [WebGet(UriTemplate = "KomentirajPredmet", ResponseFormat = WebMessageFormat.Json)]
        OcenaPredmeta SetKomentarPredmet();

        //OcenaKomentar 
        [OperationContract]
        [WebGet(UriTemplate = "AddOcenaKomentar", ResponseFormat = WebMessageFormat.Json)]
        Komentar AddOcenaKomentar();
    }
}
