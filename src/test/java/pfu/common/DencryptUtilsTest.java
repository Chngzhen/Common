package pfu.common;

import pfu.common.util.DencryptUtils;

/**
 * @author chngzhen@outlook.com
 * @since 2021/3/23
 */
public class DencryptUtilsTest {

    public static void main(String[] args) throws Exception {
        String plainText = "accessCode=nm18f2af1b989b497684a5e22d6e7c4651&gwAuth=5df901f3ac595f1c2889da481de9363a88bfc4be661e439af5d7d02ef64865d4";

        System.out.println("---------- RSA加/解密、签名、验签测试 START ------------");
        String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDv4W9jGDxPYk77FOk63Gr1XvaIaQ0k50VpWeBcG8b0djyJbMbE2JeOhLEmWPFuemEgPiDRsjoDBYk1cGugaMlw9ZVBbVGjGorBkhO0k8Mzd32ltT3EdALsvH9BKK1FPnOuhYGEewyIEtcF6Eme41x44C1R8qkPEjroMhqAQGgbLwIDAQAB";
        String rsaPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/hb2MYPE9iTvsU6TrcavVe9ohpDSTnRWlZ4FwbxvR2PIlsxsTYl46EsSZY8W56YSA+INGyOgMFiTVwa6BoyXD1lUFtUaMaisGSE7STwzN3faW1PcR0Auy8f0EorUU+c66FgYR7DIgS1wXoSZ7jXHjgLVHyqQ8SOugyGoBAa" +
                "BsvAgMBAAECgYEA5B7Kwr4lfIjM+1GjiupafQXWBVuBuHqHM+zYxD9WMNzQLRBY5pHjbxEZNpKW5q1S+EqdA04uhPkaxssmTuHXzdEBELw64bC8v2iiGEQXz+3YszpEnGhUZ2SAVlcbWZJ10nkfg227QUF7tsiqKrexPrdDNXctyh8CDc2/UxRjFGECQQD+Xp7WsaZDJCvKcyHUbFpQvRNRE+Pc3Y9PMapgnsGdw/w" +
                "Jch4ijVvvFZzIsFrxf46LNqV4tDY3pF24RbS1Y10NAkEA8WsKX4DDI87RbrNRBHHTn7385YDZIJC85ke+nFhhwekqnFIvwlzFh5TzATLkFTzK4dyUWNHULzQFjkx4QlriKwJBANxfiWkjW5HLooywllx8yvor9LJHcyAEa4YG1z8CC2ftRUvBrE3u/fjyVnSgJQoYopwFafJa3qXyJqfg7CBBx3kCQGE9CQ+QkIjyu" +
                "76H8WEF7ARShPN343zPb5adXTQiETYGkYgnPl9d+J30q7BW7DF1rA4vR31C/uEIZhh7ypCHhMMCQEm51D4cLKw2YkQoch+Sv+t1ba+73x6Wtlb/IT5PjIj9DrUFC8ocjqNdwwgwDGxcRuC7Tv3UpUwTqKAxEHeP3lw=";
        String rsaCipherText = DencryptUtils.rsaEncrypt(rsaPublicKey, plainText, true);
        System.out.println("明文：" + plainText);
        System.out.println("公钥加密：" + rsaCipherText);
        System.out.println("私钥解密：" + DencryptUtils.rsaDecryptForLongStr(rsaPrivateKey, rsaCipherText));

        System.out.println("---------- RSA加/解密、签名、验签测试 CLOSE ------------");

        System.out.println();

        System.out.println("---------- XXTea加/解密测试 START ------------");
        String xxTeaSecret = "8eqtlGIpuWdbmwhP4yV6ZLUmq8jEATue";

        String params = DencryptUtils.xxTeaEncrypt(plainText, xxTeaSecret);
        System.out.println("明文：" + plainText);
        System.out.println("密文：" + params);

        String encStr = "87ACD984914385D73080F3547FE1A6C4323042C618E7501600BD27EC67F5776FC1D5104F01FCA4551513B56808E3B6130A41D34D4906410A8BE4F54D8B074B5F3F7FC79FEB50176E36F1A7B8CBED851D60E894E14CBECEF04087ECBE9BD18B7180BF587957C1FB854B120BB43924457E24D6476ED5167D257ABBB80B";
        String pText = DencryptUtils.xxTeaDecrypt(encStr.toLowerCase(), xxTeaSecret);
        System.out.println("密文：" + encStr);
        System.out.println("明文：" + pText);
        System.out.println("---------- XXTea加/解密测试 CLOSE ------------");

    }

}
