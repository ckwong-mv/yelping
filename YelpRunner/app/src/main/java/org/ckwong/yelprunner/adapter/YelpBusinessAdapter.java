package org.ckwong.yelprunner.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.ckwong.yelprunner.R;
import org.ckwong.yelprunner.data.YelpBusiness;
import org.ckwong.yelprunner.service.YelpService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ckwong on 10/26/14.
 */
public class YelpBusinessAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private YelpService.Result result;

    public YelpBusinessAdapter(Context context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setYelpServiceResult(YelpService.Result result) {
        this.result = result;
    }

    @Override
    public int getCount() {
        return (result != null) ? result.getBusinesses().size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return result.getBusinesses().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.yelp_business_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        YelpBusiness business = result.getBusinesses().get(i);

        ImageView businessImage = (ImageView) holder.getView(R.id.businessImage);
        ImageLoader.load(businessImage, business.getImageUrl());

        TextView textView = (TextView) holder.getView(R.id.businessName);
        textView.setText(business.getName());

        List<String> displayAddress = business.getDisplayAddress();

        int size = displayAddress.size();
        String text = size > 0 ? displayAddress.get(0) : "n/a";
        textView = (TextView) holder.getView(R.id.businessAddr0);
        textView.setText(text);

        text = size > 1 ? displayAddress.get(size - 1) : "n/a";
        textView = (TextView) holder.getView(R.id.businessAddr1);
        textView.setText(text);

        StringBuilder buf = new StringBuilder();
        List<List<String>> categories = business.getCategories();
        for (List<String> list : categories) {
            for (String c : list) {
                if (buf.length() > 0)
                    buf.append(", ");

                buf.append(c);
            }
        }

        textView = (TextView) holder.getView(R.id.businessCategories);
        textView.setText(buf.toString());

        return convertView;
    }
}

class ViewHolder {

    SparseArray<View> views;
    private final View parent;

    ViewHolder(View parent) {
        this.parent = parent;
    }

    View getView(int id) {
        View v = views.get(id);
        if (v == null) {
            v = parent.findViewById(id);
            views.put(id, v);
        }

        return v;
    }
}

class ImageLoader {
    static ExecutorService gExecutorService = Executors.newFixedThreadPool(5);

    static void load(final ImageView imageView, final String imageUrl) {
        imageView.setImageBitmap(null);
        imageView.setTag(R.id.businessImage, imageUrl);

        Runnable loadRunner = new Runnable() {
            @Override
            public void run() {
                if (isImageViewRecycled(imageView, imageUrl))
                    return;

                try {
                    URL url = new URL(imageUrl);
                    final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    if (isImageViewRecycled(imageView, imageUrl))
                        return;

                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isImageViewRecycled(imageView, imageUrl))
                                return;

                            imageView.setImageBitmap(bitmap);
                        }
                    });
                } catch (MalformedURLException e) {
                    Log.e("ImageLoader", "imageUrl : " + imageUrl, e);
                } catch (IOException e) {
                    Log.e("ImageLoader", "imageUrl : " + imageUrl, e);
                }
            }

            boolean isImageViewRecycled(ImageView imageView, String imageUrl) {
                String _imageUrl = (String) imageView.getTag(R.id.businessImage);
                return !imageUrl.equals(_imageUrl);
            }
        };


        gExecutorService.execute(loadRunner);
    }
}