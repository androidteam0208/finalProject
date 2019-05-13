package com.example.vantrantrucphuong.dreamstore.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vantrantrucphuong.dreamstore.R;
import com.example.vantrantrucphuong.dreamstore.adapter.GioHangAdapter;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {

    Toolbar toolbar_GioHang;
    TextView txtView_ThongBao;
    static TextView txtView_DonGia;
    ListView listView_GioHang;
    Button btn_ThanhToan, btn_TiepTucMuaHang;

//    Adapter
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        setControl();
        actionBar();
        kiemTraDuLieu();
        setEvent();
        tinhTien();
    }

    private void setEvent() {
        btn_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThongTinKhachHang.class);
                startActivity(intent);
            }
        });
        btn_TiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

//        listView_GioHang.setItemsCanFocus(false);
        listView_GioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
//                Toast.makeText(GioHang.this, "CLICK", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa sản phẩm này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.arrGioHang.size() <=0){
                            txtView_ThongBao.setVisibility(View.VISIBLE);
                        }
                        else{
                            MainActivity.arrGioHang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            tinhTien();
                            if(MainActivity.arrGioHang.size() <=0){
                                txtView_ThongBao.setVisibility(View.VISIBLE);
                            }
                            else {
                                txtView_ThongBao.setVisibility(View.INVISIBLE);
                                tinhTien();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gioHangAdapter.notifyDataSetChanged();
                        tinhTien();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void kiemTraDuLieu() {
        if(MainActivity.arrGioHang.size() <=0 ){
            gioHangAdapter.notifyDataSetChanged();
            txtView_ThongBao.setVisibility(View.VISIBLE);
            listView_GioHang.setVisibility(View.INVISIBLE);
        }
        else{
            gioHangAdapter.notifyDataSetChanged();
            txtView_ThongBao.setVisibility(View.INVISIBLE);
            listView_GioHang.setVisibility(View.VISIBLE);
        }

    }

    public static void tinhTien(){
        int tongTien = 0;
        for(int i = 0; i < MainActivity.arrGioHang.size(); i++){
            tongTien += MainActivity.arrGioHang.get(i).getGiaSP();
        }
//        Toast.makeText(this, String.valueOf(tongTien), Toast.LENGTH_SHORT).show();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtView_DonGia.setText(decimalFormat.format(tongTien) + " VNĐ");
    }
    private void setControl() {
        toolbar_GioHang = (Toolbar) findViewById(R.id.toolbar_GioHang);
        txtView_ThongBao = (TextView) findViewById(R.id.txtView_ThongBao);
        txtView_DonGia = (TextView) findViewById(R.id.txtView_DonGia);
        listView_GioHang = (ListView) findViewById(R.id.listView_GioHang);
        btn_ThanhToan = (Button) findViewById(R.id.btn_ThanhToan);
        btn_TiepTucMuaHang = (Button) findViewById(R.id.btn_TiepTucMuaHang);
        gioHangAdapter = new GioHangAdapter(GioHang.this, MainActivity.arrGioHang);
        listView_GioHang.setAdapter(gioHangAdapter);
}

    private void actionBar() {
        //        hỗ trợ toolbar như actionBar
        setSupportActionBar(toolbar_GioHang);
//        set nút Home toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_GioHang.setNavigationIcon(R.drawable.ic_action_back);
//        bắt sự kiện click vào
        toolbar_GioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
