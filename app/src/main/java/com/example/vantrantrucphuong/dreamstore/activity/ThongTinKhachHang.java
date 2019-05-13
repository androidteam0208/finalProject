package com.example.vantrantrucphuong.dreamstore.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vantrantrucphuong.dreamstore.R;
import com.example.vantrantrucphuong.dreamstore.util.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThongTinKhachHang extends AppCompatActivity {

    Toolbar toolbar_TTKH;
    EditText edt_Ten, edt_SDT, edt_Email, edt_DiaChi;
    Button btn_XacNhan, btn_TiepTucMuaHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        setControl();
        actionBar();
//      Kiểm tra kết nối
        setEvent();
    }

    private void setEvent() {
//        btn_TiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        btn_XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten = edt_Ten.getText().toString().trim();
                final String email = edt_Email.getText().toString().trim();
                final String sdt = edt_SDT.getText().toString().trim();
                final String diachi = edt_DiaChi.getText().toString().trim();

                if (sdt.length() > 0 && ten.length() > 0 && diachi.length() > 0 && email.length() > 0) {
                    if (checkEmail(email)) {
                        int i;
                        for ( i = 0; i < MainActivity.arrGioHang.size(); i++) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            final int finalI = i;
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlThemDonHang, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                if(error!=null && error.getMessage() !=null){
                                    System.out.println("volley Error ................."+ error.getMessage());
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Xác nhận thành công!!!",Toast.LENGTH_LONG).show();
                                }
                            }
                        }) {
                            //                        POST data
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
//                            chuyển dữ liệu sang Json array (hashmap)
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("TenKH", ten);
                                hashMap.put("SDT", sdt);
                                hashMap.put("Email", email);
                                hashMap.put("DiaChi", sdt);
//                                JSONArray jsonArray = new JSONArray();
//                                for (int i = 0; i < MainActivity.arrGioHang.size(); i++) {
                                    hashMap.put("idDH", "");
                                    hashMap.put("id_SP", String.valueOf(MainActivity.arrGioHang.get(finalI).getIdSP()));
                                    hashMap.put("MoTa", String.valueOf(MainActivity.arrGioHang.get(finalI).getSize()));
                                    hashMap.put("SoLuong", String.valueOf(MainActivity.arrGioHang.get(finalI).getSoLuong()));
                                    hashMap.put("ThanhTien", String.valueOf(MainActivity.arrGioHang.get(finalI).getGiaSP()));
                                    hashMap.put("ThoiGianDat", "");
                                    hashMap.put("ThoiGianGiao", "");
//                                    JSONObject jsonObject = new JSONObject();
//                                    try {
////                                        jsonObject.put("madonhang")
//                                        jsonObject.put("id_SP", MainActivity.arrGioHang.get(i).getIdSP());
//                                        jsonObject.put("MoTa", MainActivity.arrGioHang.get(i).getSize());
//                                        jsonObject.put("SoLuong", MainActivity.arrGioHang.get(i).getSoLuong());
//                                        jsonObject.put("ThanhTien", MainActivity.arrGioHang.get(i).getGiaSP());
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    jsonArray.put(jsonObject);
//                                }

//                                hashMap.put("json", jsonArray.toString());
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }}
                    else {
                        Toast.makeText(ThongTinKhachHang.this, "Hãy kiểm tra lại Email!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Check connection show Toast
                    Toast.makeText(ThongTinKhachHang.this, "Hãy kiểm tra lại dữ liệu!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setControl() {
        btn_XacNhan = (Button) findViewById(R.id.btn_XacNhan);
        btn_TiepTucMuaHang = (Button) findViewById(R.id.btn_TiepTucMuaHang);
        edt_Ten = (EditText) findViewById(R.id.edt_Ten);
        edt_SDT = (EditText) findViewById(R.id.edt_SDT);
        edt_Email = (EditText) findViewById(R.id.edt_Email);
        edt_DiaChi = (EditText) findViewById(R.id.edt_DiaChi);
        toolbar_TTKH = (Toolbar) findViewById(R.id.toolbar_TTKH);
    }

    private void actionBar() {
        //        hỗ trợ toolbar như actionBar
        setSupportActionBar(toolbar_TTKH);
//        set nút Home toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_TTKH.setNavigationIcon(R.drawable.ic_action_back);
//        bắt sự kiện click vào
        toolbar_TTKH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public static boolean checkEmail(String email) {
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern regex = Pattern.compile(emailPattern);
        Matcher matcher = regex.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
           // Log.i("Finished sending email...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ThongTinKhachHang.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
