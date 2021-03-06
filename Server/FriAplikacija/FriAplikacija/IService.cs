﻿using Newtonsoft.Json.Linq;
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
        [WebGet(UriTemplate = "CheckEmail", ResponseFormat = WebMessageFormat.Json)]
        Boolean CheckEmail();

        [OperationContract]
        [WebGet(UriTemplate = "Login", ResponseFormat = WebMessageFormat.Json)]
        Uporabnik Login();

        [OperationContract]
        [WebGet(UriTemplate = "Register", ResponseFormat = WebMessageFormat.Json)]
        Uporabnik Register();

        [OperationContract]
        [WebGet(UriTemplate = "Verify", ResponseFormat = WebMessageFormat.Json)]
        Uporabnik Verify();

        [OperationContract]
        [WebGet(UriTemplate = "Verify1", ResponseFormat = WebMessageFormat.Json)]
        void Verify1();

        //Izvajalec
        [OperationContract]
        [WebGet(UriTemplate = "Izvajalec", ResponseFormat = WebMessageFormat.Json)]
        IzvajalecZStevilomKomentarjev GetIzvajalec();

        [OperationContract]
        [WebGet(UriTemplate = "AllIzvajalci", ResponseFormat = WebMessageFormat.Json)]
        List<Izvajalec> AllIzvajalci();

        [OperationContract]
        [WebGet(UriTemplate = "IzvajalecForPredmet", ResponseFormat = WebMessageFormat.Json)]
        List<IzvajalecPredmeta> IzvajalecForPredmet();

        [OperationContract]
        [WebGet(UriTemplate = "IzvajalciForOznaka", ResponseFormat = WebMessageFormat.Json)]
        List<Izvajalec> IzvajalciForOznaka();

        [OperationContract]
        [WebGet(UriTemplate = "AllIzvajalciPodrocje", ResponseFormat = WebMessageFormat.Json)]
        List<IzvajalecPodrocje> AllIzvajalciPodrocje();

        [OperationContract]
        [WebGet(UriTemplate = "AllIzvajalciForPodrocje", ResponseFormat = WebMessageFormat.Json)]
        List<Izvajalec> AllIzvajalciForPodrocje();

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
        [WebGet(UriTemplate = "PredmetWithOznaka", ResponseFormat = WebMessageFormat.Json)]
        PredmetWithOznaka GetPredmetWithOznaka();

        [OperationContract]
        [WebGet(UriTemplate = "AllPredmeti", ResponseFormat = WebMessageFormat.Json)]
        List<Predmet> AllPredmeti();

        [OperationContract]
        [WebGet(UriTemplate = "PredmetiForIzvajalec", ResponseFormat = WebMessageFormat.Json)]
        List<Predmet> PredmetiForIzvajalec();

        [OperationContract]
        [WebGet(UriTemplate = "PredmetiForPodrocje", ResponseFormat = WebMessageFormat.Json)]
        List<Predmet> PredmetiForPodrocje();

        [OperationContract]
        [WebGet(UriTemplate = "PredmetiForOznaka", ResponseFormat = WebMessageFormat.Json)]
        List<Predmet> PredmetiForOznaka();


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

        //Podrocja
        [OperationContract]
        [WebGet(UriTemplate = "Podrocje", ResponseFormat = WebMessageFormat.Json)]
        Podrocje GetPodrocje();

        [OperationContract]
        [WebGet(UriTemplate = "AllPodrocja", ResponseFormat = WebMessageFormat.Json)]
        List<Podrocje> AllPodrocja();

        //Oznake
        [OperationContract]
        [WebGet(UriTemplate = "Oznaka", ResponseFormat = WebMessageFormat.Json)]
        Oznaka GetOznaka();

        [OperationContract]
        [WebGet(UriTemplate = "AllOznake", ResponseFormat = WebMessageFormat.Json)]
        List<Oznaka> AllOznake();
    }
}
