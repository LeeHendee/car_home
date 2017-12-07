package com.example.gtercn.car.net;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Administrator on 2016-6-24.
 */
public class HttpsTrustManager implements X509TrustManager {
    private static final String TAG = HttpsTrustManager.class.getSimpleName();

//    private static TrustManager[] mTrustManager;

    private static final X509Certificate[] mAcceptedIssuers = new X509Certificate[]{};

    public HttpsTrustManager() {

    }

    public HttpsTrustManager(TrustManager[] trustManagers){
//        mTrustManager = trustManagers;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        if(chain != null && chain.length > 0){
//            chain[0].checkValidity();
//        }else {
//            ((X509TrustManager)(mTrustManager[0])).checkClientTrusted(chain, authType);
//        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        if(chain != null && chain.length > 0){
//            chain[0].checkValidity();
//        }else {
//            ((X509TrustManager)(mTrustManager[0])).checkServerTrusted(chain, authType);
//        }

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
//        return new X509Certificate[0];
        return mAcceptedIssuers;
    }

}
