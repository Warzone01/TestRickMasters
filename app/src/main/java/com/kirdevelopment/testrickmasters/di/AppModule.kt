package com.kirdevelopment.testrickmasters.di

import com.kirdevelopment.testrickmasters.common.Constants.BASE_URL
import com.kirdevelopment.testrickmasters.data.local.camera.CameraDatabaseOperations
import com.kirdevelopment.testrickmasters.data.local.door.DoorDatabaseOperations
import com.kirdevelopment.testrickmasters.data.local.migration
import com.kirdevelopment.testrickmasters.data.local.room.RoomDatabaseOperations
import com.kirdevelopment.testrickmasters.data.remote.RickMasterApi
import com.kirdevelopment.testrickmasters.data.repositories.camera.CamerasRepositoryImp
import com.kirdevelopment.testrickmasters.data.repositories.door.DoorRepositoryImp
import com.kirdevelopment.testrickmasters.data.repositories.RickMasterRepositoryImp
import com.kirdevelopment.testrickmasters.data.repositories.room.RoomRepositoryImpl
import com.kirdevelopment.testrickmasters.domain.repositories.CamerasRepository
import com.kirdevelopment.testrickmasters.domain.repositories.DoorRepository
import com.kirdevelopment.testrickmasters.domain.repositories.RickMasterRepository
import com.kirdevelopment.testrickmasters.domain.repositories.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRealmConfig(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .schemaVersion(2L)
            .migration(migration)
            .build()
    }

    @Provides
    @Singleton
    fun providesCamerasRepository(databaseOperations: CameraDatabaseOperations): CamerasRepository {
        return CamerasRepositoryImp(databaseOperations)
    }

    @Provides
    @Singleton
    fun providesRoomRepository(databaseOperations: RoomDatabaseOperations): RoomRepository {
        return RoomRepositoryImpl(databaseOperations)
    }

    @Provides
    @Singleton
    fun providesDoorRepository(databaseOperations: DoorDatabaseOperations): DoorRepository {
        return DoorRepositoryImp(databaseOperations)
    }

    @Provides
    @Singleton
    fun provideRickMasterApi(): RickMasterApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RickMasterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRickMasterRepository(api: RickMasterApi): RickMasterRepository {
        return RickMasterRepositoryImp(api)
    }
}