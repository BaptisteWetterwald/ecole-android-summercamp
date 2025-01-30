package fr.uha.wetterwald.summercamp.database

import android.util.Log
import fr.uha.wetterwald.summercamp.model.*
import java.util.*

class DbInitializer(private val db: AppDatabase) {

    // Insérer des données fictives dans la base
    suspend fun populate() {
        populateChildren()
        populateSupervisors()
        populateActivities()
    }

    // Vider toutes les tables
    fun clearDatabase() {
        db.clearAllTables()
    }

    suspend fun populateChildren() {
        for (i in 1..10) {
            db.childDao().upsert(generateChild())
        }
    }

    suspend fun populateSupervisors() {
        for (i in 1..5) {
            db.supervisorDao().upsert(generateSupervisor())
        }
    }

    fun populateActivities() {
        for (i in 1..5) {
            db.activityDao().update(generateActivity(i.toLong()))
        }
    }

    fun generateChild() : Child {
        return Child(
            firstname = randomFirstname(),
            lastname = randomLastname(),
            age = randomMinorAge(),
            picture = null,
            gender = randomGender(),
            parentPhone = randomPhone()
        )
    }

    fun generateSupervisor() : Supervisor {
        var specialties = mutableListOf<Specialty>()
        for (i in 1..Random().nextInt(3)+1) {
            specialties.add(randomSpecialty())
        }
        Log.d("DbInitializer", "generateSupervisor: $specialties")
        return Supervisor(
            firstname = randomFirstname(),
            lastname = randomLastname(),
            age = randomAdultAge(),
            picture = null,
            phone = randomPhone(),
            gender = randomGender(),
            specialties = specialties,
            availability = "09:00-12:00, 14:00-17:00"
        )
    }

    fun generateActivity(id : Long) : Activity {
        val name = randomNameFromSpecialty(randomSpecialty())
        return Activity(
            activityId = id,
            name = name,
            description = "This is a $name activity",
            location = randomLocation(),
            maxParticipants = 3 + Random().nextInt(18),
            period = randomPeriod(),
            specialty = randomSpecialty()
        )
    }

    fun randomPeriod() : String {
        val start = 6 + Random().nextInt(17)
        var end = (start + 1 + Random().nextInt(24 - start)) % 24
        val paddedStart = start.toString().padStart(2, '0')
        val paddedEnd = end.toString().padStart(2, '0')
        return "${randomDateStr()} $paddedStart:00-$paddedEnd:00"
    }

    fun randomDateStr() : String {
        val year = 2025
        val month = 1 + Random().nextInt(12)
        val day = 1 + Random().nextInt(28)
        return "${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/$year"
    }

    fun randomNameFromSpecialty(specialty: Specialty) : String {
        return when(specialty){
            Specialty.ARTS -> randomActivityNameArt()
            Specialty.SCIENCE -> randomActivityNameScience()
            Specialty.GAMES -> randomActivityNameGames()
            Specialty.SPORTS -> randomActivityNameSports()
            Specialty.COOKING -> randomActivityNameCooking()
            Specialty.CRAFTS -> randomActivityNameCrafts()
            Specialty.MUSIC -> randomActivityNameMusic()
            Specialty.OTHER -> randomActivityNameOther()
        }
    }

    fun randomLocation() : String {
        val locations = arrayOf("Classroom", "Gym", "Auditorium", "Library", "Computer lab", "Science lab", "Art studio", "Music room", "Dance studio", "Theater", "Cafeteria", "Kitchen", "Playground", "Sports field", "Swimming pool", "Skating rink", "Ski resort", "Beach", "Park", "Zoo", "Museum", "Aquarium", "Planetarium", "Botanical garden", "Farm", "Orchard", "Vineyard", "Forest", "Mountain", "Lake", "River", "Ocean", "Desert", "Jungle", "Savanna", "Tundra", "Prairie", "Marsh", "Swamp", "Cave", "Volcano", "Waterfall", "Geyser", "Hot spring", "Glacier", "Iceberg", "Ice cave", "Ice palace", "Ice hotel", "Ice bar", "Ice cream", "Ice cream truck", "Ice cream parlor", "Ice cream cone", "Ice cream sandwich", "Ice cream cake", "Ice cream sundae", "Ice cream float", "Ice cream soda", "Ice cream social", "Ice cream party", "Ice cream truck", "Ice cream parlor", "Ice cream cone", "Ice cream sandwich", "Ice cream cake", "Ice cream sundae", "Ice cream float", "Ice cream soda", "Ice cream social", "Ice cream party")
        return locations[Random().nextInt(locations.size)]
    }

