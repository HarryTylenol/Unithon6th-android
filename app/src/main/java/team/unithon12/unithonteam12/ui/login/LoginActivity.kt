@file:Suppress("DEPRECATION")

package team.unithon12.unithonteam12.ui.login

import android.os.Bundle
import com.bumptech.glide.Glide
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.constant.NaverClientConst
import team.unithon12.unithonteam12.data.ApiService
import team.unithon12.unithonteam12.data.ApiServiceFactory
import team.unithon12.unithonteam12.ext.isVisible
import team.unithon12.unithonteam12.ui._base.BaseActivity
import team.unithon12.unithonteam12.ui.main.MainActivity
import team.unithon12.unithonteam12.util.UserInfo

class LoginActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_login

    private val oAuthLogin: OAuthLogin by lazy { OAuthLogin.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        oAuthLogin.showDevelopersLog(true)
        oAuthLogin.init(this, NaverClientConst.CLIENT_ID, NaverClientConst.CLIENT_SECRET, "Login")

        Glide.with(this).load(R.drawable.bg_artwork_mobile).into(background)
        Glide.with(this).load(R.drawable.ic_appicon_white).into(logo)
        btn_login.setOAuthLoginHandler(handler)
        btn_login.setBgResourceId(R.drawable.btn_socialmedia)

        UserInfo.token = oAuthLogin.getAccessToken(this@LoginActivity)
        if (UserInfo.token != null) {
            btn_login_container.isVisible(false)
            tv_login_description.isVisible(false)
            btn_login_container.postDelayed({ requestEmail() }, 2000)
        }

    }

    fun requestEmail() {
        doAsync {
            val json = oAuthLogin.requestApi(this@LoginActivity, UserInfo.token, "https://openapi.naver.com/v1/nid/me")
            uiThread {
                UserInfo.email = JSONObject(json).getJSONObject("response").getString("email")
                UserInfo.token?.let {
                    requestLogin(it)
                }
            }
        }
    }

    private fun requestLogin(token: String) {
        ApiServiceFactory.apiService.login(ApiService.LoginBody(token))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {
                finish()
                startActivity<MainActivity>()
            },
            {
                it.printStackTrace()
            }
        )
    }

    private val handler = object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            when {
                success -> {
                    UserInfo.token = oAuthLogin.getAccessToken(this@LoginActivity)
                    requestEmail()
                }
            }
        }
    }

}
