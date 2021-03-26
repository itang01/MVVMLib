package com.itang.mvvm.binding.viewadapter.image;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.itang.mvvm.utils.StringUtils;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        //使用Glide框架加载图片
        url = StringUtils.isEmpty(url) ? "url" : url;
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .centerCrop()
                        .placeholder(placeholderRes))
                .into(imageView);
    }
}

