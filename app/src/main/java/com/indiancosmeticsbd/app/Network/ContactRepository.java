package com.indiancosmeticsbd.app.Network;

import androidx.lifecycle.MutableLiveData;

import com.indiancosmeticsbd.app.Model.ContactInfo.ContactInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactRepository {
    private static ContactRepository contactRepository;
    private ContactInfoApi contactInfoApi;


    public static ContactRepository getInstance(){
        if (contactRepository == null){
            contactRepository = new ContactRepository();
        }
        return contactRepository;
    }

    public ContactRepository(){
        contactInfoApi = RetrofitService.createService(ContactInfoApi.class);
    }

    public MutableLiveData<ContactInfo> getContactInfo(String api_token, String determiner){
        MutableLiveData<ContactInfo> contactInfoLiveData = new MutableLiveData<>();
        contactInfoApi.getContactInfo(api_token, determiner).enqueue(new Callback<ContactInfo>() {
            @Override
            public void onResponse(Call<ContactInfo> call, Response<ContactInfo> response) {
                if(response.isSuccessful()){
                    contactInfoLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ContactInfo> call, Throwable t) {
                contactInfoLiveData.setValue(null);
            }
        });
        return contactInfoLiveData;
    }

}
