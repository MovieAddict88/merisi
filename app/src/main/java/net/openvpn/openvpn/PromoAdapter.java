package net.openvpn.openvpn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.openvpn.openvpn.R;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PromoAdapter extends ArrayAdapter<Promo> {
    private final Context context;
    private final List<Promo> promos;

    public PromoAdapter(Context context, List<Promo> promos) {
        super(context, R.layout.promo_spinner_item, promos);
        this.context = context;
        this.promos = promos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.promo_spinner_item, parent, false);
        }

        Promo promo = promos.get(position);

        TextView promoName = view.findViewById(R.id.promo_name);
        ImageView promoIcon = view.findViewById(R.id.promo_icon);

        promoName.setText(promo.getName());
        Glide.with(context)
			.load(promo.getIconUrl())
			.into(promoIcon);

        return view;
    }
}

