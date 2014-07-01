package com.veken0m.bitcoinium.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.veken0m.bitcoinium.BalanceWidgetProvider;
import com.veken0m.bitcoinium.BitcoinAverageActivity;
import com.veken0m.bitcoinium.BitcoinChartsActivity;
import com.veken0m.bitcoinium.GraphActivity;
import com.veken0m.bitcoinium.MainActivity;
import com.veken0m.bitcoinium.MinerStatsActivity;
import com.veken0m.bitcoinium.MinerWidgetProvider;
import com.veken0m.bitcoinium.OrderbookActivity;
import com.veken0m.bitcoinium.R;
import com.veken0m.bitcoinium.WebViewerActivity;
import com.veken0m.bitcoinium.WidgetProvider;
import com.veken0m.bitcoinium.exchanges.Exchange;
import com.veken0m.utils.Constants;
import com.xeiam.xbtctrader.XTraderActivity;

public class HomeMenuFragment extends Fragment {

    private Activity activity = null;
    private Context context = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();
        context = activity.getApplicationContext();

        String exchangeName = getArguments().getString("exchange");

        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        buildMenu(view, exchangeName);
        return view;
    }

    // Attaches OnClickListeners to menu buttons
    void buildMenu(View view, final String exchangeName) {

        final Button widgetRefreshButton = (Button) view.findViewById(R.id.widgetrefresh);
        widgetRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.sendBroadcast(new Intent(context, WidgetProvider.class).setAction(Constants.REFRESH));
                activity.sendBroadcast(new Intent(context, MinerWidgetProvider.class).setAction(Constants.REFRESH));
                activity.sendBroadcast(new Intent(context, BalanceWidgetProvider.class).setAction(Constants.REFRESH));

                activity.moveTaskToBack(true);
            }
        });

        final Button displayGraphButton = (Button) view.findViewById(R.id.displaygraph);
        displayGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent graphActivity = new Intent(context, GraphActivity.class);

                Exchange exchange = null;
                try {
                    exchange = new Exchange(context, exchangeName);
                } catch (Exception e) {
                    // Do nothing
                }

                if (exchange != null && exchange.supportsTrades())
                    graphActivity.putExtra("exchange", exchange.getIdentifier());
                else
                    graphActivity.removeExtra("exchange");

                startActivity(graphActivity);
            }
        });

        final Button orderbookButton = (Button) view.findViewById(R.id.orderbook);
        orderbookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent orderbookActivity = new Intent(context, OrderbookActivity.class);

                Exchange exchange = null;
                try {
                    exchange = new Exchange(context, exchangeName);
                } catch (Exception e) {
                    // Do nothing
                }

                if (exchange != null && exchange.supportsOrderbook())
                    orderbookActivity.putExtra("exchange", exchange.getIdentifier());
                else
                    orderbookActivity.removeExtra("exchange");

                startActivity(orderbookActivity);
            }
        });

        final Button bitcoinChartsButton = (Button) view.findViewById(R.id.bitcoincharts);
        bitcoinChartsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bitcoinChartsActivity = new Intent(context, BitcoinChartsActivity.class);
                startActivity(bitcoinChartsActivity);
            }
        });

        final Button bitcoinAverageButton = (Button) view.findViewById(R.id.bitcoinaverage);
        bitcoinAverageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bitcoinAverageActivity = new Intent(context, BitcoinAverageActivity.class);
                startActivity(bitcoinAverageActivity);
            }
        });

        final Button minerStatsButton = (Button) view.findViewById(R.id.minerstats);
        minerStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent minerstatsActivity = new Intent(context, MinerStatsActivity.class);
                startActivity(minerstatsActivity);
            }
        });

        final Button marketDepth = (Button) view.findViewById(R.id.marketdepth);
        marketDepth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webViewerActivity = new Intent(context, WebViewerActivity.class);
                startActivity(webViewerActivity);
            }
        });

        final Button xTrader = (Button) view.findViewById(R.id.xtrader);
        if (xTrader != null) {
            xTrader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                    // TODO: externalize
                    final CharSequence items[] = new CharSequence[] {"bitstampUSD", "btcchinaCNY", "krakenUSD", "krakenEUR", "btceUSD", "btceRUR", "btceEUR"};
                    adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface d, int n) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), XTraderActivity.class);
                            intent.putExtra("exchange", items[n]);
                            startActivity(intent);
                        }

                    });
                    adb.setNegativeButton("Cancel", null);
                    adb.setTitle("Select a market symbol");
                    adb.show();
                }
            });
        }
    }

}
