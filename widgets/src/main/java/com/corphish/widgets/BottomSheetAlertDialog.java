package com.corphish.widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;

/**
 * Created by avinabadalal on 03/02/18.
 * Bottom sheet alert dialog, kinda material replacement for alert dialogs
 * You can use it to replace alert dialog messages
 * It wraps up only the basic alert dialog, which contains title, message, and positive, negative and neutral buttons
 */

public class BottomSheetAlertDialog {

    public static class Builder {
        Context context;
        @StyleRes int themeId;

        // Dialog content
        String title, message, positiveButtonText, negativeButtonText, neutralButtonText;

        DialogInterface.OnClickListener positiveButtonClickListener, negativeButtonClickListener, neutralButtonListener;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder(@NonNull Context context, @StyleRes int themeId) {
            this.context = context;
            this.themeId = themeId;
        }

        public Builder setTitle(String title) {
            this.title = title;

            return this;
        }

        public Builder setTitle(@StringRes int titleId) {
            this.title = context.getString(titleId);

            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;

            return this;
        }

        public Builder setMessage(@StringRes int messageId) {
            this.message = context.getString(messageId);

            return this;
        }

        public Builder setPositiveButton(String message, DialogInterface.OnClickListener onClickListener) {
            this.positiveButtonText = message;
            this.positiveButtonClickListener = onClickListener;

            return this;
        }

        public Builder setPositiveButton(@StringRes int messageId, DialogInterface.OnClickListener onClickListener) {
            this.positiveButtonText = context.getString(messageId);
            this.positiveButtonClickListener = onClickListener;

            return this;
        }
        public Builder setNegativeButton(String message, DialogInterface.OnClickListener onClickListener) {
            this.negativeButtonText = message;
            this.negativeButtonClickListener = onClickListener;

            return this;
        }

        public Builder setNegativeButton(@StringRes int messageId, DialogInterface.OnClickListener onClickListener) {
            this.negativeButtonText = context.getString(messageId);
            this.negativeButtonClickListener = onClickListener;

            return this;
        }


        public Builder setNeutralButton(String message, DialogInterface.OnClickListener onClickListener) {
            this.neutralButtonText = message;
            this.neutralButtonListener = onClickListener;

            return this;
        }

        public Builder setNeutralButton(@StringRes int messageId, DialogInterface.OnClickListener onClickListener) {
            this.neutralButtonText = context.getString(messageId);
            this.neutralButtonListener = onClickListener;

            return this;
        }

        public void show() {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, themeId);
            bottomSheetDialog.setContentView(LayoutInflater.from(context).inflate(R.layout.bottom_sheet_alert_dialog, null));
            bottomSheetDialog.show();
        }
    }
}
