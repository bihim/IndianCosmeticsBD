package com.indiancosmeticsbd.app.Network.Order;

import androidx.lifecycle.MutableLiveData;

import com.indiancosmeticsbd.app.Model.Orders.OrderStatusModel;
import com.indiancosmeticsbd.app.Network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.ORDER_INFO;

public class OrderStatusRepository {
    private static OrderStatusRepository orderStatusRepository;
    private final OrderInfoApi orderInfoApi;

    public static OrderStatusRepository getInstance(){
        if (orderStatusRepository == null){
            orderStatusRepository = new OrderStatusRepository();
        }
        return orderStatusRepository;
    }

    public OrderStatusRepository(){
        orderInfoApi = RetrofitService.createService(OrderInfoApi.class);
    }

    public MutableLiveData<OrderStatusModel> getOrderInfo(String orderId){
        MutableLiveData<OrderStatusModel> orderStatusModelMutableLiveData = new MutableLiveData<>();

        orderInfoApi.getCODInfo(API_TOKEN, ORDER_INFO, orderId).enqueue(new Callback<OrderStatusModel>() {
            @Override
            public void onResponse(Call<OrderStatusModel> call, Response<OrderStatusModel> response) {
                if (response.isSuccessful()){
                    OrderStatusModel orderStatusModel = response.body();
                    if (orderStatusModel != null){
                        if (orderStatusModel.getStatus().equals("SUCCESS")){
                            orderStatusModelMutableLiveData.setValue(response.body());
                        }
                        else{
                            orderStatusModelMutableLiveData.setValue(null);
                        }
                    }
                    else{
                        orderStatusModelMutableLiveData.setValue(null);
                    }
                }
                else {
                    orderStatusModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<OrderStatusModel> call, Throwable t) {
                orderStatusModelMutableLiveData.setValue(null);
            }
        });
        return orderStatusModelMutableLiveData;
    }
}