    fun randomActivityNameArt() : String {
        val names = arrayOf("Painting", "Drawing", "Sculpture", "Pottery", "Photography", "Collage", "Origami", "Calligraphy", "Graffiti", "Street art", "Mosaic", "Stained glass", "Tapestry")
        return names[Random().nextInt(names.size)]
    }

    fun randomActivityNameScience() : String {
        val names = arrayOf("Chemistry", "Physics", "Biology", "Astronomy", "Geology", "Botany", "Zoology", "Ecology", "Meteorology", "Oceanography", "Paleontology", "Entomology", "Herpetology", "Ornithology", "Ichthyology", "Mycology", "Microbiology", "Virology", "Immunology", "Genetics", "Biochemistry", "Biophysics", "Biotechnology", "Neuroscience", "Psychology", "Sociology", "Anthropology", "Archaeology", "Linguistics", "Economics", "Political science", "History", "Geography", "Philosophy", "Theology")
        return names[Random().nextInt(names.size)]
    }

    fun randomActivityNameGames() : String {
        val names = arrayOf("Board games", "Card games", "Video games", "Role-playing games", "Puzzles", "Crosswords", "Sudoku", "Chess", "Checkers", "Go", "Backgammon", "Mahjong", "Dominoes", "Darts", "Billiards", "Bowling", "Mini-golf", "Ping-pong", "Foosball", "Air hockey", "Pinball", "Arcade games", "Laser tag", "Paintball", "Escape room", "Treasure hunt", "Scavenger hunt", "Geocaching", "Orienteering", "Hiking", "Cycling", "Skating", "Skateboarding", "Skiing", "Snowboarding", "Sledding", "Snowball fight", "Snowman", "Ice skating", "Ice hockey", "Ice fishing", "Ice climbing", "Ice sculpting", "Ice hotel", "Ice bar", "Ice cream", "Ice cream truck", "Ice cream parlor", "Ice cream cone", "Ice cream sandwich", "Ice cream cake", "Ice cream sundae", "Ice cream float", "Ice cream soda", "Ice cream social", "Ice cream party", "Ice cream truck", "Ice cream parlor", "Ice cream cone", "Ice cream sandwich", "Ice cream cake", "Ice cream sundae", "Ice cream float", "Ice cream soda", "Ice cream social", "Ice cream party")
        return names[Random().nextInt(names.size)]
    }

    fun randomActivityNameSports() : String {
        val names = arrayOf("Football", "Basketball", "Volleyball", "Handball", "Rugby", "Tennis", "Table tennis", "Badminton", "Squash", "Golf", "Mini-golf", "Bowling", "Billiards", "Darts", "Archery", "Shooting", "Fishing", "Hiking", "Cycling", "Skating", "Skateboarding", "Skiing", "Snowboarding", "Sledding", "Snowball fight", "Snowman", "Ice skating", "Ice hockey", "Ice fishing", "Ice climbing", "Ice sculpting", "Ice hotel", "Ice bar", "Ice cream", "Ice cream truck", "Ice cream parlor", "Ice cream cone", "Ice cream sandwich", "Ice cream cake", "Ice cream sundae", "Ice cream float", "Ice cream soda", "Ice cream social", "Ice cream party", "Ice cream truck", "Ice cream parlor", "Ice cream cone", "Ice cream sandwich", "Ice cream cake", "Ice cream sundae", "Ice cream float", "Ice cream soda", "Ice cream social", "Ice cream party")
        return names[Random().nextInt(names.size)]
    }

