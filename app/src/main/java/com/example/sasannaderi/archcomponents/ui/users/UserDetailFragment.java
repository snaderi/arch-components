package com.example.sasannaderi.archcomponents.ui.users;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasannaderi.archcomponents.R;
import com.example.sasannaderi.archcomponents.models.User;
import com.example.sasannaderi.archcomponents.models.repositories.Resource;
import com.example.sasannaderi.archcomponents.ui.base.LifecycleFragment;
import com.example.sasannaderi.archcomponents.viewmodels.UserDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends LifecycleFragment {

    private static final String ARG_USER_ID = "param1";

    private int mUserId;

    private TextView mUsernameTextView;
    private TextView mEmailTextView;
    private TextView mAddressTextView;
    private TextView mPhoneTextView;
    private TextView mWebsiteTextView;
    private TextView mCompanyTextView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId User ID
     * @return A new instance of fragment UserDetailFragment.
     */
    public static UserDetailFragment newInstance(int userId) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    public UserDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_USER_ID);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bindData(mUserId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mUsernameTextView = (TextView) view.findViewById(R.id.username);
        mEmailTextView = (TextView) view.findViewById(R.id.email);
        mAddressTextView = (TextView) view.findViewById(R.id.address);
        mPhoneTextView = (TextView) view.findViewById(R.id.phone);
        mWebsiteTextView = (TextView) view.findViewById(R.id.website);
        mCompanyTextView = (TextView) view.findViewById(R.id.company);

        return view;
    }

    private void bindData(int userId) {
        UserDetailViewModel model = ViewModelProviders.of(this).get(UserDetailViewModel.class);
        model.getUser(userId).observe(this, new Observer<Resource<User>>() {

            @Override
            public void onChanged(@Nullable Resource<User> user) {
                switch (user.status) {
                    case Resource.Status.SUCCESS:
                        getActivity().setTitle(user.data.name);
                        updateUserData(user.data);
                        break;
                    case Resource.Status.LOADING:
                        break;
                    default:
                        Toast.makeText(getActivity(), user.message, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void updateUserData(User user) {
        mUsernameTextView.setText(user.username);
        mEmailTextView.setText(user.email);
        mAddressTextView.setText(user.address.toString());
        mPhoneTextView.setText(user.phone);
        mWebsiteTextView.setText(user.website);
        mCompanyTextView.setText(user.company.toString());
    }

}
