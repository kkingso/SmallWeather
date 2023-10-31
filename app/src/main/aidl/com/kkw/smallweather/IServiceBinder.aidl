// IServiceBinder.aidl
package com.kkw.smallweather;

import com.kkw.smallweather.ICallback;

// Declare any non-default types here with import statements

interface IServiceBinder {

    void setCallback(ICallback callback);

    String receivedSync(String jsonData);

    void receivedAsync(String jsonData);
}