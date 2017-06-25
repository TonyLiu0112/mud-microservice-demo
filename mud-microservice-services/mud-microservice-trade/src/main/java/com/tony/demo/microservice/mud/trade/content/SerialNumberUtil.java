package com.tony.demo.microservice.mud.trade.content;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 交易流水号生成器
 * <p>
 * Created by Tony on 09/06/2017.
 */
public class SerialNumberUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    private static final String FORMAT = "%s%s";

    public static String build() {
        Random random = new Random();
        StringBuilder rNumber = new StringBuilder(random.nextInt(9999) + "");
        while (rNumber.toString().length() < 8) {
            int offset = 8 - rNumber.toString().length();
            String limit = "";
            for (int i = 0; i < offset; i ++) {
                limit += "9";
            }
            rNumber.insert(0, random.nextInt(Integer.valueOf(limit)));
        }
        return String.format(FORMAT, sdf.format(new Date()), rNumber.toString());
    }

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            String orderId = SerialNumberUtil.build();
//            System.out.println(SerialNumberUtil.build());
            set.add(SerialNumberUtil.build());
            if (orderId.length() != 20) {
                System.out.println(orderId);
            }
        }
        System.out.println(set.size());
    }


}
