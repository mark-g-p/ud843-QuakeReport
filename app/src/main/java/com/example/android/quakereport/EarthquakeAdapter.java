package com.example.android.quakereport;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.quakereport.databinding.ListItemBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        ListItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.list_item, parent, false);
            convertView = binding.getRoot();
        } else {
            binding = (ListItemBinding) convertView.getTag();
        }


        // Get the {@link Attraction} object located at this position in the list
        Earthquake earthquake = getItem(position);
        if (earthquake != null) {
            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) binding.mag.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());

            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);

            DecimalFormat formatter = new DecimalFormat("0.0");
            String magnitude = formatter.format(earthquake.getMagnitude());
            binding.mag.setText(magnitude);

//            Divide location based on form.
            String wholeLocation = earthquake.getPlace();
            Pattern pattern = Pattern.compile("(.+of\\s)(.*)");
            Matcher matcher = pattern.matcher(wholeLocation);
            if (matcher.find()) {
                binding.placeDetails.setText(matcher.group(1));
                binding.place.setText(matcher.group(2));
            } else {
                binding.placeDetails.setText(R.string.near_the);
                binding.place.setText(wholeLocation);
            }

            Date dateObject = new Date(earthquake.getDate());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("DD MMM, yyyy", Locale.getDefault());
            binding.date.setText(dateFormatter.format(dateObject));
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            binding.time.setText(timeFormatter.format(dateObject));
        }
        convertView.setTag(binding);


        return convertView;
    }

    private int getMagnitudeColor(double magnitude) {
//        Return color depending on magnitude value.
//        Casting to int is enough, no need to add Math.floor
        switch ((int) magnitude) {
            case (0):
            case (1):
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case (2):
                return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case (3):
                return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case (4):
                return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case (5):
                return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case (6):
                return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case (7):
                return ContextCompat.getColor(getContext(), R.color.magnitude7);
            case (8):
                return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case (9):
                return ContextCompat.getColor(getContext(), R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }
    }
}

