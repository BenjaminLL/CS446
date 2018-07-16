package com.example.gograd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class ProgramDialog extends AppCompatDialogFragment {

    private NumberPicker picker;
    private DialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.program_dialog, null);

        // get the passed arguments
        final String[] values = this.getArguments().getStringArray("values");
        String title = this.getArguments().getString("dialog_title");

        // get components
        picker = view.findViewById(R.id.picker);

        builder.setView(view);
        builder.setTitle(title);
        builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // set text on option or academic year
                int pos = picker.getValue();
                String result = values[pos];
                String object = values[values.length - 1];

                dialogListener.applyTexts(object, result);
            }
        });

        picker.setMinValue(0);
        picker.setMaxValue(values.length - 2);
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(values);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    public interface DialogListener {
        void applyTexts(String object, String value);
    }
}
