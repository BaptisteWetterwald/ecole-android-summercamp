package fr.uha.wetterwald.summercamp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.uha.wetterwald.summercamp.database.ActivityDao
import fr.uha.wetterwald.summercamp.database.AppDatabase
import fr.uha.wetterwald.summercamp.database.ChildDao
import fr.uha.wetterwald.summercamp.database.PersonDao
import fr.uha.wetterwald.summercamp.database.SupervisorDao
import fr.uha.wetterwald.summercamp.repository.ActivityRepository
import fr.uha.wetterwald.summercamp.repository.ChildRepository
import fr.uha.wetterwald.summercamp.repository.PersonRepository
import fr.uha.wetterwald.summercamp.repository.SupervisorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.create(appContext)

    @Singleton
    @Provides
    fun providePersonDao(db: AppDatabase) = db.personDao()

    @Singleton
    @Provides
    fun provideSupervisorDao(db: AppDatabase) = db.supervisorDao()

    @Singleton
    @Provides
    fun provideChildDao(db: AppDatabase) = db.childDao()

    @Singleton
    @Provides
    fun provideActivityDao(db: AppDatabase) = db.activityDao()

    @Singleton
    @Provides
    fun provideActivityRepository(
        activityDao: ActivityDao,
        ioDispatcher: CoroutineDispatcher
    ) = ActivityRepository(activityDao, ioDispatcher)


    @Singleton
    @Provides
    fun providePersonRepository(
        personDao: PersonDao,
        ioDispatcher: CoroutineDispatcher
    ) = PersonRepository(personDao, ioDispatcher)

    @Singleton
    @Provides
    fun provideChildRepository(
        childDao: ChildDao,
        ioDispatcher: CoroutineDispatcher
    ) = ChildRepository(childDao, ioDispatcher)

    @Singleton
    @Provides
    fun provideSupervisorRepository(
        supervisorDao: SupervisorDao,
        ioDispatcher: CoroutineDispatcher
    ) = SupervisorRepository(supervisorDao, ioDispatcher)

}