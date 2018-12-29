package nec.cst.pra;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class CustomFontEditText extends android.support.v7.widget.AppCompatEditText {

    String tittle = "";
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    private String result;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private String TAG_DATE_PICKER_DIALOG = "3000";

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(final Context context, AttributeSet attrs) {
        final TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontTextView);

        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_fontName);

        final String category = attributeArray.getString(R.styleable.CustomFontTextView_category);
        final String choices = attributeArray.getString(R.styleable.CustomFontTextView_choices);
        tittle = attributeArray.getString(R.styleable.CustomFontTextView_tittle);
        try {
            tittle = getHint().toString();
        } catch (Exception e) {
            Log.e("xxxxxxxxxx", e.toString());
        }
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
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
                        } else if (category != null && category.equals("date")) {
                            pickDate(context);
                        } else if (category != null && category.equals("time")) {
                            pickTime(context);
                        }
                    }
                }
            }
        });


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    } else if (category != null && category.equals("date")) {
                        pickDate(context);
                    } else if (category != null && category.equals("time")) {
                        pickTime(context);
                    }
                }
            }
        });
        if (fontName != null) {
            Typeface customFont = selectTypeface(context, fontName);
            setTypeface(customFont);
        }


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

    private void pickDate(Context context) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void pickTime(Context context) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}