package team.unithon12.unithonteam12.data

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import team.unithon12.unithonteam12.data.model.Script

interface ApiService {

    @POST("/login")
    fun login(
        @Body body: LoginBody
    ): Completable

    data class LoginBody(
        val token: String
    )

    @GET("/getSlist")
    fun getScripts(): Single<List<Script>>
}
