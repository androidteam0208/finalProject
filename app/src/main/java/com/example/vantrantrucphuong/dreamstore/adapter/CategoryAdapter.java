package com.example.vantrantrucphuong.dreamstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vantrantrucphuong.dreamstore.R;
import com.example.vantrantrucphuong.dreamstore.model.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Van Tran Truc Phuong on 3/10/2019.
 */

public class CategoryAdapter extends BaseAdapter {
    ArrayList<ProductCategory> arrCategory;
    //    truyền vào màn hình
    Context context;

    public CategoryAdapter(ArrayList<ProductCategory> arrCategory, Context context) {
        this.context = context;
        this.arrCategory = arrCategory;
    }

    @Override
    public int getCount() {
        return arrCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return arrCategory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

//    hỗ trợ việc load dữ liệu nhanh hơn

    public class ViewHolder {
        TextView txtTenCL;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
//        view chưa tồn tại
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            view = layoutInflater.inflate(R.layout.dong_listview_category, null);
//            setControl()
            viewHolder.txtTenCL = (TextView) view.findViewById(R.id.txtViewCategory);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ProductCategory productCategory = (ProductCategory) getItem(i);
        viewHolder.txtTenCL.setText(productCategory.getTenChungLoai());

        return view;
    }
}
