package app.mian.lawrene.posterdesigner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.List;

class ImageAdapter extends ArrayAdapter<Backgrounds> {
    public ImageAdapter(@NonNull Context context, @NonNull List<Backgrounds> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.background_list_item, parent, false);
        }

        Backgrounds currentbackground = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.background);
        imageView.setImageResource(currentbackground.getImage());
        return convertView;
    }

    //    Context mContext;
//
//    public ImageAdapter(Context mContext) {
//        this.mContext = mContext;
//    }
//
//    private Integer[] thumbIDs = {
//            R.drawable.ic_color_lens_24dp, R.drawable.ic_edit_24dp
//    };
//
//    @Override
//    public int getCount() {
//        return thumbIDs.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if(convertView == null){
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8,8,8,8);
//        }else{
//            imageView = (ImageView)convertView;
//        }
//
//        imageView.setImageResource(thumbIDs[position]);
//        return null;
//    }
//

}
