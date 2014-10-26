package org.ckwong.yelprunner.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.ckwong.yelprunner.R;
import org.ckwong.yelprunner.adapter.YelpBusinessAdapter;
import org.ckwong.yelprunner.service.ServiceCallback;
import org.ckwong.yelprunner.service.ServiceError;
import org.ckwong.yelprunner.service.YelpService;
import org.ckwong.yelprunner.util.ViewHolder;

public class YelpSearchActivity extends Activity {

    ViewHolder holder;
    volatile boolean canceled;
    YelpBusinessAdapter businessAdapter;
    InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelp_search);

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        holder = new ViewHolder(findViewById(R.id.yelpSearch));

        businessAdapter = new YelpBusinessAdapter(this);
        ListView listView = (ListView) holder.getView(R.id.resultList);
        listView.setAdapter(businessAdapter);

        bindUiListeners();
    }

    void bindUiListeners() {
        final Context context = this;
        Button goButton = (Button) holder.getView(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText findField = (EditText) holder.getView(R.id.findField);
                String find = findField.getText().toString();
                if (find == null || find.length() == 0) {
                    Toast toast = Toast.makeText(context, "Find field can't be empty", Toast.LENGTH_LONG);
                    toast.show();
                }

                EditText nearField = (EditText) holder.getView(R.id.nearField);
                String near = nearField.getText().toString();
                if (near == null || near.length() == 0) {
                    Toast toast = Toast.makeText(context, "Near field can't be empty", Toast.LENGTH_LONG);
                    toast.show();
                }

                hideKeyboard(findField);
                hideKeyboard(nearField);

                final ProgressBar progressBar = (ProgressBar) holder.getView(R.id.searchProgress);
                progressBar.setVisibility(View.VISIBLE);

                YelpService.getInstance().searchBusinesses(find, near, 20,
                        new ServiceCallback<YelpService.Result, ServiceError>() {

                            @Override
                            public void onSuccess(YelpService.Result result) {
                                progressBar.setVisibility(View.GONE);

                                businessAdapter.setYelpServiceResult(result);
                                businessAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(ServiceError failureObj) {
                                progressBar.setVisibility(View.GONE);

                                Toast toast = Toast.makeText(context, "Error in searching : " +
                                        failureObj.getMessage(), Toast.LENGTH_LONG);
                                toast.show();
                            }

                            @Override
                            public boolean isCanceled() {
                                return canceled;
                            }
                        });

            }
        });
    }

    void hideKeyboard(EditText editText) {
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        canceled = true;
        businessAdapter.setCanceled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.yelp_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
