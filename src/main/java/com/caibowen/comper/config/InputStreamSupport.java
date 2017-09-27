
package com.caibowen.comper.config;

import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author BowenCai
 *
 */
public class InputStreamSupport {

  InputStreamProvider streamProvider;


  public InputStreamProvider getStreamProvider() {
    return streamProvider;
  }

  public void setStreamProvider(InputStreamProvider streamProvider) {
    this.streamProvider = streamProvider;
  }


  public void doInStream(String path, InputStreamCallback callback) {
    Exception ex = null;
    InputStream inputStream = null;
    try {
      inputStream = streamProvider.getStream(path);
      callback.doWithStream(inputStream);

    } catch (IOException e) {
      ex = e;
    } catch (Exception e) {
      ex = e;
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          ex = e;
        }
      }
    }
    if (ex != null) {
      throw new RuntimeException("IO Error", ex);
    }

  }
}
