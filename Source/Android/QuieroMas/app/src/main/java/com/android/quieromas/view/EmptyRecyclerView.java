package com.android.quieromas.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.android.quieromas.R;
import com.android.quieromas.fragment.SearchFragment;
import com.firebase.client.annotations.NotNull;

/**
 * Created by lucas on 29/6/17.
 */

public class EmptyRecyclerView extends RecyclerView {

    @Nullable
    View emptyView;

    public EmptyRecyclerView(Context context) { super(context); }

    public EmptyRecyclerView(Context context, AttributeSet attrs) { super(context, attrs); }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {
        if (emptyView != null) {
            if(getAdapter() != null){
                emptyView.setVisibility(getAdapter().getItemCount() > 0 ? GONE : VISIBLE);
            }else{
                emptyView.setVisibility(VISIBLE);
            }

        }
    }

    final @NotNull AdapterDataObserver observer = new AdapterDataObserver() {
        @Override public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }
    };

    @Override public void setAdapter(@Nullable Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
            checkIfEmpty();
        }
    }

    public void setEmptyView(@Nullable View emptyView) {
        this.emptyView = emptyView;
    }

    public View getEmptyView() {
        return emptyView;
    }

    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }

        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        super.swapAdapter(adapter, removeAndRecycleExistingViews);
        checkIfEmpty();
    }
}