package com.okhttp.kotlin.utils;

public class Util {

    private static String TAG = "Utils";

    private volatile static Util uniqueInstance;

    //采用Double CheckLock(DCL)实现单例
    public static Util getInstance() {
        if (uniqueInstance == null) {
            synchronized (Util.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Util();
                }
            }
        }
        return uniqueInstance;
    }


    // 有符号转换16进制
    public String bytesToString(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];

            sb.append(hexChars[i * 2]);
            sb.append(hexChars[i * 2 + 1]);
            sb.append(' ');
        }
        return sb.toString();
    }

    //有符号转换10进制(无符号)
    public int byteToInt(byte b) {
        return b & 0xFF;
    }

    //有符号转换10进制(无符号)
    public short bytesToShort(byte a, byte b) {
        return (short) (((a & 0x00FF) << 8) | (0x00FF & b));
    }

    //16进制转换10进制
    public int signedHexToDecimal(String hexStr) {
        hexStr = hexStr.trim();
        boolean isNegative = hexStr.startsWith("-");
        if (isNegative) hexStr = hexStr.substring(1);
        int absValue = Integer.parseInt(hexStr.replaceFirst("^0x", ""), 16);
        return isNegative ? -absValue : absValue;
    }


    //10进制(无符号)转换有符号
    public byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }

    //10进制(无符号)转换有符号
    public byte[] tobetys(int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8) {
        byte[] lpSendBuf = new byte[8];
        lpSendBuf[0] = (byte) (a1 & 0xFF);
        lpSendBuf[1] = (byte) (a2 & 0xFF);
        lpSendBuf[2] = (byte) (a3 & 0xFF);
        lpSendBuf[3] = (byte) (a4 & 0xFF);
        lpSendBuf[4] = (byte) (a5 & 0xFF);
        lpSendBuf[5] = (byte) (a6 & 0xFF);
        lpSendBuf[6] = (byte) (a7 & 0xFF);
        lpSendBuf[7] = (byte) (a8 & 0xFF);
        return lpSendBuf;
    }


    // =================================== byte转换 ===============================

}