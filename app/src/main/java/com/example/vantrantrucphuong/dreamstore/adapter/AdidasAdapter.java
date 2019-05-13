package com.example.vantrantrucphuong.dreamstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vantrantrucphuong.dreamstore.R;
import com.example.vantrantrucphuong.dreamstore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Van Tran Truc Phuong on 4/3/2019.
 */

public class AdidasAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<SanPham> arrayAdidas;
    ValueFilter valueFilter;
    ArrayList<SanPham> filterList;

    public AdidasAdapter(Context context, ArrayList<SanPham> arrayAdidas) {
        super();
        this.context = context;
        this.arrayAdidas = arrayAdidas;
        this.filterList = arrayAdidas;
    }

    @Override
    public int getCount() {
        return arrayAdidas.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayAdidas.get(i);
    }

    @Override
    public long getItemId(int position) {

        int itemID;

        // orig will be null only if we haven't filtered yet:
        if (filterList == null)
        {
            itemID = position;
        }
        else
        {
            itemID = filterList.indexOf(arrayAdidas.get(position));
        }
        return itemID;
    }
    public class ViewHolder {
        TextView txtViewTenSP, txtViewGiaSP;
        ImageView imgViewSP;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
//        view chưa tồn tại
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
//            Gán Layout vào View
            view = layoutInflater.inflate(R.layout.dong_listview_adidas, null);
//            setControl()
            viewHolder.txtViewTenSP = (TextView) view.findViewById(R.id.txtViewTen_Adidas);
            viewHolder.txtViewGiaSP = (TextView) view.findViewById(R.id.txtViewGia_Adidas);
            viewHolder.imgViewSP = (ImageView) view.findViewById(R.id.imgView_Adidas);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham ;
        sanPham = arrayAdidas.get(i);
        viewHolder.txtViewTenSP.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtViewGiaSP.setText("Giá: " + decimalFormat.format(sanPham.getGiaSP()) + " VNĐ");
        Picasso.with(context).load(sanPham.getUrlHinh()).placeholder(R.drawable.no_image).into(viewHolder.imgViewSP);
        return view;
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

    class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<SanPham> filters = new ArrayList<SanPham>();
                constraint = constraint.toString().toUpperCase();
                for (int i = 0; i < filterList.size(); i++) {
                    if ((filterList.get(i).getTenSP().toUpperCase()).contains(constraint))
                    {
                        SanPham sp = new SanPham(filterList.get(i).getUrlHinh(),filterList.get(i).getTenSP(),filterList.get(i).getGiaSP(),filterList.get(i).getIdSP(),filterList.get(i).getIdCL());
                        filters.add(sp);
                    }
                }
//                results.count = filters.size();
                results.values = filters;
            } else {
//                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            arrayAdidas = (ArrayList<SanPham>) results.values;
            notifyDataSetChanged();
        }
    }
}
