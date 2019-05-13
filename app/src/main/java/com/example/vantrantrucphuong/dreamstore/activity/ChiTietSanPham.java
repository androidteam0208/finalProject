package com.example.vantrantrucphuong.dreamstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vantrantrucphuong.dreamstore.R;
import com.example.vantrantrucphuong.dreamstore.model.GioHangModel;
import com.example.vantrantrucphuong.dreamstore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbar_CTSP;
    ImageView imgView_CTSP;
    TextView txtViewTenSP_CTSP, txtViewGia_CTSP;
    Spinner spinner_SoLuong;
    Button btn_ThemVaoGioHang;
//    Sử dụng lại
    int id = 0;
    String tenCTSP = "";
    int giaCTSP = 0;
    String hinhCTSP = "";
    int idSP = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        setControl();
        actionBar();
        GetData_CTSP();
        //        chứa sự kiện click mua ngay
        setEvent();
    }

    private void setEvent() {
        btn_ThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.arrGioHang.size() > 0){
                    int soLuong = Integer.parseInt(spinner_SoLuong.getSelectedItem().toString());
//                      nếu đã có dữ liệu rồi
                    boolean checkExist = false;
                    for(int i = 0; i < MainActivity.arrGioHang.size(); i++){
                        if(MainActivity.arrGioHang.get(i).getIdSP() == idSP){
//                            MainActivity.arrGioHang.get(i).setTenSP(tenCTSP );
//                            MainActivity.arrGioHang.get(i).setTenSP(String.valueOf(giaCTSP));
                            MainActivity.arrGioHang.get(i).setSoLuong(MainActivity.arrGioHang.get(i).getSoLuong());
                            if(MainActivity.arrGioHang.get(i).getSoLuong() >= 10 ){
                                MainActivity.arrGioHang.get(i).setSoLuong(10);
                            }
                            MainActivity.arrGioHang.get(i).setGiaSP(giaCTSP*MainActivity.arrGioHang.get(i).getSoLuong());
//                            checkExist = true;
                        }
                    }
                    if(checkExist == false){
                        int giaMoi = soLuong * giaCTSP;
                        MainActivity.arrGioHang.add(new GioHangModel(idSP, tenCTSP, giaMoi, hinhCTSP, soLuong, 0));
                    }
                }
                else{
//                      dữ liệu mới
                    int soLuong = Integer.parseInt(spinner_SoLuong.getSelectedItem().toString());
                    int giaMoi = soLuong * giaCTSP;
                    MainActivity.arrGioHang.add(new GioHangModel(idSP, tenCTSP, giaMoi, hinhCTSP, soLuong, 0));
                }

                Intent intent = new Intent(view.getContext(), GioHang.class);
                startActivity(intent);
//                Toast.makeText(ChiTietSanPham.this, "OK", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetData_CTSP() {
//        đã truyền dữ liệu qua dưới dang Object => nhận dạng Object
//        int mPostId = getIntent().getIntExtra("chitietsanpham", 0);
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("chitietsanpham");
//        Toast.makeText(this, String.valueOf(sanPham.getIdSP()), Toast.LENGTH_SHORT).show();
        id = sanPham.getIdCL();
        tenCTSP = sanPham.getTenSP();
        giaCTSP = sanPham.getGiaSP();
        hinhCTSP = sanPham.getUrlHinh();
        idSP = sanPham.getIdSP();
        txtViewTenSP_CTSP.setText(tenCTSP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtViewGia_CTSP.setText("Giá: " + decimalFormat.format(giaCTSP) + " VNĐ");
        Picasso.with(getApplicationContext()).load(sanPham.getUrlHinh()).placeholder(R.drawable.no_image).into(imgView_CTSP);
    }

    private void actionBar() {
        //        hỗ trợ toolbar như actionBar
        setSupportActionBar(toolbar_CTSP);
//        set nút Home toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_CTSP.setNavigationIcon(R.drawable.ic_action_back);
//        bắt sự kiện click vào
        toolbar_CTSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setControl() {
        toolbar_CTSP = (Toolbar) findViewById(R.id.toolbar_CTSP);
        imgView_CTSP = (ImageView) findViewById(R.id.imgView_CTSP);
        txtViewTenSP_CTSP = (TextView) findViewById(R.id.txtViewTenSP_CTSP);
        txtViewGia_CTSP = (TextView) findViewById(R.id.txtViewGia_CTSP);
        spinner_SoLuong = (Spinner) findViewById(R.id.spinner_SoLuong);
        btn_ThemVaoGioHang = (Button) findViewById(R.id.btn_ThemVaoGioHang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_GioHang:
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
