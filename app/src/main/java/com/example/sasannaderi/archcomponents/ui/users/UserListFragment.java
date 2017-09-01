package com.example.sasannaderi.archcomponents.ui.users;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasannaderi.archcomponents.R;
import com.example.sasannaderi.archcomponents.models.User;
import com.example.sasannaderi.archcomponents.models.repositories.Resource;
import com.example.sasannaderi.archcomponents.ui.base.LifecycleFragment;
import com.example.sasannaderi.archcomponents.viewmodels.UserListViewModel;

import java.util.List;

public class UserListFragment extends LifecycleFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mUserList;
    private UserAdapter mUserAdapter;

    private OnUserListFragmentListener mListener;

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resource_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindData(true);
            }
        });

        mUserList = (RecyclerView) view.findViewById(R.id.resources);
        mUserList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bindData(savedInstanceState != null);
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(getString(R.string.app_name));
    }

    private void bindData(boolean refresh) {
        UserListViewModel model = ViewModelProviders.of(this).get(UserListViewModel.class);
        model.getUsers(refresh).observe(this, new Observer<Resource<List<User>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<User>> users) {
                switch (users.status) {
                    case Resource.Status.SUCCESS:
                        mSwipeRefreshLayout.setRefreshing(false);

                        mUserAdapter = new UserAdapter(getActivity(), users.data, mClickListener);
                        mUserList.setAdapter(mUserAdapter);
                        break;
                    case Resource.Status.LOADING:
                        mSwipeRefreshLayout.setRefreshing(true);
                        break;
                    default:
                        mSwipeRefreshLayout.setRefreshing(false);

                        Toast.makeText(getActivity(), users.message, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private final UserAdapter.OnUserClickListener mClickListener = new UserAdapter.OnUserClickListener() {
        @Override
        public void onUserClick(int userId) {
            if (mListener != null) {
                mListener.onUserClicked(userId);
            }
        }
    };

    private static class UserAdapter extends RecyclerView.Adapter<UserAdapter.ResourceViewHolder> {

        public interface OnUserClickListener {
            void onUserClick(int userId);
        }

        static class ResourceViewHolder extends RecyclerView.ViewHolder {

            public ImageView image;
            public TextView name;

            public ResourceViewHolder(View itemView) {
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.name);
                image = (ImageView) itemView.findViewById(R.id.image);
            }

            public void bind(final Context context, final User user, final OnUserClickListener listener) {
//                Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(image);
                name.setText(user.name);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onUserClick(user.id);
                        }
                    }
                });
            }
        }

        private final Context mContext;
        private final List<User> mUsers;
        private final OnUserClickListener mListener;

        public UserAdapter(Context context, List<User> users, OnUserClickListener listener) {
            mContext = context;
            mUsers = users;
            mListener = listener;
        }

        @Override
        public UserAdapter.ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_resource, parent, false);
            ResourceViewHolder holder = new ResourceViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ResourceViewHolder holder, int position) {
            User user = mUsers.get(position);

            holder.bind(mContext, user, mListener);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserListFragmentListener) {
            mListener = (OnUserListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnUserListFragmentListener {
        void onUserClicked(int userId);
    }
}
