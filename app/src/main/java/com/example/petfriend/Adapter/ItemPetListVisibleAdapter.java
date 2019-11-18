package com.example.petfriend.Adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfriend.Model.Pet;
import com.example.petfriend.R;

import java.util.ArrayList;

public class ItemPetListVisibleAdapter extends RecyclerView.Adapter<ItemPetListVisibleAdapter.ListVisibleViewHolder>{
    private Context context;
    private ArrayList<Pet> listMountain;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;



    public class ListVisibleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvElevation;
        private ImageView imgList,imgListVisible;
        private ConstraintLayout relativeLayout;

        public ListVisibleViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_visible_list);
            tvElevation = itemView.findViewById(R.id.tv_elevation_visible_list);
            imgList = itemView.findViewById(R.id.img_visible_list);
            imgListVisible = itemView.findViewById(R.id.imageView_img_on);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }

        // 이미지뷰를 클릭했을때 보이고 안보이고를 애니메이션을 추가해서 크기랑 시간까지 세팅 해주는 작업
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    imgListVisible.getLayoutParams().height = value;
                    imgListVisible.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    imgListVisible.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }

    public ItemPetListVisibleAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Pet> getListMountain() {
        return listMountain;
    }

    public void setListMountain(ArrayList<Pet> listMountain) {
        this.listMountain = listMountain;
    }


    @Override
    public ItemPetListVisibleAdapter.ListVisibleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_idea_pet_list_visible, parent, false);

        return new ItemPetListVisibleAdapter.ListVisibleViewHolder(itemList);
    }


    @Override
    public void onBindViewHolder(ItemPetListVisibleAdapter.ListVisibleViewHolder holder, final int position) {

        holder.tvName.setText(getListMountain().get(position).getName());
        holder.tvElevation.setText(getListMountain().get(position).getElevation());



        Glide.with(context).load(getListMountain().get(position).getPhoto()).into(holder.imgList);
        Glide.with(context).load(getListMountain().get(position).getPhoto()).into(holder.imgListVisible);


        holder.changeVisibility(selectedItems.get(position));


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItems.get(position)) {
                    // 펼쳐진 Item을 클릭 시
                    selectedItems.delete(position);
                } else {
                    // 직전의 클릭됐던 Item의 클릭상태를 지움
                    selectedItems.delete(prePosition);
                    // 클릭한 Item의 position을 저장
                    selectedItems.put(position, true);
                }
                // 해당 포지션의 변화를 알림
                if (prePosition != -1) notifyItemChanged(prePosition);
                notifyItemChanged(position);
                // 클릭된 position 저장
                prePosition = position;
            }
        });


    }


    @Override
    public int getItemCount() {
        return getListMountain().size();
    }

}
