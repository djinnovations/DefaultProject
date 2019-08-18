package co.djphy.glance.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.TextView;

import co.djphy.glance.R;

/**
 * Created by DJphy on 10-07-2017.
 */

public class TabFragment2 extends PrimaryBaseFragment{

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout_dummy, container, false);
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.tvItem)).setText("Tab 2");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_layout_dummy;
    }

    @Override
    protected void onGarbageCollection() {

    }

    @Override
    protected String getFragmentTitle() {
        return "Tab - 2";
    }
}
