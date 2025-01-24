package fr.uha.wetterwald.summercamp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.uha.wetterwald.summercamp.model.FullTeam
import fr.uha.wetterwald.summercamp.model.Team
import fr.uha.wetterwald.summercamp.model.TeamPersonAssociation
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Query("SELECT * from teams")
    fun getAll () : Flow<List<Team>>

    @Query("SELECT * from teams WHERE tid = :id")
    fun getTeamById (id : Long) : Flow<FullTeam?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create (team: Team) : Long

    @Update(entity = Team::class)
    suspend fun update (team : TeamUpdateDTO.Name)

    @Update(entity = Team::class)
    suspend fun update (team : TeamUpdateDTO.StartDay)

    @Update(entity = Team::class)
    suspend fun update (team : TeamUpdateDTO.Duration)

    @Update(entity = Team::class)
    suspend fun update (team : TeamUpdateDTO.Leader)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert (team: Team) : Long

    @Delete
    suspend fun delete (team: Team)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMember (assoc : TeamPersonAssociation)

    @Delete
    suspend fun deleteMember (assoc : TeamPersonAssociation)

}