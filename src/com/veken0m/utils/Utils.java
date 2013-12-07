
package com.veken0m.utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class Utils {

    public static String formatDecimal(float valueToFormat,
            int numberOfDecimalPlaces, boolean useGroupings) {

        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(numberOfDecimalPlaces);
        numberFormat.setMinimumFractionDigits(numberOfDecimalPlaces);
        // Remove grouping if commas cause errors when parsing to double/float
        numberFormat.setGroupingUsed(useGroupings);

        return numberFormat.format(valueToFormat);
    }

    public static String formatDecimal(BigDecimal valueToFormat,
            int numberOfDecimalPlaces, boolean useGroupings) {

        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(numberOfDecimalPlaces);
        numberFormat.setMinimumFractionDigits(numberOfDecimalPlaces);
        numberFormat.setGroupingUsed(useGroupings);

        try {
            return numberFormat.format(valueToFormat.doubleValue());
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String formatWidgetMoney(float amount, String currencyCode,
            boolean includeCurrencyCode) {

        String symbol = getCurrencySymbol(currencyCode);
        int numOfDecimals = 2;

        if (includeCurrencyCode) {
            currencyCode = " " + currencyCode;
        } else {
            currencyCode = "";
        }

        // If too large, remove a digit behind decimal
        if (amount >= 1000 && !includeCurrencyCode) {
            numOfDecimals = 1;
        }

        // If too small, scale the value
        if (amount < 0.1) {
            amount *= 1000;
            currencyCode = currencyCode.replace(" ", " m");
        }

        return symbol + formatDecimal(amount, numOfDecimals, false) + currencyCode;
    }

    public static String getCurrencySymbol(String currencyCode) {

        String symbol = "";

        if (!(currencyCode.equalsIgnoreCase("DKK")
                || currencyCode.equalsIgnoreCase("BTC")
                || currencyCode.equalsIgnoreCase("LTC")
                || currencyCode.equalsIgnoreCase("NMC")
                || currencyCode.equalsIgnoreCase("PLN")
                || currencyCode.equalsIgnoreCase("RUB")
                || currencyCode.equalsIgnoreCase("SEK")
                || currencyCode.equalsIgnoreCase("SGD")
                || currencyCode.equalsIgnoreCase("XVN")
                || currencyCode.equalsIgnoreCase("XRP")
                || currencyCode.equalsIgnoreCase("CHF")
                || currencyCode.equalsIgnoreCase("RUR"))) {
            symbol = CurrencyUnit.of(currencyCode).getSymbol();
            symbol = symbol.substring(symbol.length() - 1);
        }

        return symbol;
    }

    public static boolean isBetween(float value, float min, float max) {

        return ((value >= min) && (value <= max));
    }

    public static String getCurrentTime(Context ctxt) {
        Date time = new Date();
        DateFormat.getTimeFormat(ctxt).format(time);

        StringBuilder sb = new StringBuilder();
        sb.append(DateFormat.format("E", time));
        sb.append(" ");
        sb.append(DateFormat.getTimeFormat(ctxt).format(time));

        return sb.toString();
    }

    public static String dateFormat(Context ctxt, long date) {
        Date dateFormatted = new Date(date);
        StringBuilder sb = new StringBuilder();
        sb.append(DateFormat.format("MMM dd", dateFormatted));
        sb.append(" @ ");
        sb.append(DateFormat.getTimeFormat(ctxt).format(dateFormatted));
        return sb.toString();
    }

    public static void setTextViewParams(TextView tv, String text) {

        LayoutParams params = new TableRow.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        tv.setText(text);
        tv.setLayoutParams(params);
        tv.setGravity(1);
    }

    public static String formatHashrate(float hashRate) {

        DecimalFormat df = new DecimalFormat("#0.00");
        if (hashRate > 999) {
            return df.format((hashRate / 1000)) + " GH/s";
        } else {
            return df.format((hashRate)) + " MH/s";
        }
    }

}