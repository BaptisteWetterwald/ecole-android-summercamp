package fr.uha.wetterwald.summercamp.database

import android.net.Uri
import fr.uha.wetterwald.summercamp.model.*
import fr.uha.wetterwald.summercamp.R
import java.util.*

class FeedDatabase (
    private val db : TeamDatabase
) {

    private suspend fun feedPersons(): LongArray {
        val dao: PersonDao = db.personDAO()
        val ids = LongArray(4)
        ids[0] = dao.create(getRandomPerson(Gender.BOY))
        ids[1] = dao.create(getRandomPerson(Gender.GIRL))
        ids[2] = dao.create(getRandomPerson(Gender.NO))
        ids[3] = dao.create(getRandomPerson(Gender.NO))
        return ids
    }

    private suspend fun feedTeams(pids: LongArray) {
        val dao: TeamDao = db.teamDAO()
        val team = getRandomTeam(pids[0])
        val tid = dao.upsert(team)
        dao.addMember(TeamPersonAssociation(tid, pids[0]))
        dao.addMember(TeamPersonAssociation(tid, pids[3]))
    }

    @Suppress("unused")
    suspend fun populate(mode : Int) {
        val pids = feedPersons()
        feedTeams(pids)
    }

    fun clear() {
        db.clearAllTables()
    }

    companion object {
        private val rnd: Random = Random()
        private val maleFirstNames: Array<String> = arrayOf(
                "Alexander",
                "Brendon",
                "Carrol",
                "Davis",
                "Emmerson",
                "Franklin",
                "Gordon",
                "Humphrey",
                "Ike",
                "Jarrod",
                "Kevin",
                "Lionel",
                "Mickey",
                "Nathan",
                "Oswald",
                "Phillip",
                "Quinn",
                "Ralph",
                "Shawn",
                "Terrence",
                "Urban",
                "Vince",
                "Wade",
                "Xan",
                "Yehowah",
                "Zed"
        )
        private val femaleFirstNames: Array<String> = arrayOf(
                "Abigail",
                "Betsy",
                "Carry",
                "Dana",
                "Edyth",
                "Fay",
                "Grace",
                "Hannah",
                "Isabel",
                "Jane",
                "Karrie",
                "Lauren",
                "Maddie",
                "Nanna",
                "Oprah",
                "Pamela",
                "Queen",
                "Rachel",
                "Samanta",
                "Tess",
                "Ursula",
                "Violet",
                "Wendy",
                "Xena",
                "Yvonne",
                "Zoey"
        )
        private val lastNames: Array<String> = arrayOf(
                "Activox",
                "Biseptine",
                "Calendula",
                "Delidose",
                "Eludril",
                "Fervex",
                "Gelox",
                "Hextril",
                "Imurel",
                "Jouvence",
                "Kenzen",
                "Lanzor",
                "Malocide",
                "Nicorette",
                "Oflocet",
                "Paracetamol",
                "Quotane",
                "Rennie",
                "Smecta",
                "Tamiflu",
                "Uniflox",
                "Vectrine",
                "Wellvone",
                "Xanax",
                "Yranol",
                "Zyban"
        )
        private val teamNames: Array<String> = arrayOf(
                "Zeus",
                "Héra",
                "Hestia",
                "Déméter",
                "Apollon",
                "Artémis",
                "Héphaïstos",
                "Athéna",
                "Arès",
                "Aphrodite",
                "Hermès",
                "Dionysos"
        )

        private val malePictures: Array<Int> = arrayOf(
            R.mipmap.person_male_1,
            R.mipmap.person_male_2,
        )
        private val femalePictures: Array<Int> = arrayOf(
            R.mipmap.person_female_1,
            R.mipmap.person_female_2,
            R.mipmap.person_female_3,
        )
        private const val schemeName : String = "android.resource"
        private const val packageName : String = "fr.uha.hassenforder.team"
        private fun buildResourceUri (id : Int) : Uri {
            return Uri.Builder()
                .scheme(schemeName)
                .authority(packageName)
                .path(id.toString())
                .build()
        }

        private fun getRandomPicture (gender: Gender) : Uri? {
            val id : Int = when (gender) {
                Gender.NO -> 0
                Gender.BOY -> femalePictures[rnd.nextInt(femalePictures.size)]
                Gender.GIRL -> malePictures[rnd.nextInt(malePictures.size)]
            }
            if (id == 0) return null
            return buildResourceUri(id)
        }

        private fun getRandomName(names: Array<String>): String {
            return names[rnd.nextInt(names.size)]
        }

        private fun getRandomFirstName(gender: Gender): String {
            var g = gender
            if (gender == Gender.NO) {
                g = if (rnd.nextInt(1000) > 500) {
                    Gender.BOY
                } else {
                    Gender.GIRL
                }
            }
            return when (g) {
                Gender.BOY -> getRandomName(maleFirstNames)
                Gender.GIRL -> getRandomName(femaleFirstNames)
                else -> ""
            }
        }

        private fun getRandomLastName(): String {
            return getRandomName(lastNames)
        }

        private fun getRandomPhone(): String {
            val tmp = StringBuilder()
            if (rnd.nextInt(1000) > 750) {
                tmp.append("36")
                tmp.append(rnd.nextInt(10))
                tmp.append(rnd.nextInt(10))
            } else {
                tmp.append("0")
                for (i in 0..8) {
                    tmp.append(rnd.nextInt(10))
                }
            }
            return tmp.toString()
        }

        private fun getRandomBetween(low: Int, high: Int): Int {
            return rnd.nextInt(high - low) + low
        }

        private fun getRandomBetween(low: Double, high: Double): Double {
            return rnd.nextDouble() * (high - low) + low
        }

        private fun getRandomPerson(gender: Gender): Person {
            return Person(0,
                    getRandomFirstName(gender),
                    getRandomLastName(),
                    getRandomPhone(),
                    gender,
                    getRandomPicture(gender)
            )
        }

        private fun getRandomTeamName(): String {
            return getRandomName(teamNames)
        }

        private fun getRandomTeam(leader: Long): Team {
            return Team(0,
                    getRandomTeamName(),
                    Date(),
                    getRandomBetween(3, 9),
                    leader
            )
        }

    }
}