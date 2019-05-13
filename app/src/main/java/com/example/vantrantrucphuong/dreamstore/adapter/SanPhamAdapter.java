package com.example.vantrantrucphuong.dreamstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vantrantrucphuong.dreamstore.R;
import com.example.vantrantrucphuong.dreamstore.activity.ChiTietSanPham;
import com.example.vantrantrucphuong.dreamstore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Van Tran Truc Phuong on 3/19/2019.
 */

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arraySanPham;

    public SanPhamAdapter(ArrayList<SanPham> arrSanPham, Context context) {
        this.context = context;
        this.arraySanPham = arrSanPham;
    }

//    Định nghĩa các Item layout và khởi tạo Holder.
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_spmoive, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

//    Thiết lập các thuộc tính của view và dữ liệu
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        SanPham sanPham = arraySanPham.get(position);
        holder.txtTenSP.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText("Giá: " + decimalFormat.format(sanPham.getGiaSP()) + " VNĐ");
        Picasso.with(context).load(sanPham.getUrlHinh()).error(R.drawable.cart_1).placeholder(R.drawable.no_image).into(holder.imgSP);
    }

    @Override
    public int getItemCount() {
        return arraySanPham.size();
    }

//    Holder anhxa()
    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgSP;
        public TextView txtTenSP, txtGiaSP;

        public ItemHolder(View itemView) {
            super(itemView);
            txtTenSP = (TextView) itemView.findViewById(R.id.txtViewTenSP);
            txtGiaSP = (TextView) itemView.findViewById(R.id.txtViewGiaSP);
            imgSP = (ImageView) itemView.findViewById(R.id.imgViewSP);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("chitietsanpham",arraySanPham.get(getPosition()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
