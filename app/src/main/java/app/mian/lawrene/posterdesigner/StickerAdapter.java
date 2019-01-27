package app.mian.lawrene.posterdesigner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class StickerAdapter extends ArrayAdapter<Sticker> {
    public StickerAdapter(@NonNull Context context, @NonNull List<Sticker> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sticker_listitem, parent, false);
        }

        Sticker sticker = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.sticker);
        imageView.setImageResource(sticker.getImage());

        return convertView;
    }
}
