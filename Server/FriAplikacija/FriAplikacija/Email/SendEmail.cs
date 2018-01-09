using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net.Mail;
using System.Net;

namespace FriAplikacija.Email {
    public class SendEmail {

        internal static bool sendVerificationEmail(String email, String verificationCode) {
            try {
                MailMessage mail = new MailMessage();
                mail.From = new MailAddress("no-reply.FriAplikacija@outlook.com");
                mail.To.Add(email);
                mail.Subject = "Registration to FRI aplikacija";
                mail.Body = "Account verification code: " + verificationCode +"\n Account verification link: http://friaplikacija.azurewebsites.net/Service.svc/Verify1?email="+ email + "&cerCode=" + verificationCode;

                SmtpClient client = new SmtpClient();
                client.Port = 25;
                client.DeliveryMethod = SmtpDeliveryMethod.Network;
                client.Credentials = new NetworkCredential("no-reply.FriAplikacija@outlook.com", "FriAplikacija123");
                client.Host = "smtp.live.com";
                client.EnableSsl = true;
                client.Send(mail);
                return true;
            }catch(Exception e) {
                return false;
            }
        }
    }
}