package com.indiancosmeticsbd.app.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indiancosmeticsbd.app.Model.ContactInfo.ContactInfo;
import com.indiancosmeticsbd.app.Network.ContactRepository;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CONTACT_INFO;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public class ContactInfoViewModel extends ViewModel
{
    private MutableLiveData<ContactInfo> mutableLiveData;
    private ContactRepository contactRepository;

    public void init(){
        if (mutableLiveData!=null){
            return;
        }

        contactRepository = ContactRepository.getInstance();
        mutableLiveData = contactRepository.getContactInfo(API_TOKEN, CONTACT_INFO);
    }

    public LiveData<ContactInfo> getContactInfo(){
        return mutableLiveData;
    }
}