    fun randomActivityNameCooking() : String {
        val names = arrayOf("Baking", "Pastry", "Confectionery", "Chocolate", "Candy", "Ice cream", "Gelato", "Sorbet", "Sherbet", "Frozen yogurt", "Popsicle", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor", "Cone", "Sandwich", "Cake", "Sundae", "Float", "Soda", "Social", "Party", "Truck", "Parlor")
        return names[Random().nextInt(names.size)]
    }

    fun randomActivityNameCrafts() : String {
        val names = arrayOf("Sewing", "Knitting", "Crocheting", "Embroidery", "Cross-stitch", "Tapestry", "Macramé", "Weaving", "Spinning", "Felting", "Quilting", "Patchwork", "Appliqué", "Origami", "Kirigami", "Paper cutting", "Paper folding", "Paper mache", "Decoupage", "Collage", "Mosaic", "Stained glass", "Pottery", "Ceramics", "Sculpture", "Wood carving", "Wood burning", "Wood turning", "Woodworking", "Metalworking", "Blacksmithing", "Jewelry", "Beadwork", "Glassblowing", "Glass etching")
        return names[Random().nextInt(names.size)]
    }

    fun randomActivityNameMusic() : String {
        val names = arrayOf("Singing", "Choir", "A cappella", "Barbershop", "Gospel", "Opera", "Musical", "Theater", "Dance", "Ballet", "Tap", "Jazz", "Hip-hop", "Breakdance", "Ballroom", "Latin", "Swing", "Country", "Folk", "Bluegrass", "Blues", "Jazz", "Ragtime", "Dixieland", "Big band", "Swing", "Bebop", "Cool jazz", "Hard bop", "Free jazz", "Fusion", "Smooth jazz", "Latin jazz", "Afro-Cuban jazz", "Salsa", "Mambo", "Cha-cha-cha", "Rumba", "Samba", "Bossa nova", "Tango", "Flamenco", "Samba", "Salsa", "Merengue", "Reggaeton", "Cumbia", "Bachata", "Soca", "Calypso", "Zouk", "Ska", "Reggae", "Dancehall", "Dub", "Ska", "Rocksteady", "Roots reggae", "Dub", "Lovers rock")
        return names[Random().nextInt(names.size)]
    }

    fun randomActivityNameOther() : String {
        val names = arrayOf("Yoga", "Pilates", "Tai chi", "Qi gong", "Meditation", "Mindfulness", "Relaxation", "Breathing", "Stretching", "Aerobics", "Zumba", "Step", "Spinning", "Kickboxing", "Boxing", "Martial arts", "Karate", "Judo", "Taekwondo", "Kung fu", "Capoeira", "Krav maga", "Muay Thai", "Wrestling", "Jiu-jitsu", "Sambo", "Sumo", "Aikido", "Hapkido", "Hwa rang do", "Tang soo do", "Haidong gumdo", "Gymnastics", "Acrobatics", "Circus", "Clown", "Juggling", "Magic", "Puppetry", "Ventriloquism", "Balloon twisting", "Face painting", "Body painting", "Henna", "Tattoo", "Piercing", "Haircut")
        return names[Random().nextInt(names.size)]
    }

    fun randomSpecialty() : Specialty {
        return Specialty.values()[Random().nextInt(Specialty.values().size)]
    }

    fun randomFirstname() : String {
        val firstnames = arrayOf("Alice", "Bob", "Charlie", "David", "Eve", "Fiona", "George", "Helen", "Ivan", "Julia", "Kevin", "Linda", "Michael", "Nancy", "Oscar", "Pamela", "Quentin", "Rachel", "Steve", "Tina", "Ursula", "Victor", "Wendy", "Xavier", "Yvonne", "Zack")
        return firstnames[Random().nextInt(firstnames.size)]
    }

    fun randomLastname() : String {
        val lastnames = arrayOf("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James", "Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross", "Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington", "Butler", "Simmons", "Foster", "Gonzales", "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Hayes")
        return lastnames[Random().nextInt(lastnames.size)]
    }

    fun randomMinorAge() : Int {
        return 6 + Random().nextInt(12)
    }

    fun randomAdultAge() : Int {
        return 18 + Random().nextInt(50)
    }

    fun randomPhone() : String {
        return "06" + (10000000 + Random().nextInt(90000000))
    }

    fun randomGender() : Gender {
        return if (Random().nextBoolean())
            Gender.MALE
        else
            Gender.FEMALE
    }

}
