package com.fiap.tech.challenge.global.util;

import lombok.experimental.UtilityClass;

import java.net.Socket;

@UtilityClass
public class NetworkUtil {

    public boolean healthCheck(String address) {
        String hostName = address.split(":")[0];
        int portNumber = Integer.parseInt(address.split(":")[1]);
        return healthCheck(hostName, portNumber);
    }

    public boolean healthCheck(final String hostName, final int portNumber) {
        try (Socket ignored = new Socket(hostName, portNumber)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}