package ru.ebi.romaprepod.ui.widget;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ru.ebi.romaprepod.R;

@SuppressWarnings("unused")
public class DialogFactory {

	public static AlertDialog newProgressDialog(Context context, String msg) {
		View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false);
		TextView msgView = (TextView) dialogView.findViewById(R.id.message);
		msgView.setText(msg);

		AlertDialog dialog = new AlertDialog.Builder(context)
				.setCancelable(false)
				.setView(dialogView)
				.create();

		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}

	public static AlertDialog newProgressDialog(Context context, @StringRes int msgId) {
		return newProgressDialog(context, context.getString(msgId));
	}
}