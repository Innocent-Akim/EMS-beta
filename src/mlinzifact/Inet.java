/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlinzifact;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Inet {

    static String AdresseIp;
    static String HosName;

    public static String getHosName() {
        try {
            final InetAddress addr = InetAddress.getLocalHost();
            HosName = addr.getHostName();
            return HosName;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Inet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void setHosName(String HosName) {
        Inet.HosName = HosName;
    }

    public static String getAdresseIp() {
        try {
            final InetAddress addr = InetAddress.getLocalHost();
            AdresseIp = addr.getHostAddress();
            return AdresseIp;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Inet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void setAdresseIp(String AdresseIp) {
        Inet.AdresseIp = AdresseIp;
    }

    public static boolean isPing(String Ip) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        boolean bool = false;
        try {
            InetAddress inet = InetAddress.getByName(Ip);
            if (inet.isReachable(5000)) {
                bool = true;
            } else {
                bool = false;
                tk.beep();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return bool;
    }

    public static String getMacAdresse() {
        String mac = null;
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] macAddress = ni.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            if (macAddress.length > 0) {
                for (int i = 0; i < macAddress.length; i++) {
                    sb.append(String.format("%02X%s", macAddress[i], (i < macAddress.length - 1) ? "-" : ""));
                }
            }

            mac = sb.toString();
        } catch (UnknownHostException | SocketException ex) {
            mac = null;
        }
        return mac;
    }
}
