package epitos.com.cake_android_client.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import epitos.com.cake_android_client.R;
import epitos.com.cake_android_client.adapter.CakeAdapter;
import epitos.com.cake_android_client.utils.StreamUtils;

import static android.content.ContentValues.TAG;


public class PlaceHolderFragment extends Fragment {

    private static String JSON_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/" +
            "raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    private RecyclerView cakeRecyclerView;
    private CakeAdapter cakeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        bindView(view);
        setRecyclerView();
        new GetCakesTask().execute();
        return view;
    }

    private void bindView(View v) {
        cakeRecyclerView = (RecyclerView) v.findViewById(R.id.cake_list);
    }

    private void setRecyclerView() {
        cakeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cakeRecyclerView.setHasFixedSize(true);
    }

    private JSONArray loadData() throws IOException, JSONException {
        URL url = new URL(JSON_URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Can you think of a way to improve the performance of loading data
            // using HTTP headers???

            // Also, Do you trust any utils thrown your way????

            byte[] bytes = StreamUtils.readUnknownFully(in);

            // Read in charset of HTTP content.
            String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));

            // Convert byte array to appropriate encoded string.
            String jsonText = new String(bytes, charset);

            // Read string as JSON.
            return new JSONArray(jsonText);
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    public static String parseCharset(String contentType) {
        if (contentType != null) {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }

    private class GetCakesTask extends AsyncTask<Void, Void, Void> {
        JSONArray array = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                array = loadData();
            } catch (IOException | JSONException e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cakeAdapter = new CakeAdapter(getActivity(), array);
            cakeRecyclerView.setAdapter(cakeAdapter);
        }
    }
}
