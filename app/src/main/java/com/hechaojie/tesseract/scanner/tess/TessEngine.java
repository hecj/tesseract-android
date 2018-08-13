package com.hechaojie.tesseract.scanner.tess;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.hechaojie.tesseract.scanner.utils.Tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Fadi on 6/11/2014.
 */
public class TessEngine {

    static final String TAG = "DBG_" + TessEngine.class.getName();

    private Context context;

    private TessEngine(Context context){
        this.context = context;
    }

    public static TessEngine Generate(Context context) {
        return new TessEngine(context);
    }

    public String detectText(Bitmap bitmap) {
        Log.d(TAG, "Initialization of TessBaseApi");
        TessDataManager.initTessTrainedData(context);
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        String path = TessDataManager.getTesseractFolder();
        Log.d(TAG, "Tess folder: " + path);
        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, "eng");
        // 白名单
//        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789");
        // 黑名单
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?");
        tessBaseAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
        Log.d(TAG, "Ended initialization of TessEngine");
        Log.d(TAG, "Running inspection on bitmap");
        tessBaseAPI.setImage(bitmap);
        String inspection = tessBaseAPI.getHOCRText(0);
        Log.d("tess",inspection);
        Log.d(TAG, "tess values: " + tessBaseAPI.meanConfidence());
        tessBaseAPI.end();
        System.gc();
        return Tools.getTelNum(inspection);
//        return inspection;

//        Document doc = Jsoup.parse(inspection);
//        String str = doc.select(".ocr_carea").text();

//        return str;
    }

}
