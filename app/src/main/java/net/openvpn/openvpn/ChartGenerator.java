package net.openvpn.openvpn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.util.TypedValue;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ChartGenerator {

    public static Bitmap generateChartBitmap(Context context, long download, long upload) {
        BarChart chart = new BarChart(context);
        int width = (int) context.getResources().getDimension(R.dimen.chart_width);
        int height = (int) context.getResources().getDimension(R.dimen.chart_height);
        chart.layout(0, 0, width, height);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, download));
        entries.add(new BarEntry(1f, upload));

        int downloadColor = context.getResources().getColor(R.color.chart_download_color);
        int uploadColor = context.getResources().getColor(R.color.chart_upload_color);

        // Get text color based on theme
        int textColor = getTextColorFromTheme(context);

        BarDataSet dataSet = new BarDataSet(entries, "Data Usage");
        dataSet.setColors(downloadColor, uploadColor);
        dataSet.setValueTextColor(textColor);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        barData.setValueFormatter(new ValueFormatter() {
				@Override
				public String getFormattedValue(float value) {
					return OpenVPNService.humanReadableByteCount((long) value, true);
				}
			});

        chart.setData(barData);
        chart.setFitBars(true);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        // Configure XAxis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(textColor);
        xAxis.setTextSize(12f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(2); // Ensure both labels are shown
        xAxis.setValueFormatter(new ValueFormatter() {
				@Override
				public String getFormattedValue(float value) {
					if (value == 0f) {
						return "Download";
					} else if (value == 1f) {
						return "Upload";
					}
					return "";
				}
			});

        // Configure YAxis
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextColor(textColor);
        leftAxis.setGridColor(adjustAlpha(textColor, 0.3f)); // Subtle grid lines

        chart.getAxisRight().setEnabled(false);

        // Set chart background and text colors
        chart.setBackgroundColor(getBackgroundColorFromTheme(context));
        chart.setNoDataTextColor(textColor);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        chart.draw(canvas);

        return bitmap;
    }

    /**
     * Get text color based on current theme (light/dark mode)
     */
    private static int getTextColorFromTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);

        if (typedValue.resourceId != 0) {
            return context.getResources().getColor(typedValue.resourceId);
        } else {
            return typedValue.data;
        }
    }

    /**
     * Get background color based on current theme
     */
    private static int getBackgroundColorFromTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);

        if (typedValue.resourceId != 0) {
            return context.getResources().getColor(typedValue.resourceId);
        } else {
            return typedValue.data;
        }
    }

    /**
     * Adjust alpha value of a color
     */
    private static int adjustAlpha(int color, float alpha) {
        int alphaValue = Math.round(alpha * 255);
        return (alphaValue << 24) | (color & 0x00FFFFFF);
    }
}
