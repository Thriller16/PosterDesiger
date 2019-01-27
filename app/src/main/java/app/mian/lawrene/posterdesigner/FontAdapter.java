package app.mian.lawrene.posterdesigner;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FontAdapter extends ArrayAdapter<Fonts> {
    public FontAdapter(@NonNull Context context, @NonNull List<Fonts> fonts) {
        super(context, 0, fonts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.font_list_item, parent, false);
        }

        Fonts currentfont = getItem(position);

        TextView textView = convertView.findViewById(R.id.font_name);
        textView.setText(currentfont.getFontname());


        setTypeFace(textView, currentfont);
        return convertView;
    }

    private void setTypeFace(TextView textView, Fonts currentfont) {
        if(currentfont.getFontname().equals("Baloony")){
            Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/baloony.ttf");
            textView.setTypeface(custom_font1);
        }
        if(currentfont.getFontname().equals("Brewsky")){
            Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/brewsky.ttf");
            textView.setTypeface(custom_font1);
        }

        if(currentfont.getFontname().equals("Cheeky Rabbit")){
            Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/cheeky rabbit.ttf");
            textView.setTypeface(custom_font1);
        }

        if(currentfont.getFontname().equals("Delicious")){
            Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/delicious.ttf");
            textView.setTypeface(custom_font1);
        }

        if(currentfont.getFontname().equals("Diploma")){
            Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/diploma.ttf");
            textView.setTypeface(custom_font1);
        }

        if(currentfont.getFontname().equals("Happy cat")){
            Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/happy cat.ttf");
            textView.setTypeface(custom_font1);
        }

        if(currentfont.getFontname().equals("Corsiva")){
            Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/corsiva.ttf");
            textView.setTypeface(custom_font1);
        }

        if(currentfont.getFontname().equals("My crush")){
            Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/my crush.ttf");
            textView.setTypeface(custom_font1);
        }

        if (currentfont.getFontname().equals("Water park")) {
            Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/water park.ttf");
            textView.setTypeface(custom_font1);
        }
        else{

        }
    }
}
