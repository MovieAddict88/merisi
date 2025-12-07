package net.openvpn.openvpn;

import androidx.core.content.ContextCompat;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileAdapter extends ArrayAdapter<OpenVPNService.Profile> {

    private static final String TAG = "ProfileAdapter";

    public ProfileAdapter(Context context, List<OpenVPNService.Profile> profiles) {
        super(context, 0, profiles);
    }

    private static class ViewHolder {
        ImageView flagIcon;
        TextView serverName;
        TextView ping_view;
        View separator;
        LinearLayout signalBars;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent, false);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent, true);
    }

    private View createItemView(int position, View convertView, ViewGroup parent, boolean isDropDown) {
        ViewHolder holder;
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_with_flag, parent, false);
            holder = new ViewHolder();
            holder.flagIcon = listItem.findViewById(R.id.flag_icon);
            holder.serverName = listItem.findViewById(R.id.server_name);
            holder.ping_view = listItem.findViewById(R.id.ping_view);
            holder.separator = listItem.findViewById(R.id.separator);
            holder.signalBars = listItem.findViewById(R.id.signal_bars);
            listItem.setTag(holder);
        } else {
            holder = (ViewHolder) listItem.getTag();
        }

        OpenVPNService.Profile currentProfile = getItem(position);

        if (isDropDown) {
            if (position == getCount() - 1) {
                holder.separator.setVisibility(View.GONE);
            } else {
                holder.separator.setVisibility(View.VISIBLE);
            }
        } else {
            holder.separator.setVisibility(View.GONE);
        }

        if (currentProfile != null) {
            String profileName = currentProfile.get_name();
            holder.serverName.setText(profileName);

            String iconPath = currentProfile.get_icon_path();
            if (iconPath != null && !iconPath.isEmpty()) {
                Glide.with(getContext())
					.load(iconPath)
					.placeholder(R.drawable.icon)
					.error(R.drawable.icon)
					.into(holder.flagIcon);
            } else {
                holder.flagIcon.setImageResource(R.drawable.icon); // default icon
            }

            // Set ping and color
            int ping = currentProfile.get_ping();
            holder.ping_view.setText(String.format("%d ms", ping));
            if (ping < 100) {
                holder.ping_view.setTextColor(ContextCompat.getColor(getContext(), R.color.ping_green));
            } else if (ping < 200) {
                holder.ping_view.setTextColor(ContextCompat.getColor(getContext(), R.color.ping_orange));
            } else {
                holder.ping_view.setTextColor(ContextCompat.getColor(getContext(), R.color.ping_red));
            }

            // Set signal strength bars
            int signalStrength = currentProfile.get_signal_strength();
            updateSignalBars(holder.signalBars, signalStrength);

        } else {
            holder.flagIcon.setImageResource(R.drawable.icon); // default icon
        }

        return listItem;
    }

    private void updateSignalBars(LinearLayout signalBars, int signalStrength) {
        int signalColor;
        if (signalStrength > 75) {
            signalColor = ContextCompat.getColor(getContext(), R.color.signal_excellent);
        } else if (signalStrength > 50) {
            signalColor = ContextCompat.getColor(getContext(), R.color.signal_good);
        } else if (signalStrength > 25) {
            signalColor = ContextCompat.getColor(getContext(), R.color.signal_fair);
        } else {
            signalColor = ContextCompat.getColor(getContext(), R.color.signal_poor);
        }

        for (int i = 0; i < signalBars.getChildCount(); i++) {
            View bar = signalBars.getChildAt(i);
            int threshold = (i + 1) * 25;
            if (signalStrength >= threshold) {
                bar.setBackgroundColor(signalColor);
            } else {
                bar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.signal_none));
            }
        }
    }
}

