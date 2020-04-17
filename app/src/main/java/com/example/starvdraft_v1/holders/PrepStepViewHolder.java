package com.example.starvdraft_v1.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.models.Ingredient;
import com.example.starvdraft_v1.models.PrepStep;

import androidx.recyclerview.widget.RecyclerView;

public class PrepStepViewHolder extends RecyclerView.ViewHolder {

    TextView tv_PrepStep;
    public ImageView iv_DeleteStep;

    public PrepStepViewHolder(View itemView) {
        super(itemView);

        tv_PrepStep = (TextView) itemView.findViewById(R.id.tv_PrepStep);
        iv_DeleteStep = (ImageView) itemView.findViewById(R.id.iv_DeleteStep);

    }

    public void updateUI(PrepStep prepStep) {

        String formattedStep = prepStep.getPrepStep();
        tv_PrepStep.setText(formattedStep);

    }

}
