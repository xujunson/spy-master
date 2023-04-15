package com.atu.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Filter;
import com.atu.common.constants.LogConstants;

import java.net.*;
import java.util.Enumeration;
import java.util.LinkedHashSet;

/**
 * @author: Tom
 * @date: 2023/4/15 18:06
 * @description: TODO
 **/
public class LocalhostUtil {

    private static String hostIp = LogConstants.UNKNOWN;

    private static String hostName = LogConstants.UNKNOWN;

    public static String localhostName;

    public static String getHostIp() {
        try {
            if (hostIp.equals(LogConstants.UNKNOWN)) {
                hostIp = getLocalhostStr();
            }
        } catch (Exception e) {
        }
        return hostIp;
    }

    private static String getLocalhostStr() {
        InetAddress localhost = getLocalhost();
        if (null != localhost) {
            return localhost.getHostAddress();
        }
        return null;
    }

    private static InetAddress getLocalhost() {
        final LinkedHashSet<InetAddress> localAddressList = localAddressList(address -> {
            // 非loopback地址，指127.*.*.*的地址
            return !address.isLoopbackAddress()
                    // 需为IPV4地址
                    && address instanceof Inet4Address;
        });

        if (CollUtil.isNotEmpty(localAddressList)) {
            InetAddress address2 = null;
            for (InetAddress inetAddress : localAddressList) {
                if (!inetAddress.isSiteLocalAddress()) {
                    // 非地区本地地址，指10.0.0.0 ~ 10.255.255.255、172.16.0.0 ~ 172.31.255.255、192.168.0.0 ~ 192.168.255.255
                    return inetAddress;
                } else if (null == address2) {
                    address2 = inetAddress;
                }
            }

            if (null != address2) {
                return address2;
            }
        }

        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // ignore
        }

        return null;
    }

    private static LinkedHashSet<InetAddress> localAddressList(Filter<InetAddress> addressFilter) {
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new UtilException(e);
        }

        if (networkInterfaces == null) {
            throw new UtilException("Get network interface error!");
        }

        final LinkedHashSet<InetAddress> ipSet = new LinkedHashSet<>();

        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();
            final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                final InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress != null && (null == addressFilter || addressFilter.accept(inetAddress))) {
                    ipSet.add(inetAddress);
                }
            }
        }

        return ipSet;
    }
}
