package com.example.imb.uzbekistanhotels.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.imb.uzbekistanhotels.R;
import com.example.imb.uzbekistanhotels.async.ClientDeleteOrderSide;
import com.example.imb.uzbekistanhotels.room.Order;
import com.example.imb.uzbekistanhotels.room.OrderDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.ViewHolder> {
    private List<Order> ordersData;
    private OrderDatabase db = OrderDatabase.getDb();

    public OrdersListAdapter(List<Order> ordersData) {
        this.ordersData = ordersData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.hName.setText(ordersData.get(position).getHotelName());
        Glide.with(holder.itemView).load(R.drawable.order).into(holder.hImage);
        holder.adultsNum.setText(String.valueOf(ordersData.get(position).getAdultsNum()));
        holder.childNum.setText(String.valueOf(ordersData.get(position).getChildNum()));
        holder.price.setText("$" + ordersData.get(position).getTotalCost());
        holder.roomNum.setText(String.valueOf(ordersData.get(position).getRoomNum()));
        holder.rType.setText(ordersData.get(position).getType());
        holder.delete.setOnClickListener(v -> {
            Order order = db.orderDaoAccess().getOrder(ordersData.get(position).getId());
            ClientDeleteOrderSide client = new ClientDeleteOrderSide();
            client.execute(""+ordersData.get(position).getId());
            db.orderDaoAccess().deleteOrder(order);
        });
    }
    @Override
    public int getItemCount() {
        return ordersData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hName)
        TextView hName;
        @BindView(R.id.hImage)
        ImageView hImage;
        @BindView(R.id.adultsNum)
        TextView adultsNum;
        @BindView(R.id.childNum)
        TextView childNum;
        @BindView(R.id.roomNum)
        TextView roomNum;
        @BindView(R.id.rType)
        TextView rType;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.delete)
        Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
