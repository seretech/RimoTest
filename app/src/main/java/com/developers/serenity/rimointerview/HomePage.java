package com.developers.serenity.rimointerview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

public class HomePage extends AppCompatActivity {

    private RequestQueue requestQueue;
    private ListView ls;
    private ProgressBar prog;
    private ArrayList <TransClass> arrayList;
    private BottomNavigationView navView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        TextView txt = findViewById(R.id.txt);
        TextView txt1 = findViewById(R.id.hide);

        ImageView img = findViewById(R.id.pic);
        ls = findViewById(R.id.ls);
        prog = findViewById(R.id.prog);

        prog.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.home), PorterDuff.Mode.SRC_IN);

        requestQueue = Volley.newRequestQueue(this);

        txt1.setOnClickListener(v-> {
            if(txt.getText().toString().trim().matches("200,000")){
                txt.setText("**********");
            } else {
                txt.setText("200,000");
            }
        });

        Picasso.get()
                .load("null")
                .fit()
                .transform(new PicassoCircular())
                .placeholder(R.drawable.pic)
                .centerInside()
                .into(img);

        loadData();

    }

    private void loadData() {
        arrayList = new ArrayList<>();
        String url = "https://api.jsonbin.io/b/6201d3274ce71361b8d2735c/2";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int k = 0; k < jsonArray.length(); k++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(k);
                            String img = jsonObject1.optString("img", "na");
                            String ds = jsonObject1.optString("desc", "na");
                            String am = jsonObject1.optString("amount", "na");
                            String st = jsonObject1.optString("state", "na");
                            String da = jsonObject1.optString("date", "na");

                            TransClass transClass = new TransClass();
                            transClass.setImg(img);
                            transClass.setDesc(ds);
                            transClass.setAmount(am);
                            transClass.setState(st);
                            transClass.setDate(da);
                            arrayList.add(transClass);

                        }
                        arrayList.sort(Comparator.comparing(TransClass::getDate));
                        TransAdapter transAdapter = new TransAdapter(this, R.layout.list_adapter, arrayList);
                        transAdapter.setListData(arrayList);
                        ls.setAdapter(transAdapter);
                        prog.setVisibility(View.INVISIBLE);

                    } catch (JSONException e) {
                        Toast.makeText(this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                e -> Toast.makeText(this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(getString(R.string.sure_to_exit))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.no),
                        (dialog, which) -> dialog.dismiss())
                .setNegativeButton(getString(R.string.yes_exit),
                        (dialog, which) -> finishAffinity());
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }
}

