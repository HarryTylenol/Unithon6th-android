@file:Suppress("DEPRECATION")

package team.unithon12.unithonteam12.ui.login

import android.os.Bundle
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import team.unithon12.unithonteam12.R
import team.unithon12.unithonteam12.constant.NaverClientConst
import team.unithon12.unithonteam12.ui._base.BaseActivity

class LoginActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_login

    private val onAuthLogin: OAuthLogin by lazy { OAuthLogin.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAuthLogin.showDevelopersLog(true)
        onAuthLogin.init(this, NaverClientConst.CLIENT_ID, NaverClientConst.CLIENT_SECRET, "Login")

        val handler = object : OAuthLoginHandler() {
            override fun run(success: Boolean) {
                toast(success.toString())
            }
        }

        login_button.setOAuthLoginHandler(handler)
        login_button.setBgResourceId(R.drawable.btn_socialmedia)
    }

}
