package com.tony.demo.microservice.mud.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于google
 * Created by Tony on 29/11/2016.
 */
public class QRUtil {

    /**
     * 生成一个二维码图片
     *
     * @param content
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    public static String buildQRCode(String content, int width, int height) throws Exception {
        System.out.println(content);
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        String fileName = FileUtil.getPath(PropertUtil.get("image.activity.qrcode")) + FileUtil.getRangeFileName("png");
        File file = new File(fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, "png", file.toPath());
        return fileName;
    }

}