package co.djphy.glance.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;

import butterknife.BindView;
import butterknife.OnClick;
import co.djphy.glance.R;
import co.djphy.glance.activities.NormalLoginActivity;
import co.djphy.glance.uiutils.UiRandomUtils;

/**
 * Created by DJphy on 25-01-2017.
 */

public class SocialLoginFragment extends PrimaryBaseFragment {

    @BindView(R.id.btnFbLogin)
    Button btnFbLogin;
    @BindView(R.id.btnGoogleLogin)
    SignInButton btnGoogleLogin;
    @BindView(R.id.tvAcctLogin)
    TextView tvAcctLogin;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiRandomUtils.getInstance().setGooglePlusButtonText("Sign in with Google", btnGoogleLogin);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login_social;
    }

    @Override
    protected void onGarbageCollection() {

    }

    @Override
    protected String getFragmentTitle() {
        return "";
    }


    @OnClick(R.id.btnGoogleLogin)
    void performGlLogin(){
        /*if (getActivity() instanceof AwignLoginActivity){
            ((AwignLoginActivity) getActivity()).getmSocialLoginInstance().onGoogleLogin(getActivity());
        }*/
    }

    @OnClick(R.id.tvAcctLogin)
    void launchNormalLoginScreen(){
        //RandomUtils.getInstance().launchNormalSignIn(getActivity());
        Intent intent = new Intent(getActivity(), NormalLoginActivity.class);
        startActivity(intent);
    }
}
