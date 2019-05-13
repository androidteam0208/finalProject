package com.example.vantrantrucphuong.dreamstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.vantrantrucphuong.dreamstore.R;
import com.example.vantrantrucphuong.dreamstore.adapter.CategoryAdapter;
import com.example.vantrantrucphuong.dreamstore.adapter.SanPhamAdapter;
import com.example.vantrantrucphuong.dreamstore.model.GioHangModel;
import com.example.vantrantrucphuong.dreamstore.model.ProductCategory;
import com.example.vantrantrucphuong.dreamstore.model.SanPham;
import com.example.vantrantrucphuong.dreamstore.util.KiemTraKetNoi;
import com.example.vantrantrucphuong.dreamstore.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listViewMain;
//    CHUNG LOAI
    ArrayList<ProductCategory> arrProductCategory;
    CategoryAdapter categoryAdapter;
    int idCL = 0;
    String tenCL = "";
//    SAN PHAM
    ArrayList<SanPham> arrSanPham;
    SanPhamAdapter sanPhamAdapter;
//    GIO HANG
    public static ArrayList<GioHangModel> arrGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        if(KiemTraKetNoi.haveNetworkConnection(getApplicationContext())){
            actionBar();
            actionViewFlipper();
            GetDataTenCL();
            GetDaTaSPMoiVe();
//            bắt sự kiện cho Tên chủng loại
            CatchOnItemTenCL();
        }
        else {
            KiemTraKetNoi.ShowToast_KTKN(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }

    }

    private void CatchOnItemTenCL() {
        listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                bắt theo từng dòng (truyền vào position i)
                switch (i){
                    case 0:
//                  kiểm tra kết nối
                        if(KiemTraKetNoi.haveNetworkConnection((getApplicationContext()))){
                            Intent intent = new Intent(MainActivity.this, AdidasActivity.class);
                            intent.putExtra("idTenCL",arrProductCategory.get(i).getIdChungLoai());
                            startActivity(intent);
                        }
                        else {
                            KiemTraKetNoi.ShowToast_KTKN(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối!!!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDaTaSPMoiVe() {
//        Thực hiện các phương thức gửi lên cho server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlSPMoiVe, new Response.Listener<JSONArray>() {
//            nhận dữ liệu trả về từ server khi request hoàn thành
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int idCL = 0;
                    int idSP = 0;
                    String tenSP = "";
                    int giaSP = 0;
                    String urlHinh = "";
                    for(int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idCL = jsonObject.getInt("id_CL");
                            idSP = jsonObject.getInt("idSP");
                            tenSP = jsonObject.getString("TenSP");
                            giaSP = jsonObject.getInt("Gia");
//                            urlHinh = jsonObject.getString("UrlHinh");
                            urlHinh = "http://" + Server.localhost + jsonObject.getString("UrlHinh");
                            arrSanPham.add(new SanPham(urlHinh,tenSP,giaSP,idSP, idCL));
                            sanPhamAdapter.notifyDataSetChanged();
//                            Toast.makeText(MainActivity.this, urlHinh,Toast.LENGTH_LONG).show();
                        }
                        catch (JSONException e){
                            Log.d("ERROR SP MOI VE : ", e.toString());
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

    private void GetDataTenCL(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlProductCategory, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for(int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idCL = jsonObject.getInt("idCL");
                            tenCL = jsonObject.getString("TenCL");
                            arrProductCategory.add(new ProductCategory(idCL, tenCL));
                            categoryAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.d("ERROR CL : ", e.toString());
                            e.printStackTrace();
                        }
//                        Toast.makeText(MainActivity.this, tenCL ,Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(MainActivity.this, String.valueOf(response.length()),Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
                Log.d("ERROR Volley : ", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest); //add request muốn gửi lên cho Server
    }

    private void setControl(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        listViewMain  = (ListView) findViewById(R.id.listView_main);

//        DANH MỤC
        arrProductCategory = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(arrProductCategory, getApplicationContext());
//        gán Adapter vào ListView
        listViewMain.setAdapter(categoryAdapter);

//        SẢN PHẨM MỚI VỀ
        arrSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(arrSanPham, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanPhamAdapter);
//        kiểm tra mảng giỏ hàng đã có dữ liệu hay chưa để cấp phát vùng bộ nhớ
        if(arrGioHang != null){

        }else {
            arrGioHang = new ArrayList<>();
        }
    }

    private void actionViewFlipper(){
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://www.elleman.vn/wp-content/uploads/2018/09/22/logo-thuong-hieu-converse-elle-man.jpg");
        mangquangcao.add("http://dsconcerts.com/wp-content/uploads/nike-logos-nike-logo-nike-logo-design-icons-vector-free-download.jpg");
        mangquangcao.add("https://www.muraldecal.com/en/img/asfs980-jpg/folder/products-listado-merchant/stickers-vans-logo.jpg");
        for(int i = 0; i < mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
//          load hình ảnh từ url về
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
//        bắt sự kiện cho viewFlipper tự chạy
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
    }

    private void actionBar(){
//        hỗ trợ toolbar như actionBar
        setSupportActionBar(toolbar);
//        set nút Home toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_navbar);
//        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
//        MenuItem item = menu.findItem(R.id.menu_Search);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_GioHang:
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
//            case R.id.action_search:
//                intent = new Intent(getApplication(),Search_activity.class);
//                startActivity(intent);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        Log.d("Ketqua", newText);
//        return true;
//    }
}
