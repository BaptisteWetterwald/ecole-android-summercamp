package fr.uha.wetterwald.summercamp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.uha.wetterwald.summercamp.database.PersonDao
import fr.uha.wetterwald.summercamp.database.TeamDao
import fr.uha.wetterwald.summercamp.database.TeamDatabase
import fr.uha.wetterwald.summercamp.repository.PersonRepository
import fr.uha.wetterwald.summercamp.repository.TeamRepository
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
    fun provideDatabase(@ApplicationContext appContext: Context) = TeamDatabase.create(appContext)

    @Singleton
    @Provides
    fun providePersonDao(db: TeamDatabase) = db.personDAO()

    @Singleton
    @Provides
    fun providePersonRepository(
        ioDispatcher: CoroutineDispatcher,
        personDao: PersonDao
    ) = PersonRepository(ioDispatcher, personDao)

    @Singleton
    @Provides
    fun provideTeamDao(db: TeamDatabase) = db.teamDAO()

    @Singleton
    @Provides
    fun provideTeamRepository(
        ioDispatcher: CoroutineDispatcher,
        teamDao: TeamDao
    ) = TeamRepository(ioDispatcher, teamDao)

}