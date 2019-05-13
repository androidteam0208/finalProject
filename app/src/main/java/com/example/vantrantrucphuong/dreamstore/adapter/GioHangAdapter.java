package com.example.vantrantrucphuong.dreamstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vantrantrucphuong.dreamstore.R;
import com.example.vantrantrucphuong.dreamstore.activity.GioHang;
import com.example.vantrantrucphuong.dreamstore.activity.MainActivity;
import com.example.vantrantrucphuong.dreamstore.model.GioHangModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.vantrantrucphuong.dreamstore.R.id.btn_SoLuong;
import static com.example.vantrantrucphuong.dreamstore.R.id.txtView_DonGia;

/**
 * Created by Van Tran Truc Phuong on 4/10/2019.
 */

public class GioHangAdapter extends BaseAdapter{
    Context context;
    ArrayList<GioHangModel> arrayGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHangModel> arrGioHang) {
        this.context = context;
        this.arrayGioHang = arrGioHang;
    }

    @Override
    public int getCount() {
        return arrayGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{
        TextView txtViewTenSP_GioHang, txtViewGiaSP_GioHang, txtView_DonGia;
        Spinner spinner_Size;
        Button btn_Giam, btn_SoLuong, btn_Tang;
        ImageView imgView_GioHang;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
//        view chưa tồn tại
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            view = layoutInflater.inflate(R.layout.dong_spgiohang, null);
//            setControl()
            viewHolder.txtViewTenSP_GioHang = (TextView) view.findViewById(R.id.txtViewTenSP_GioHang);
            viewHolder.txtViewGiaSP_GioHang = (TextView) view.findViewById(R.id.txtViewGiaSP_GioHang);
            viewHolder.txtView_DonGia= (TextView) view.findViewById(txtView_DonGia);
            viewHolder.spinner_Size = (Spinner) view.findViewById(R.id.spinner_Size);
            viewHolder.btn_Giam = (Button) view.findViewById(R.id.btn_Giam);
            viewHolder.btn_SoLuong = (Button) view.findViewById(btn_SoLuong);
            viewHolder.btn_Tang = (Button) view.findViewById(R.id.btn_Tang);
            viewHolder.imgView_GioHang = (ImageView) view.findViewById(R.id.imgView_GioHang);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GioHangModel gioHang = (GioHangModel) getItem(i);
        viewHolder.txtViewTenSP_GioHang.setText(gioHang.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtViewGiaSP_GioHang.setText("Giá: " + decimalFormat.format(gioHang.getGiaSP()) + " VNĐ");
        Picasso.with(context).load(gioHang.getHinhSP()).error(R.drawable.cart_1).placeholder(R.drawable.no_image).into(viewHolder.imgView_GioHang);
//        Picasso.with(context).load(gioHang.getHinhSP()).placeholder(R.drawable.no_image).into(viewHolder.imgView_GioHang);
        viewHolder.btn_SoLuong.setText(gioHang.getSoLuong() + "");

//        int size = Integer.parseInt(viewHolder.spinner_Size.getSelectedItem().toString());
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.spinner_Size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0) MainActivity.arrGioHang.get(i).setSize(36);
                if(position == 1) MainActivity.arrGioHang.get(i).setSize(37);
                if(position == 2) MainActivity.arrGioHang.get(i).setSize(38);
                if(position == 3) MainActivity.arrGioHang.get(i).setSize(39);
                if(position == 4) MainActivity.arrGioHang.get(i).setSize(40);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        Toast.makeText(context,String.valueOf(MainActivity.arrGioHang.get(i).getSize()), Toast.LENGTH_SHORT).show();
        int sl = Integer.parseInt(viewHolder.btn_SoLuong.getText().toString());
        if(sl  >= 10){
            viewHolder.btn_Tang.setVisibility(View.INVISIBLE);
            viewHolder.btn_Giam.setVisibility(View.VISIBLE);
        }
        else if(sl <= 1){
            viewHolder.btn_Giam.setVisibility(View.INVISIBLE);
        }
        else if(sl >=1 ){
            viewHolder.btn_Tang.setVisibility(View.VISIBLE);
            viewHolder.btn_Giam.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btn_Tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int soLuongMoi =
                int soLuongMoi = Integer.parseInt(finalViewHolder.btn_SoLuong.getText().toString()) + 1;
                int giaSP = MainActivity.arrGioHang.get(i).getGiaSP();
                int soLuongHT = MainActivity.arrGioHang.get(i).getSoLuong();
                MainActivity.arrGioHang.get(i).setSoLuong(soLuongMoi);
                int thanhTien = (soLuongMoi * giaSP)/soLuongHT;
                MainActivity.arrGioHang.get(i).setGiaSP(thanhTien);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtViewGiaSP_GioHang.setText("Giá: " + String.valueOf(decimalFormat.format(thanhTien)) + " VNĐ");
                GioHang.tinhTien();
                if(soLuongMoi  >= 9){
                    finalViewHolder.btn_Tang.setVisibility(View.INVISIBLE);
                    finalViewHolder.btn_Giam.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_SoLuong.setText(String.valueOf(soLuongMoi));
                }
                else {
                    finalViewHolder.btn_Tang.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_Giam.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_SoLuong.setText(String.valueOf(soLuongMoi));
                }
//                finalViewHolder.txtView_DonGia.setText(String.valueOf(decimalFormat.format(thanhTien)) + " VNĐ");
            }
        });
        viewHolder.btn_Giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int soLuongMoi =
                int soLuongMoi = Integer.parseInt(finalViewHolder.btn_SoLuong.getText().toString()) - 1;
                int giaSP = MainActivity.arrGioHang.get(i).getGiaSP();
                int soLuongHT = MainActivity.arrGioHang.get(i).getSoLuong();
                MainActivity.arrGioHang.get(i).setSoLuong(soLuongMoi);
                int thanhTien = (soLuongMoi * giaSP)/soLuongHT;
                MainActivity.arrGioHang.get(i).setGiaSP(thanhTien);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtViewGiaSP_GioHang.setText("Giá: " + String.valueOf(decimalFormat.format(thanhTien)) + " VNĐ");
                GioHang.tinhTien();
                if(soLuongMoi  < 2){
                    finalViewHolder.btn_Tang.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_Giam.setVisibility(View.INVISIBLE);
                    finalViewHolder.btn_SoLuong.setText(String.valueOf(soLuongMoi));
                }
                else {
                    finalViewHolder.btn_Tang.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_Giam.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_SoLuong.setText(String.valueOf(soLuongMoi));
                }
//                finalViewHolder.txtView_DonGia.setText(String.valueOf(decimalFormat.format(thanhTien)) + " VNĐ");
            }
        });
        return view;
    }
}
