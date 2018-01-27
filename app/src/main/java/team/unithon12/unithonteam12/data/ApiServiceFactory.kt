package team.unithon12.unithonteam12.data

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import team.unithon12.unithonteam12.App
import team.unithon12.unithonteam12.BuildConfig
import team.unithon12.unithonteam12.constant.ServerConst

object ApiServiceFactory {

    val apiService by lazy {
        makeApiService(BuildConfig.DEBUG, App.context!!)
    }

    fun makeApiService(isDebug: Boolean, context: Context): ApiService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(isDebug), context
        )
        return makeFeedGetService(okHttpClient, makeGson())
    }

    private fun makeFeedGetService(okHttpClient: OkHttpClient, gson: Gson): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(ServerConst.URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(makeCookieInterceptor(context))
            .addInterceptor(makeAddCookieInterceptor(context))
            .build()
    }

    private fun makeAddCookieInterceptor(context: Context): Interceptor = Interceptor { chain ->
        val builder = chain.request().newBuilder()
        val preferences = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("Cookies", HashSet()) as HashSet<String>
        for (cookie in preferences) {
            builder.addHeader("Cookie", cookie)
        }
        chain.proceed(builder.build())


    }

    @SuppressLint("ApplySharedPref")
    private fun makeCookieInterceptor(context: Context): Interceptor = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = HashSet<String>()
            cookies += originalResponse.headers("Set-Cookie")
            PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet("Cookies", cookies).commit()
        }

        originalResponse
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}
