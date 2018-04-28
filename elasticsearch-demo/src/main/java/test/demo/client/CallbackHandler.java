package test.demo.client;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/25 上午10:24
 */
public class CallbackHandler implements Callback {

    private String result;
    @Override
    public void onFailure(Call call, IOException e) {
        this.result = e.getMessage();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        this.result = response.body().string();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
