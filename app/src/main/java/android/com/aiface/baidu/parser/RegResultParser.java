/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package android.com.aiface.baidu.parser;

import android.com.aiface.baidu.exception.FaceError;
import android.com.aiface.baidu.model.RegResult;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class RegResultParser implements Parser<RegResult> {


    @Override
    public RegResult parse(String json) throws FaceError {
        Log.e("xx", "oarse:" + json);
        try {
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.has("error_code")) {
                FaceError error = new FaceError(jsonObject.optInt("error_code"), jsonObject.optString("error_msg"));
                if (error.getErrorCode() != 0) {
                    throw error;
                }
            }

            RegResult result = new RegResult();
            result.setLogId(jsonObject.optLong("log_id"));
            result.setJsonRes(json);

            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            FaceError error = new FaceError(FaceError.ErrorCode.JSON_PARSE_ERROR, "Json parse error:" + json, e);
            throw error;
        }
    }
}
