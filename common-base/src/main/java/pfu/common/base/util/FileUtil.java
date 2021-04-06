package pfu.common.base.util;

import pfu.common.base.exception.LocalException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.zip.CRC32;

/**
 * @author chngzhen@outlook.com
 * @since 2020/7/26
 */
public class FileUtil {

    private FileUtil() {}

    private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String md5(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");

            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messagedigest.update(byteBuffer);
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            throw new LocalException("文件MD5值计算失败");
        }
    }

    public static String sha1(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");

            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messagedigest.update(byteBuffer);
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            throw new LocalException("文件MD5值计算失败");
        }
    }

    public static String crc32(File file) {
        CRC32 crc32 = new CRC32();
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            int length;
            byte[] buffer = new byte[8192];
            while ((length = fileInputStream.read(buffer)) != -1) {
                crc32.update(buffer, 0, length);
            }
            return crc32.getValue() + "";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuilder stringBuilder = new StringBuilder(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringBuilder);
        }
        return stringBuilder.toString();
    }

    private static void appendHexPair(byte bt, StringBuilder stringBuilder) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringBuilder.append(c0);
        stringBuilder.append(c1);
    }

}
