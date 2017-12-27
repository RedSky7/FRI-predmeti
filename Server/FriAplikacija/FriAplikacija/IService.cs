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

        //OcenaIzvajalec
        [OperationContract]
        [WebGet(UriTemplate = "GetKomentarIzvajalec", ResponseFormat = WebMessageFormat.Json)]
        List<OcenaIzvajalca> GetKomentarIzvajalec();

        [OperationContract]
        [WebGet(UriTemplate = "KomentirajIzvaj", ResponseFormat = WebMessageFormat.Json)]
        OcenaIzvajalca SetKomentarIzvajalec();
    }
}
