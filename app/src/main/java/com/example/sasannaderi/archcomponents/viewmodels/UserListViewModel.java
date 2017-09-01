package com.example.sasannaderi.archcomponents.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sasannaderi.archcomponents.models.User;
import com.example.sasannaderi.archcomponents.models.repositories.Resource;
import com.example.sasannaderi.archcomponents.models.repositories.UserRepository;

import java.util.List;

/**
 * Created by sasannaderi on 2017-06-19.
 */

public class UserListViewModel extends ViewModel {

    private LiveData<Resource<List<User>>> mUsers;
    private UserRepository mRepository;

    public UserListViewModel() {
        mRepository = new UserRepository();
    }

    public LiveData<Resource<List<User>>> getUsers(boolean refresh) {
        if (mUsers == null || refresh) {
            return mUsers = mRepository.getAll();
        } else {
            return mUsers;
        }

    }

}
