package com.example.vantrantrucphuong.dreamstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.vantrantrucphuong.dreamstore.R;
import com.example.vantrantrucphuong.dreamstore.adapter.AdidasAdapter;
import com.example.vantrantrucphuong.dreamstore.model.SanPham;
import com.example.vantrantrucphuong.dreamstore.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdidasActivity extends AppCompatActivity {
    Toolbar toolbar_Adidas;
    ListView listView_Adidas;
    AdidasAdapter adidasAdapter;
    SearchView searchView;
    ArrayList<SanPham> arrayAdidas;
    int idTenCL = 0;

//    Search
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adidas);
        setControl();
        GetIDLoaiSP();
        actionBar();
        GetData();
        loadData();
    }

    private void GetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlSP_Adidas, new Response.Listener<JSONArray>() {
            //            nhận dữ liệu trả về từ server khi request hoàn thành
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int idCL = 0;
                    int idSP_Adidas = 0;
                    String tenSP = "";
                    int giaSP = 0;
                    String urlHinh = "";
                    for(int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idCL = jsonObject.getInt("id_CL");
                            idSP_Adidas = jsonObject.getInt("idSP");
                            tenSP = jsonObject.getString("TenSP");
                            giaSP = jsonObject.getInt("Gia");
                            urlHinh = jsonObject.getString("UrlHinh");
//                            urlHinh = "http://" + Server.localhost + jsonObject.getString("UrlHinh");
                            arrayAdidas.add(new SanPham(urlHinh, tenSP, giaSP, idSP_Adidas, idCL));
                            adidasAdapter.notifyDataSetChanged();
                        }
                        catch (JSONException e){
                            Log.d("ERROR SP ADIDAS : ", e.toString());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionBar() {
        //        hỗ trợ toolbar như actionBar
        setSupportActionBar(toolbar_Adidas);
//        set nút Home toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_Adidas.setNavigationIcon(R.drawable.ic_action_back);
//        bắt sự kiện click vào
        toolbar_Adidas.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIDLoaiSP() {
        idTenCL = getIntent().getIntExtra("idTenCL", -1);
        Log.d("Id Chủng loại: ", idTenCL + "");
    }

    private void setControl(){
        toolbar_Adidas = (Toolbar) findViewById(R.id.toolbar_adidas);
        listView_Adidas = (ListView) findViewById(R.id.listView_Adidas);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adidasAdapter.getFilter().filter(query);
                return false;
            }
        });

        arrayAdidas = new ArrayList<>();
        adidasAdapter = new AdidasAdapter(getApplicationContext(), arrayAdidas);
        listView_Adidas.setAdapter(adidasAdapter);
        listView_Adidas.setTextFilterEnabled(true);
        adidasAdapter.notifyDataSetChanged();
    }

    private void loadData(){
        listView_Adidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
//                Muốn truyền dữ liêu Object cho màn hình khác qua khuôn của mảng
                intent.putExtra("chitietsanpham", arrayAdidas.get(i));
                startActivity(intent);
            }
        });
    }
}
