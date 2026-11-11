package com.example.chorarmela.data

enum class RhymeCategory(val titleBn: String, val titleEn: String, val icon: String) {
    ALL("সব ছড়া", "All Rhymes", "📚"),
    FAVORITE("জনপ্রিয়", "Popular", "🌟"),
    ANIMALS("পশুপাখি", "Animals & Birds", "🦜"),
    BEDTIME("ঘুমপাড়ানি", "Lullabies", "🌙"),
    CLASSIC("বিখ্যাত কবি", "Great Poets", "✍️"),
    PLAYFUL("খেলার ছড়া", "Fun & Play", "🎈")
}

data class Rhyme(
    val id: String,
    val titleBn: String,
    val titleEn: String,
    val authorBn: String,
    val authorEn: String,
    val category: RhymeCategory,
    val iconEmoji: String,
    val themeColorHex: Long,
    val linesBn: List<String>,
    val linesEn: List<String>,
    val meaning: String,
    val funFact: String
)

object RhymeRepository {
    val rhymes: List<Rhyme> = listOf(
        Rhyme(
            id = "hattima_tim_tim",
            titleBn = "হাট্টিমা টিম টিম",
            titleEn = "Hattima Tim Tim",
            authorBn = "রোকনুজ্জামান খান",
            authorEn = "Rokonuzzaman Khan",
            category = RhymeCategory.FAVORITE,
            iconEmoji = "🦏",
            themeColorHex = 0xFFFF7043,
            linesBn = listOf(
                "হাট্টিমা টিম টিম",
                "তারা মাঠে পাড়ে ডিম,",
                "তাদের খাড়া দুটো শিং,",
                "তারা হাট্টিমা টিম টিম।"
            ),
            linesEn = listOf(
                "Hattima tim tim",
                "Tara mathe pare dim,",
                "Tader khara duto shing,",
                "Tara hattima tim tim."
            ),
            meaning = "A delightfully imaginative creature with straight upright horns that lays eggs out in open fields.",
            funFact = "This is one of Bengal's most beloved nursery rhymes, adored by generations of toddlers and school kids!"
        ),
        Rhyme(
            id = "aay_re_aay_tiye",
            titleBn = "আয় রে আয় টিয়ে",
            titleEn = "Aay Re Aay Tiye",
            authorBn = "লোকছড়া (প্রচলিত)",
            authorEn = "Traditional Folk Rhyme",
            category = RhymeCategory.ANIMALS,
            iconEmoji = "🦜",
            themeColorHex = 0xFF4CAF50,
            linesBn = listOf(
                "আয় রে আয় টিয়ে,",
                "নায়ে ভরা দিয়ে।",
                "নিয়ে গেল বোয়াল মাছে,",
                "তাই না দেখে কোলা ব্যাঙে",
                "বাজায় ঢাক ঢাক।"
            ),
            linesEn = listOf(
                "Aay re aay tiye,",
                "Naaye bhora diye.",
                "Niye gelo boyal maache,",
                "Taai na dekhe kola bange",
                "Bajay dhak dhak."
            ),
            meaning = "An invitation to a parrot arriving on a boat, followed by amusing river creatures playing drums.",
            funFact = "Bengal has a deep bond with river life, fish, and birds, inspiring this musical rhyme."
        ),
        Rhyme(
            id = "khoka_ghumalo_para_juralo",
            titleBn = "খোকা ঘুমাল পাড়া জুড়াল",
            titleEn = "Khoka Ghumalo Para Juralo",
            authorBn = "লোকছড়া (প্রচলিত)",
            authorEn = "Traditional Folk Lullaby",
            category = RhymeCategory.BEDTIME,
            iconEmoji = "🌙",
            themeColorHex = 0xFF5C6BC0,
            linesBn = listOf(
                "খোকা ঘুমাল পাড়া জুড়াল,",
                "বর্গী এল দেশে।",
                "বুলবুলিতে ধান খেয়েছে,",
                "খাজনা দেব কিসে?",
                "ধান ফুরাল পান ফুরাল,",
                "খাজনার উপায় কি?",
                "আর কটা দিন সবুর কর,",
                "রসুন বুনেছি।"
            ),
            linesEn = listOf(
                "Khoka ghumalo para juralo,",
                "Borgi elo deshe.",
                "Bulbulite dhaan kheyeche,",
                "Khajna debo kishe?",
                "Dhaan furalo paan furalo,",
                "Khajnar upay ki?",
                "Aar kota din sobur koro,",
                "Roshun bunechi."
            ),
            meaning = "The timeless lullaby of mothers rocking their sleepy babies while singing of ancient folklore.",
            funFact = "Composed centuries ago, this historic lullaby reflects rustic village tranquility and motherly love."
        ),
        Rhyme(
            id = "amader_chhoto_nodi",
            titleBn = "আমাদের ছোট নদী",
            titleEn = "Amader Chhoto Nodi",
            authorBn = "রবীন্দ্রনাথ ঠাকুর",
            authorEn = "Rabindranath Tagore",
            category = RhymeCategory.CLASSIC,
            iconEmoji = "🌊",
            themeColorHex = 0xFF00ACC1,
            linesBn = listOf(
                "আমাদের ছোট নদী চলে আঁকে বাঁকে,",
                "বৈশাখ মাসে তার হাঁটু জল থাকে।",
                "পার হয়ে যায় গরু, পার হয় গাড়ি,",
                "দুই ধার উঁচু তার, ঢালু তার পাড়ি।",
                "চিক্ চিক্ করে বালী, কোথা নাই কাদা,",
                "একধারে কাশবন ফুলে ফুলে সাদা।"
            ),
            linesEn = listOf(
                "Amader chhoto nodi chole aanke baanke,",
                "Boishakh mashe tar haantu jol thake.",
                "Paar hoye jaay goru, paar hoy gaari,",
                "Dui dhar uchu tar, dhalu tar paari.",
                "Chik chik kore baali, kotha naai kada,",
                "Ekdhare kashbon phule phule shaada."
            ),
            meaning = "Nobel Laureate Rabindranath Tagore's poetic masterpiece painting the serene beauty of a quiet village river.",
            funFact = "Tagore wrote this beloved poem to teach children to appreciate nature's winding rivers and blooming Kash flowers."
        ),
        Rhyme(
            id = "bhor_holo_dor_kholo",
            titleBn = "ভোর হলো দোর খোলো",
            titleEn = "Bhor Holo Dor Kholo",
            authorBn = "কাজী নজরুল ইসলাম",
            authorEn = "Kazi Nazrul Islam",
            category = RhymeCategory.CLASSIC,
            iconEmoji = "☀️",
            themeColorHex = 0xFFFFA000,
            linesBn = listOf(
                "ভোর হলো দোর খোলো,",
                "খুকুমণি ওঠ রে!",
                "ঐ ডাকে জুঁই-শাখে",
                "ফুল-খুকী ছোট রে!",
                "খুকুমণি ওঠ রে!",
                "রবি মামা দেয় হামা",
                "গায়ে রাঙা জামা রে!"
            ),
            linesEn = listOf(
                "Bhor holo dor kholo,",
                "Khukumoni oth re!",
                "Oi daake jui-shaakhe",
                "Phul-khuki chhot re!",
                "Khukumoni oth re!",
                "Robi mama dey hama",
                "Gaye ranga jama re!"
            ),
            meaning = "National Poet Kazi Nazrul Islam's cheerful wake-up song calling children to greet the morning sun.",
            funFact = "This rhythmically energetic morning poem has welcomed millions of Bengali kids to school each sunrise!"
        ),
        Rhyme(
            id = "chand_utheche_phul_phuteche",
            titleBn = "চাঁদ উঠেছে ফুল ফুটেছে",
            titleEn = "Chand Utheche Phul Phuteche",
            authorBn = "লোকছড়া (প্রচলিত)",
            authorEn = "Traditional Folk Rhyme",
            category = RhymeCategory.BEDTIME,
            iconEmoji = "🌸",
            themeColorHex = 0xFF8E24AA,
            linesBn = listOf(
                "চাঁদ উঠেছে ফুল ফুটেছে,",
                "কদম তলায় কে?",
                "হাতি নাচছে ঘোড়া নাচছে,",
                "সোনামণির বে।"
            ),
            linesEn = listOf(
                "Chand utheche phul phuteche,",
                "Kodom tolai ke?",
                "Haathi naachche ghora naachche,",
                "Sonamonir be."
            ),
            meaning = "Under the moon and blooming blossoms, animals dance in celebratory joy for the darling child.",
            funFact = "A festive and magical rhyme sung during celebrations and family gatherings."
        ),
        Rhyme(
            id = "aata_gache_tota_pakhi",
            titleBn = "আতা গাছে তোতা পাখি",
            titleEn = "Aata Gache Tota Pakhi",
            authorBn = "লোকছড়া (প্রচলিত)",
            authorEn = "Traditional Folk Rhyme",
            category = RhymeCategory.ANIMALS,
            iconEmoji = "🌳",
            themeColorHex = 0xFF2E7D32,
            linesBn = listOf(
                "আতা গাছে তোতা পাখি,",
                "ডালিম গাছে মৌ।",
                "এত ডাকি তবু কথা",
                "কও না কেন বউ?"
            ),
            linesEn = listOf(
                "Aata gaache tota pakhi,",
                "Daalim gaache mou.",
                "Eto daaki tobu kotha",
                "Kow na keno bou?"
            ),
            meaning = "Custard-apple tree has parrots, pomegranate trees have honey; playful question asking why the bride is so shy.",
            funFact = "This catchy 4-line rhyme is usually the very first poem taught to Bengali preschool children!"
        ),
        Rhyme(
            id = "baburam_sapure",
            titleBn = "বাবুরাম সাপুড়ে",
            titleEn = "Baburam Sapure",
            authorBn = "সুকুমার রায়",
            authorEn = "Sukumar Ray",
            category = RhymeCategory.PLAYFUL,
            iconEmoji = "🐍",
            themeColorHex = 0xFFD81B60,
            linesBn = listOf(
                "বাবুরাম সাপুড়ে,",
                "কোথা যাস বাপুরে?",
                "আয় তবে দেখে যা,",
                "দুটো সাপ রেখে যা!",
                "যে সাপের চোখ নেই,",
                "শিং নেই নখ নেই,",
                "ছুটে কিম্বা হাঁটে না,",
                "কাউকে যে কাটে না!"
            ),
            linesEn = listOf(
                "Baburam sapure,",
                "Kotha jaas baapure?",
                "Aay tobe dekhe ja,",
                "Duto saap rekhe ja!",
                "Je saaper chokh nei,",
                "Shing nei nokh nei,",
                "Chhute kimba haante na,",
                "Kaoke je kaate na!"
            ),
            meaning = "Master of nonsense verse Sukumar Ray humorously orders a harmless snake with no teeth, claws, or bite!",
            funFact = "Sukumar Ray is revered as the Lewis Carroll of Bengali literature, creating endless hilarious nonsense rhymes."
        ),
        Rhyme(
            id = "kathberali_kathberali",
            titleBn = "কাঠবেড়ালী কাঠবেড়ালী",
            titleEn = "Kathberali Kathberali",
            authorBn = "কাজী নজরুল ইসলাম",
            authorEn = "Kazi Nazrul Islam",
            category = RhymeCategory.ANIMALS,
            iconEmoji = "🐿️",
            themeColorHex = 0xFF8D6E63,
            linesBn = listOf(
                "কাঠবেড়ালী! কাঠবেড়ালী!",
                "পেয়ারা তুমি খাও?",
                "গুড়-মুড়ি খাও? দুধ-ভাত খাও?",
                "বাতাবি-লেবু? লাউ?",
                "বিড়াল-বাচ্চা? কুকুর-ছানা?",
                "ছোঁচা-তুমি? হ্যাবলা-পানা?",
                "তুমি কি মোর বন্ধু হবে?"
            ),
            linesEn = listOf(
                "Kathberali! Kathberali!",
                "Peyara tumi khao?",
                "Gur-muri khao? Dudh-bhat khao?",
                "Batabi-lebu? Lau?",
                "Biral-baccha? Kukur-chhana?",
                "Chhocha-tumi? Hyabla-pana?",
                "Tumi ki mor bondhu hobe?"
            ),
            meaning = "A joyful child asking a furry squirrel perched on a tree if it wants sweet guava and to become best friends.",
            funFact = "Bengali courtyards often have frisky squirrels jumping across mango and guava trees, captivating young children."
        ),
        Rhyme(
            id = "noton_noton_pairaguli",
            titleBn = "নোটন নোটন পায়রাগুলি",
            titleEn = "Noton Noton Pairaguli",
            authorBn = "লোকছড়া (প্রচলিত)",
            authorEn = "Traditional Folk Rhyme",
            category = RhymeCategory.FAVORITE,
            iconEmoji = "🕊️",
            themeColorHex = 0xFF039BE5,
            linesBn = listOf(
                "নোটন নোটন পায়রাগুলি,",
                "ঝোটন বেঁধেছে।",
                "দুই পারেতে দুই দোসর,",
                "রাঁধতে বসেছে।",
                "এক পারেতে সীতা রানি,",
                "রাঁধেন বাড়ে বসেন,",
                "আর এক পারে লক্ষ্মণ ঠাকুর,",
                "খেতে বসে হাসেন।"
            ),
            linesEn = listOf(
                "Noton noton pairaguli,",
                "Jhoton bendheche.",
                "Dui paarete dui doshor,",
                "Raandhte boseche.",
                "Ek paarete Sita rani,",
                "Raandhen baaren bosen,",
                "Aar ek paare Lokkhon thakur,",
                "Khete bose hasen."
            ),
            meaning = "Crested pigeons preening their feathers on river banks while sweet companionship and food bring smiles.",
            funFact = "Sung with hand clapping, children love mimicking fluttering wings while singing this tune."
        ),
        Rhyme(
            id = "khoka_jabe_machh_dhorte",
            titleBn = "খোকা যাবে মাছ ধরতে",
            titleEn = "Khoka Jabe Machh Dhorte",
            authorBn = "লোকছড়া (প্রচলিত)",
            authorEn = "Traditional Folk Rhyme",
            category = RhymeCategory.PLAYFUL,
            iconEmoji = "🐟",
            themeColorHex = 0xFF00897B,
            linesBn = listOf(
                "খোকা যাবে মাছ ধরতে,",
                "ক্ষীর নদীর কূলে।",
                "ছিপ নিয়ে গেল কোলা ব্যাঙে,",
                "মাছ নিয়ে গেল চিলে।",
                "খোকা তুমি কেঁদো না,",
                "আর একটা ছিপ দেবো না!"
            ),
            linesEn = listOf(
                "Khoka jaabe maachh dhorte,",
                "Kheer nodir kule.",
                "Chhip niye gelo kola bange,",
                "Maachh niye gelo cheele.",
                "Khoka tumi kendo na,",
                "Aar ekta chhip debo na!"
            ),
            meaning = "Little boy goes fishing by the river, but a bullfrog takes the rod and a kite bird takes the fish!",
            funFact = "A delightful nursery rhyme depicting village riversides, fishing adventures, and cheeky animals."
        ),
        Rhyme(
            id = "tai_tai_tai",
            titleBn = "তাই তাই তাই",
            titleEn = "Tai Tai Tai",
            authorBn = "লোকছড়া (প্রচলিত)",
            authorEn = "Traditional Folk Rhyme",
            category = RhymeCategory.PLAYFUL,
            iconEmoji = "👏",
            themeColorHex = 0xFFF06292,
            linesBn = listOf(
                "তাই তাই তাই,",
                "মামার বাড়ি যাই।",
                "মামার বাড়ি ভারী মজা,",
                "কিল চড় নাই।",
                "মামী দিলেন দুধ-ভাত,",
                "মেগে মেগে খাই!"
            ),
            linesEn = listOf(
                "Tai tai tai,",
                "Mamar bari jaai.",
                "Mamar bari bhaari moja,",
                "Keel chor naai.",
                "Maami dilen doodh-bhaat,",
                "Mege mege khaai!"
            ),
            meaning = "Clapping hands while anticipating the endless pampering, delicious food, and fun at maternal uncle's house.",
            funFact = "Bengali kids accompany this rhyme with gentle rhythmic hand clapping from infancy."
        ),
        Rhyme(
            id = "bak_bakum_paira",
            titleBn = "বাক বাকুম পায়রা",
            titleEn = "Bak Bakum Paira",
            authorBn = "লোকছড়া (প্রচলিত)",
            authorEn = "Traditional Folk Rhyme",
            category = RhymeCategory.ANIMALS,
            iconEmoji = "👑",
            themeColorHex = 0xFF7E57C2,
            linesBn = listOf(
                "বাক বাকুম পায়রা,",
                "মাথায় দিয়ে টায়রা।",
                "বউ সাজবে কাল কি,",
                "চড়বে সোনার পালকি!"
            ),
            linesEn = listOf(
                "Bak bakum paira,",
                "Mathay diye tayra.",
                "Bou sajbe kaal ki,",
                "Chorbe shonar paalki!"
            ),
            meaning = "Cooing pigeons wearing tiaras and riding inside royal golden palanquins in a celebration.",
            funFact = "The rhythm mimics the soothing coo ('bak-bakum') of Bengal's domestic pigeons."
        ),
        Rhyme(
            id = "aam_pata_jora_jora",
            titleBn = "আমপাতা জোড়া জোড়া",
            titleEn = "Aam Pata Jora Jora",
            authorBn = "লোকছড়া (প্রচলিত)",
            authorEn = "Traditional Folk Rhyme",
            category = RhymeCategory.PLAYFUL,
            iconEmoji = "🍃",
            themeColorHex = 0xFF43A047,
            linesBn = listOf(
                "আমপাতা জোড়া জোড়া,",
                "মারব চাবুক চড়ব ঘোড়া।",
                "ওরে বুবু সরে দাঁড়া,",
                "আসছে আমার পাগলা ঘোড়া!",
                "পাগলা ঘোড়া ক্ষেপেছে,",
                "চাবুক ছুড়ে মেরেছে!"
            ),
            linesEn = listOf(
                "Aam pata jora jora,",
                "Marbo chabuk chorbo ghora.",
                "Ore bubu shore dara,",
                "Aashche amar pagla ghora!",
                "Pagla ghora khepoche,",
                "Chaabuk chhure mereche!"
            ),
            meaning = "Pairs of mango leaves, cracking the whip and galloping away wildly on an imaginary spirited horse!",
            funFact = "Kids across rural and urban Bengal play galloping horseback games while chanting this spirited rhyme."
        ),
        Rhyme(
            id = "brishti_pore_tapur_tupur",
            titleBn = "বৃষ্টি পড়ে টাপুর টুপুর",
            titleEn = "Brishti Pore Tapur Tupur",
            authorBn = "রবীন্দ্রনাথ ঠাকুর",
            authorEn = "Rabindranath Tagore",
            category = RhymeCategory.CLASSIC,
            iconEmoji = "🌧️",
            themeColorHex = 0xFF1E88E5,
            linesBn = listOf(
                "বৃষ্টি পড়ে টাপুর টুপুর,",
                "নদে এলো বান।",
                "শিব ঠাকুরের বিয়ে হলো,",
                "তিন কন্যে দান।",
                "এক কন্যে রাঁধেন বাড়েন,",
                "এক কন্যে খান,",
                "এক কন্যে না খেয়ে,",
                "বাপের বাড়ি যান।"
            ),
            linesEn = listOf(
                "Brishti pore tapur tupur,",
                "Nodey elo baan.",
                "Shib thakurer biye holo,",
                "Teen konye daan.",
                "Ek konye raandhen baaren,",
                "Ek konye khaan,",
                "Ek konye na kheye,",
                "Baaper baari jaan."
            ),
            meaning = "Pitter-patter rainfall during monsoon floods, echoing famous folklore and rhythmic monsoon imagery.",
            funFact = "Written by Rabindranath Tagore inspired by ancient Bengali monsoon verses."
        )
    )

    fun getRhymeById(id: String): Rhyme? = rhymes.find { it.id == id }

    fun searchRhymes(query: String): List<Rhyme> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return rhymes
        return rhymes.filter {
            it.titleBn.lowercase().contains(q) ||
            it.titleEn.lowercase().contains(q) ||
            it.authorBn.lowercase().contains(q) ||
            it.authorEn.lowercase().contains(q) ||
            it.linesBn.any { line -> line.lowercase().contains(q) } ||
            it.linesEn.any { line -> line.lowercase().contains(q) }
        }
    }

    fun getRhymesByCategory(category: RhymeCategory): List<Rhyme> {
        return if (category == RhymeCategory.ALL) rhymes else rhymes.filter { it.category == category }
    }
}
