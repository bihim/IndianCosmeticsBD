package com.indiancosmeticsbd.app.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indiancosmeticsbd.app.Model.Orders.OrderStatusModel;
import com.indiancosmeticsbd.app.Network.Order.OrderStatusRepository;

public class OrderStatusViewModel extends ViewModel {
    private MutableLiveData<OrderStatusModel> mutableLiveData;

    public void init(String orderId){
        if (mutableLiveData !=null){
            return;
        }
        OrderStatusRepository orderStatusRepository = OrderStatusRepository.getInstance();
        mutableLiveData = orderStatusRepository.getOrderInfo(orderId);
    }

    public LiveData<OrderStatusModel> getOrderStatus(){
        return mutableLiveData;
    }
}
