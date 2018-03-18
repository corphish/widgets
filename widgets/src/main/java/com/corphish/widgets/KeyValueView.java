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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * Key Value view
 * A container having 2 text views
 * One showing the heading or key and the other one showing its value
 * Written as separate library cause it is used it widely
 */

public class KeyValueView extends LinearLayout {
    /**
     * 2 text views
     * We will work with these 2
     */
    TextView key, value;

    // To handle sizes
    float dpi;

    // Prefer method set value over property set
    boolean methodSetValue = false;

    /**
     * Constructor
     * @param context Context
     */
    public KeyValueView(Context context) {
        this(context, null);
    }

    /**
     * Constructor
     * @param context Context
     * @param attributeSet AttributeSet
     */
    public KeyValueView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /**
     * Constructor
     * @param context Context
     * @param attributeSet AttributeSet
     * @param defStyle Default Style
     */
    public KeyValueView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);

        LayoutInflater.from(context).inflate(R.layout.key_value_view, this);

        key = findViewById(R.id.key);
        value = findViewById(R.id.value);

        dpi = (float)Resources.getSystem().getDisplayMetrics().densityDpi/(float)DisplayMetrics.DENSITY_DEFAULT;

        processProperties(context, attributeSet);
    }

    private void processProperties(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.KeyValueView);

        if (typedArray != null) {
            int count = typedArray.getIndexCount();
            for (int i = 0; i < count; i++) {
                int property = typedArray.getIndex(i);

                if (property == R.styleable.KeyValueView_keyText) {
                    setKeyText(typedArray.getText(property).toString());
                    continue;
                }
                if (property == R.styleable.KeyValueView_valueText) {
                    if (!methodSetValue) setValueText(typedArray.getText(property).toString());
                    continue;
                }
                if (property == R.styleable.KeyValueView_keySize) {
                    setKeySize(typedArray.getDimension(property, key.getTextSize()));
                    continue;
                }
                if (property == R.styleable.KeyValueView_valueSize) {
                    setValueSize(typedArray.getDimension(property, value.getTextSize()));
                    continue;
                }
                if (property == R.styleable.KeyValueView_keyEnabled) {
                    setEnabled(typedArray.getBoolean(property, true), value.isEnabled());
                    continue;
                }
                if (property == R.styleable.KeyValueView_valueEnabled) {
                    setEnabled(key.isEnabled(), typedArray.getBoolean(property, true));
                    continue;
                }
                if (property == R.styleable.KeyValueView_keyStyle) {
                    setKeyTypeface(getKeyTypeface(), typedArray.getInt(property, -1));
                    continue;
                }
                if (property == R.styleable.KeyValueView_valueStyle) {
                    setValueTypeface(getValueTypeface(), typedArray.getInt(property, -1));
                    continue;
                }
                if (property == R.styleable.KeyValueView_absoluteSpacing) {
                    setAbsoluteSpacing(typedArray.getDimensionPixelSize(property, 1));
                    continue;
                }
                if (property == R.styleable.KeyValueView_absoluteSpacingTop) {
                    setAbsoluteSpacing(typedArray.getDimensionPixelSize(property, 1), value.getPaddingTop());
                    continue;
                }
                if (property == R.styleable.KeyValueView_absoluteSpacingBottom) {
                    setAbsoluteSpacing(key.getPaddingBottom(), typedArray.getDimensionPixelSize(property, 1));
                    continue;
                }
                if (property == R.styleable.KeyValueView_relativeSpacing) {
                    setRelativeSpacing(typedArray.getDimensionPixelSize(property, 1));
                    continue;
                }
                if (property == R.styleable.KeyValueView_relativeSpacingTop) {
                    setRelativeSpacing(typedArray.getDimensionPixelSize(property, 1), value.getPaddingTop());
                    continue;
                }
                if (property == R.styleable.KeyValueView_relativeSpacingBottom) {
                    setRelativeSpacing(key.getPaddingBottom(), typedArray.getDimensionPixelSize(property, 1));
                    continue;
                }
                if (property == R.styleable.KeyValueView_keyTextColor) {
                    setKeyTextColor(typedArray.getColor(property, key.getCurrentTextColor()));
                    continue;
                }
                if (property == R.styleable.KeyValueView_valueTextColor) {
                    setValueTextColor(typedArray.getColor(property, value.getCurrentTextColor()));
                    continue;
                }
                if (property == R.styleable.KeyValueView_keyBackgroundColor) {
                    int color = Color.TRANSPARENT;
                    if (key.getBackground() instanceof ColorDrawable) {
                        color = ((ColorDrawable) key.getBackground()).getColor();
                    }
                    setKeyBackgroundColor(typedArray.getColor(property, color));
                    continue;
                }
                if (property == R.styleable.KeyValueView_valueBackgroundColor) {
                    int color = Color.TRANSPARENT;
                    if (value.getBackground() instanceof ColorDrawable) {
                        color = ((ColorDrawable) value.getBackground()).getColor();
                    }
                    setValueBackgroundColor(typedArray.getColor(property, color));
                    continue;
                }
                if (property == R.styleable.KeyValueView_methodForValue) {
                    if (context.isRestricted()) throw new IllegalStateException("The app:methodForValue cannot be used in a restricted context");

                    String methodName = typedArray.getString(property);
                    if (methodName != null) processMethod(methodName);
                }
                if (property == R.styleable.KeyValueView_keyAppearance) {
                    setKeyTextAppearance(typedArray.getResourceId(property, android.R.attr.textAppearance));
                }
                if (property == R.styleable.KeyValueView_valueAppearance) {
                    setValueTextAppearance(typedArray.getResourceId(property, android.R.attr.textAppearanceSmall));
                }
            }
            typedArray.recycle();
        }
    }

    private void processMethod(@NonNull String methodName) {
        // This method expects the method in form of domainname.orgname.projectname.className.subClassName.methodName
        // Either way, the last part must be the method, the rest must be class

        String className;
        Context context = getContext();
        String methodExtracted = methodName.substring(methodName.lastIndexOf(".") + 1);
        if (methodName.contains("."))
            className = methodName.substring(0, methodName.lastIndexOf("."));
        else {
            //throw new IllegalArgumentException("The method name supplied must be absolute");
            className = context.getClass().getName();
        }

        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodExtracted);
            String value = (String) method.invoke(context);
            if (value != null) {
                setValueText(value);
                methodSetValue = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets text into the <strong>Key</strong> field (or TextView in this case)
     * @param text Text to set
     */
    public void setKeyText(@NonNull String text) {
        key.setText(text);
    }

    /**
     * Sets text into the <strong>Key</strong> field (or TextView in this case)
     * @param resId String resource id
     */
    public void setKeyText(@StringRes int resId) {
        key.setText(resId);
    }

    /**
     * Sets text into the <strong>Value</strong> field (or TextView in this case)
     * @param text Text to set
     */
    public void setValueText(@NonNull String text) {
        value.setText(text);
    }

    /**
     * Sets text into the <strong>Value</strong> field (or TextView in this case)
     * @param resId String resource id
     */
    public void setValueText(@StringRes int resId) {
        value.setText(resId);
    }

    /**
     * Enables or disables each view as needed
     * @param keyEnabled Whether or not to enable Key
     * @param valueEnabled Whether or not to enable Value
     */
    public void setEnabled(boolean keyEnabled, boolean valueEnabled) {
        // Set the key and value enabled as required
        key.setEnabled(keyEnabled);
        value.setEnabled(valueEnabled);

        // Disable the entire view if all of them are false
        // Enable if any one of them is true
        super.setEnabled(keyEnabled | valueEnabled);
    }

    /**
     * Enables or disables the entire view
     * @param enabled Whether or not to enable this view
     */
    public void setEnabled(boolean enabled) {
        this.setEnabled(enabled, enabled);
    }

    /**
     * Sets Key size
     * @param size Size
     */
    public void setKeySize(float size) {
        key.setTextSize(TypedValue.COMPLEX_UNIT_SP, size/dpi);
    }

    /**
     * Sets Value size
     * @param size Size
     */
    public void setValueSize(float size) {
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP, size/dpi);
    }

    /**
     * Sets the padding of the 2 text views
     * @param dp Padding in dp
     */
    public void setPadding(int dp) {
        key.setPadding(dp, dp, dp, dp);
        value.setPadding(dp, dp, dp, dp);
    }

    /**
     * Sets absolute vertical spacing in between the Key and Value
     * The passed parameters are applied directly
     * @param spacingBelowKey Spacing below key in dp
     * @param spacingAboveValue Spacing above value in dp
     */
    public void setAbsoluteSpacing(int spacingBelowKey, int spacingAboveValue) {
        key.setPadding(key.getPaddingLeft(), key.getPaddingTop(), key.getPaddingRight(), spacingBelowKey);
        value.setPadding(value.getPaddingLeft(), spacingAboveValue, value.getPaddingRight(), value.getPaddingBottom());
    }

    /**
     * Sets absolute vertical spacing in between the Key and Value
     * @param spacing Spacing in dp
     */
    public void setAbsoluteSpacing(int spacing) {
        setAbsoluteSpacing(spacing, spacing);
    }

    /**
     * Sets relative spacing in between the Key and Value
     * Use this to increase or decrease the spacing
     * A positive value means spacing will be increased, a negative value
     * @param spacingBelowKey Spacing in dp
     * @param spacingAboveValue Spacing in dp
     */
    public void setRelativeSpacing(int spacingBelowKey, int spacingAboveValue) {
        setAbsoluteSpacing(key.getPaddingBottom() + spacingBelowKey, value.getPaddingTop() + spacingAboveValue);
    }

    /**
     * Sets relative spacing in between the Key and Value
     * Use this to increase or decrease the spacing
     * A positive value means spacing will be increased, a negative value
     * @param spacing Spacing in dp
     */
    public void setRelativeSpacing(int spacing) {
        setRelativeSpacing(spacing, spacing);
    }

    /**
     * Sets typeface for Key
     * @param typeface Typeface
     * @param style Text Style
     */
    public void setKeyTypeface(@Nullable Typeface typeface, int style) {
        key.setTypeface(typeface, style);
    }

    /**
     * Sets typeface for Value
     * @param typeface Typeface
     * @param style Text Style
     */
    public void setValueTypeface(@Nullable Typeface typeface, int style) {
        value.setTypeface(typeface, style);
    }

    /**
     * Gets typeface of key
     * @return Key typeface
     */
    public Typeface getKeyTypeface() {
        return key.getTypeface();
    }

    /**
     * Gets value typeface
     * @return Value typeface
     */
    public Typeface getValueTypeface() {
        return value.getTypeface();
    }

    /**
     * Sets text color of key
     * @param color Color to set
     */
    public void setKeyTextColor(int color) {
        key.setTextColor(color);
    }

    /**
     * Sets text color of value
     * @param color Color to set
     */
    public void setValueTextColor(int color) {
        value.setTextColor(color);
    }

    /**
     * Sets background color of key
     * @param color Color to set
     */
    public void setKeyBackgroundColor(int color) {
        key.setBackgroundColor(color);
    }

    /**
     * Sets background color of value
     * @param color Color to set
     */
    public void setValueBackgroundColor(int color) {
        value.setBackgroundColor(color);
    }

    /**
     * Set drawables for key
     * @param left Left drawable
     * @param top Top drawable
     * @param right Right drawable
     * @param bottom Bottom drawable
     */
    public void setKeyDrawables(@DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        key.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * Set drawables for value
     * @param left Left drawable
     * @param top Top drawable
     * @param right Right drawable
     * @param bottom Bottom drawable
     */
    public void setValueDrawables(@DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        value.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * Set relative drawables for key
     * @param start Start drawable
     * @param top Top drawable
     * @param end End drawable
     * @param bottom Bottom drawable
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setKeyRelativeDrawables(@DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        key.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    /**
     * Set relative drawables for value
     * @param start Start drawable
     * @param top Top drawable
     * @param end End drawable
     * @param bottom Bottom drawable
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setValueRelativeDrawables(@DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        value.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    /**
     * Sets text appearance of key
     * @param appearance Appearance
     */
    public void setKeyTextAppearance(@StyleRes int appearance) {
        key.setTextAppearance(getContext(), appearance);
    }

    /**
     * Sets text appearance of value
     * @param appearance Appearance
     */
    public void setValueTextAppearance(@StyleRes int appearance) {
        value.setTextAppearance(getContext(), appearance);
    }

    /**
     * Just in case user wants to use the full capabilities of a TextView or do advanced stuff
     * @return Key
     */
    public TextView getKeyTextView() {
        return key;
    }

    /**
     * Just in case user wants to use the full capabilities of a TextView or do advanced stuff
     * @return Value
     */
    public TextView getValueTextView() {
        return value;
    }
}
