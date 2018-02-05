package epitos.com.cake_android_client.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import epitos.com.cake_android_client.R;

/**
 * Created by epitos on 04/02/2018.
 */

public class CakeViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView title;
    public TextView description;

    public CakeViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.desc);
    }
}
