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
                mail.From = new MailAddress("friaplikacija.noreplay@outlook.com");
                mail.To.Add(email);
                mail.Subject = "this is a test email.";
                mail.Body = "this is my test email body " + verificationCode;

                SmtpClient client = new SmtpClient();
                client.Port = 25;
                client.DeliveryMethod = SmtpDeliveryMethod.Network;
                client.Credentials = new NetworkCredential("friaplikacija.noreplay@outlook.com", "FriAplikacija123");
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