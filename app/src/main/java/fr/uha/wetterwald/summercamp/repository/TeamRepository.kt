package fr.uha.wetterwald.summercamp.repository

import androidx.annotation.WorkerThread
import fr.uha.wetterwald.summercamp.database.TeamDao
import fr.uha.wetterwald.summercamp.database.TeamUpdateDTO
import fr.uha.wetterwald.summercamp.model.FullTeam
import fr.uha.wetterwald.summercamp.model.Person
import fr.uha.wetterwald.summercamp.model.Team
import fr.uha.wetterwald.summercamp.model.TeamPersonAssociation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TeamRepository(
    private val dispatcher: CoroutineDispatcher,
    private val teamDao : TeamDao
) {

    fun getAll () : Flow<List<Team>> {
        return teamDao.getAll()
    }

    fun getTeamById (id : Long) : Flow<FullTeam?> {
        return teamDao.getTeamById(id)
    }

    @WorkerThread
    suspend fun create(team: Team) : Long = withContext(dispatcher){
        return@withContext teamDao.create(team)
    }

    @WorkerThread
    suspend fun update(update: TeamUpdateDTO) = withContext(dispatcher){
        when (update) {
            is TeamUpdateDTO.Name -> teamDao.update(update)
            is TeamUpdateDTO.StartDay -> teamDao.update(update)
            is TeamUpdateDTO.Duration -> teamDao.update(update)
            is TeamUpdateDTO.Leader -> teamDao.update(update)
        }
    }

    @WorkerThread
    suspend fun upsert(team: Team) : Long = withContext(dispatcher){
        return@withContext teamDao.upsert(team)
    }

    @WorkerThread
    suspend fun delete(team: Team) = withContext(dispatcher){
        teamDao.delete(team)
    }

    @WorkerThread
    suspend fun addMember(tid : Long, person : Person) = withContext(dispatcher){
        teamDao.addMember(TeamPersonAssociation(tid, person.pid))
    }

    @WorkerThread
    suspend fun removeMember(tid : Long, person : Person) = withContext(dispatcher){
        teamDao.deleteMember(TeamPersonAssociation(tid, person.pid))
    }

}