package epitos.com.cake_android_client.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import epitos.com.cake_android_client.utils.ImageLoader;
import epitos.com.cake_android_client.R;
import epitos.com.cake_android_client.viewHolder.CakeViewHolder;

/**
 * Created by epitos on 04/02/2018.
 */

public class CakeAdapter extends RecyclerView.Adapter<CakeViewHolder> {

    private JSONArray mItems;
    private ImageLoader mImageLoader;
    private Activity activity;

    public CakeAdapter(Activity activity, JSONArray mItems) {
        this.mItems = mItems;
        this.activity = activity;
        mImageLoader = new ImageLoader();
    }

    @Override
    public CakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        CakeViewHolder viewHolder = new CakeViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CakeViewHolder holder, int position) {
        try {
            final JSONObject object = (JSONObject) mItems.get(position);
            holder.title.setText(object.getString("title"));
            holder.description.setText(object.getString("desc"));
            new GetCakeImageTask(holder, object).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.length();
    }

    class GetCakeImageTask extends AsyncTask<Void, Void, Void> {
        CakeViewHolder holder;
        JSONObject object;
        Bitmap bitmap;
        byte[] bytes;

        public GetCakeImageTask(CakeViewHolder holder, JSONObject object) {
            this.holder = holder;
            this.object = object;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                bytes = mImageLoader.loadImageData(object.getString("image"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            bitmap = mImageLoader.convertToBitmap(bytes);
            holder.imageView.setImageBitmap(bitmap);
        }
    }
}
