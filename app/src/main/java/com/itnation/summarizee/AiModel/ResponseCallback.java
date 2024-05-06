package com.itnation.summarizee.AiModel;

public interface ResponseCallback {

    void onResponse(String response);
    void onError(Throwable throwable);
}
