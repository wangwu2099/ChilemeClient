package com.wangqing.chilemecilent.view.foodrec;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wangqing.chilemecilent.R;
import com.wangqing.chilemecilent.databinding.FragmentFoodRecDetailBinding;
import com.wangqing.chilemecilent.object.dto.FRDSelDto;
import com.wangqing.chilemecilent.object.dto.FoodRecDetailDto;
import com.wangqing.chilemecilent.utils.AppConfig;
import com.wangqing.chilemecilent.utils.RelativeDateFormat;
import com.wangqing.chilemecilent.viewmodel.foodRec.FoodRecDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodRecDetailFragment extends Fragment implements View.OnClickListener{

    private FragmentFoodRecDetailBinding binding;

    private FoodRecDetailViewModel viewModel;

    public FoodRecDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_rec_detail, container, false);
        viewModel = new ViewModelProvider(this).get(FoodRecDetailViewModel.class);

        FRDSelDto frdSelDto = (FRDSelDto) getArguments().getSerializable(AppConfig.FOOD_REC_BROWSER_TO_DETAIL_KEY);
        viewModel.setFrdSelDto(frdSelDto);

        binding.setData(viewModel);
        binding.setLifecycleOwner(requireActivity());


        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_food_rec_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    /**
     * 初始化界面
     */
    private void initView() {
        binding.buttonLike.init(requireActivity());
        binding.buttonLike.setOnClickListener(this);
        binding.buttonFavorite.init(requireActivity());
        binding.buttonFavorite.setOnClickListener(this);
        binding.buttonAttention.setOnClickListener(this);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getDetailByFRDSel();
            }
        });

        viewModel.getInfo().observe(getViewLifecycleOwner(), new Observer<FoodRecDetailDto>() {
            @Override
            public void onChanged(FoodRecDetailDto info) {
                // 头像
                Glide.with(FoodRecDetailFragment.this)
                        .load(AppConfig.BASE_URL + info.getUserAvatar())
                        .into(binding.userAvtar);

                // 配图
                if (TextUtils.isEmpty(info.getPostImageUrl())){
                    binding.postImage.setVisibility(View.GONE);
                }else {
                    Glide.with(FoodRecDetailFragment.this)
                            .load(AppConfig.BASE_URL + info.getPostImageUrl())
                            .into(binding.postImage);
                }

                // 日期
                binding.postTime.setText(RelativeDateFormat.format(info.getPostTime()));

                // 点赞状态
                if (info.isLikeStatus()){
                    binding.buttonLike.setChecked(true);
                }else {
                    binding.buttonLike.setChecked(false);
                }

                // 收藏状态
                if (info.isFavoriteStatus()){
                    binding.buttonFavorite.setChecked(true);
                }else {
                    binding.buttonFavorite.setChecked(false);
                }

                // 关注
                if (info.getUserId() == viewModel.getAccountManager().getUser().getUserId()){
                    binding.buttonAttention.setVisibility(View.GONE);
                }else{
                    if (info.isAttentionStatus()){
                        binding.buttonAttention.setText("已关注");
                    }else {
                        binding.buttonAttention.setText("关注");
                    }
                }
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    /**
     * 处理点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLike:
                buttonLikeHandler();
                break;
            case R.id.buttonFavorite:
                buttonFavoriteHandler();
                break;
            case R.id.buttonAttention:
                break;
        }
    }

    private void buttonFavoriteHandler() {
        FoodRecDetailDto info = viewModel.getInfo().getValue();
        if (binding.buttonFavorite.isChecked()){
            info.setFavoriteNumber(info.getFavoriteNumber() + 1);
            info.setFavoriteStatus(true);
        }else {
            info.setFavoriteNumber(info.getFavoriteNumber() - 1);
            info.setFavoriteStatus(false);
        }
        viewModel.giveAFavorite();
        viewModel.getInfo().setValue(info);
    }

    private void buttonLikeHandler() {
        FoodRecDetailDto info = viewModel.getInfo().getValue();
        if (binding.buttonLike.isChecked()){
            info.setLikeNumber(info.getLikeNumber() + 1);
            info.setLikeStatus(true);
        }else {
            info.setLikeNumber(info.getLikeNumber() - 1);
            info.setLikeStatus(false);
        }
        viewModel.giveALike(true);
        viewModel.getInfo().setValue(info);
    }


}
