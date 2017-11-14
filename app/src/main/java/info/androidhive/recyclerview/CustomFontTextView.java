package info.androidhive.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;


public class CustomFontTextView extends android.support.v7.widget.AppCompatTextView implements DatePickerDialog.OnDateSetListener {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    private String result;

    private String TAG_DATE_PICKER_DIALOG = "3000";

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(final Context context, AttributeSet attrs) {
        final TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontTextView);

        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_font);
        final String category = attributeArray.getString(R.styleable.CustomFontTextView_category);
        final String choices = attributeArray.getString(R.styleable.CustomFontTextView_choices);
        final String tittle = attributeArray.getString(R.styleable.CustomFontTextView_tittle);
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        if (fontName != null) {
            Typeface customFont = selectTypeface(context, fontName);
            setTypeface(customFont);
        }
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (view instanceof TextView) {
                    final TextView textView = (TextView) view;
                    if (category != null && category.equals(getResources().getString(R.string.selectable))) {
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                        builderSingle.setTitle("Select " + tittle + ":-");
                        String[] choicesStrings = choices.split(",");
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
                        for (int i = 0; i < choicesStrings.length; i++) {
                            arrayAdapter.add(choicesStrings[i]);
                        }
                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String strName = arrayAdapter.getItem(which);
                                if (strName.equalsIgnoreCase("Others")) {
                                    othersModule(tittle, context, textView);
                                } else {
                                    setText(strName);
                                }
                            }
                        });
                        builderSingle.show();
                    } else if (category != null && category.equals(getResources().getString(R.string.dateandtime))) {
                        try {
                            final FragmentActivity activity = (FragmentActivity) context;
                            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(CustomFontTextView.this, 2017, 9, 5);
                            datePickerDialog.setYearRange(1903, 2017);
                            datePickerDialog.setCloseOnSingleTapDay(true);
                            datePickerDialog.show(activity.getSupportFragmentManager(), TAG_DATE_PICKER_DIALOG);
                        } catch (ClassCastException e) {
                            Log.d("xxxxxxxxxxx", "Can't get the fragment manager with this");
                        }
                    }
                }
            }
        });

        attributeArray.recycle();
    }

    private Typeface selectTypeface(Context context, String fontName) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName + ".ttf");
    }

    public String othersModule(String tittle, final Context context, final TextView textView) {
        result = "";
        LayoutInflater li = LayoutInflater.from(context);
        View dialogView = li.inflate(R.layout.dialog_activity, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle(tittle);
        alertDialogBuilder.setView(dialogView);
        final EditText userInput = (EditText) dialogView
                .findViewById(R.id.et_input);
        userInput.setHint("Enter " + tittle);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog,

                                                int id) {
                                if (userInput.getText().toString().length() > 0) {
                                    result = userInput.getText().toString();
                                    textView.setText(result);
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                if (userInput.getText().toString().length() > 0) {
                                    dialog.cancel();
                                }
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return result;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        setText(String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day));
    }
}