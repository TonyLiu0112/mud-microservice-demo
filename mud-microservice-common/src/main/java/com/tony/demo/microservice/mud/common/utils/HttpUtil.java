package com.tony.demo.microservice.mud.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * http/https 请求工具类
 * <p>
 * http调用类废弃，{@see org.springframework.web.client.RestTemplate}
 * Created by Tony on 09/11/2016.
 */
@Deprecated
public class HttpUtil {

    private static final String DEFAULT_CHARSET = "utf-8";

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doGet(String url, String charset) {
        try {
            CloseableHttpClient httpClient = createSSLClientDefault();
            HttpGet get = new HttpGet(new URI(url));
            HttpResponse response = httpClient.execute(get);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null)
                return EntityUtils.toString(httpEntity, charset == null ? DEFAULT_CHARSET : charset);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

}
