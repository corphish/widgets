/*
 *
 * Copyright (C) 2018 Avinaba Dalal <d97.avinaba@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.corphish.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Placeholder view to show in case there is nothing to show in certain situations
 * For example, use this to show empty search results or failure screen
 */

public class PlaceholderView extends RelativeLayout {
    private TextView descTV, titleTV;
    private AppCompatImageView imageView;

    // To handle sizes
    private float dpi;


    public PlaceholderView(Context context) {
        this(context,null);
    }

    public PlaceholderView(Context context, AttributeSet attributeSet) {
        this(context,attributeSet,0);
    }

    public PlaceholderView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);

        LayoutInflater.from(context).inflate(R.layout.placeholder_view, this);

        descTV = findViewById(R.id.placeholder_desc_tv);
        titleTV = findViewById(R.id.placeholder_title_tv);
        imageView = findViewById(R.id.placeholder_image);

        dpi = Resources.getSystem().getDisplayMetrics().densityDpi/ DisplayMetrics.DENSITY_DEFAULT;

        processProperties(context, attributeSet);
    }

    private void processProperties(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PlaceholderView);

        if (typedArray != null) {
            int count = typedArray.getIndexCount();
            for (int i = 0; i < count; i++) {
                int property = typedArray.getIndex(i);

                if (property == R.styleable.PlaceholderView_titleText) {
                    setTitle(typedArray.getText(property).toString());
                    continue;
                }
                if (property == R.styleable.PlaceholderView_descriptionText) {
                    setDescription(typedArray.getText(property).toString());
                    continue;
                }
                if (property == R.styleable.PlaceholderView_titleSize) {
                    setTitleSize(typedArray.getDimension(property, 18*dpi));
                    continue;
                }
                if (property == R.styleable.PlaceholderView_descriptionSize) {
                    setDescriptionSize(typedArray.getDimension(property, 12*dpi));
                    continue;
                }
                if (property == R.styleable.PlaceholderView_viewTint) {
                    setViewTint(typedArray.getColor(property, titleTV.getCurrentTextColor()));
                    continue;
                }
                if (property == R.styleable.PlaceholderView_srcCompat) {
                    Drawable drawable = typedArray.getDrawable(property);
                    if (drawable != null) setImageDrawable(drawable);
                    continue;
                }
                if (property == R.styleable.PlaceholderView_imageHeight) {
                    setImageHeight(typedArray.getDimensionPixelSize(property, imageView.getLayoutParams().height));
                    continue;
                }
                if (property == R.styleable.PlaceholderView_imageWidth) {
                    setImageWidth(typedArray.getDimensionPixelSize(property, imageView.getLayoutParams().width));
                    continue;
                }
                if (property == R.styleable.PlaceholderView_titleStyle) {
                    setTitleTypeface(getTitleTypeface(), typedArray.getInt(property, -1));
                    continue;
                }
                if (property == R.styleable.PlaceholderView_descriptionStyle) {
                    setDescriptionTypeface(getDescriptionTypeface(), typedArray.getInt(property, -1));
                }
            }
            typedArray.recycle();
        }
    }

    /**
     * Sets description of this view
     * @param description Description res id
     */
    public void setDescription(@StringRes int description) {
        descTV.setText(description);
    }

    /**
     * Sets description of this view
     * @param description Description string
     */
    public void setDescription(@NonNull String description) {
        descTV.setText(description);
    }

    /**
     * Sets title of this view
     * @param title Title res id
     */
    public void setTitle(@StringRes int title) {
        titleTV.setText(title);
    }

    /**
     * Sets title of this view
     * @param title Title string
     */
    public void setTitle(@NonNull String title) {
        titleTV.setText(title);
    }

    /**
     * Sets the resource id for the imageView
     * @param resourceId Resource id for the image to be shown
     */
    public void setImageResourceId(@DrawableRes int resourceId) {
        imageView.setImageResource(resourceId);
    }

    /**
     * Sets the drawable for the imageView
     * @param drawable Drawable to set in the imageView
     */
    public void setImageDrawable(@NonNull Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    /**
     * Sets Title size
     * @param size Size
     */
    public void setTitleSize(float size) {
        titleTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, size/dpi);
    }

    /**
     * Sets Description size
     * @param size Size
     */
    public void setDescriptionSize(float size) {
        descTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, size/dpi);
    }

    /**
     * Sets title typeface
     * @param typeface Typeface (pass null to clear current typeface, or use getTitleTypeface())
     * @param style Style to set
     */
    public void setTitleTypeface(@Nullable Typeface typeface, int style) {
        titleTV.setTypeface(typeface, style);
    }

    /**
     * Sets description typeface
     * @param typeface Typeface (pass null to clear current typeface, or use getDescriptionTypeface())
     * @param style  Style to set
     */
    public void setDescriptionTypeface(@Nullable Typeface typeface, int style) {
        descTV.setTypeface(typeface, style);
    }

    /**
     * Returns title typeface
     * @return Typeface
     */
    public Typeface getTitleTypeface() {
        return titleTV.getTypeface();
    }

    /**
     * Return description typeface
     * @return Typeface
     */
    public Typeface getDescriptionTypeface() {
        return descTV.getTypeface();
    }

    /**
     * Sets image height
     * @param height Height
     */
    public void setImageHeight(@Dimension int height) {
        imageView.getLayoutParams().height = height;
        imageView.requestLayout();
    }

    /**
     * Sets image width
     * @param width Width
     */
    public void setImageWidth(@Dimension int width) {
        imageView.getLayoutParams().width = width;
        imageView.requestLayout();
    }

    /**
     * Sets tint of this view
     * @param color Tint color
     */
    public void setViewTint(@ColorInt int color) {
        imageView.setColorFilter(color);
        titleTV.setTextColor(color);
        descTV.setTextColor(color);
    }

    /**
     * Make the views used user accessible for advanced stuff
     * @return Title textview
     */
    public TextView getTitleTextView() {
        return titleTV;
    }

    /**
     * Make the views used user accessible for advanced stuff
     * @return Description textview
     */
    public TextView getDescriptionTextView() {
        return descTV;
    }

    /**
     * Make the views used user accessible for advanced stuff
     * @return imageview
     */
    public AppCompatImageView getImageView() {
        return imageView;
    }
}
