package com.example.honkaigallery

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Characters(
    val image: Int,
    val element: Int,
    val stars: Int,
    val name: String,
    val path: Int,
) : Parcelable

val dummyChar = listOf(
    Characters(R.drawable.bailu, R.drawable.lightning_sm, R.drawable.fivestar_bg,"Bailu",R.drawable.path_the_abundance),
    Characters(R.drawable.danheng, R.drawable.wind_sm, R.drawable.fourstar_bg,"Dan Heng",R.drawable.path_the_hunt),
    Characters(R.drawable.gepard, R.drawable.ice_sm, R.drawable.fivestar_bg,"Gepard",R.drawable.path_the_preservation),
    Characters(R.drawable.march7th, R.drawable.ice_sm, R.drawable.fourstar_bg,"March 7th",R.drawable.path_the_preservation),
    Characters(R.drawable.natasha, R.drawable.physical_sm, R.drawable.fourstar_bg,"Natasha",R.drawable.path_the_abundance),
    Characters(R.drawable.sampo, R.drawable.wind_sm, R.drawable.fourstar_bg,"Sampo",R.drawable.path_the_nihility),
    Characters(R.drawable.seele, R.drawable.quantum_sm, R.drawable.fivestar_bg,"Seele",R.drawable.path_the_hunt),
    Characters(R.drawable.bronya, R.drawable.wind_sm, R.drawable.fivestar_bg,"Bronya",R.drawable.path_the_harmony),
    Characters(R.drawable.serval, R.drawable.lightning_sm, R.drawable.fourstar_bg,"Serval",R.drawable.path_the_erudition),
    Characters(R.drawable.tingyun, R.drawable.fire_sm, R.drawable.fourstar_bg,"Tingyun",R.drawable.path_the_harmony),
    Characters(R.drawable.asta, R.drawable.fire_sm, R.drawable.fourstar_bg,"Asta",R.drawable.path_the_harmony),
    Characters(R.drawable.yanqing, R.drawable.ice_sm, R.drawable.fivestar_bg,"Yanqing",R.drawable.path_the_hunt),
    Characters(R.drawable.clara, R.drawable.physical_sm, R.drawable.fivestar_bg,"Clara",R.drawable.path_the_destruction),
    Characters(R.drawable.herta, R.drawable.ice_sm, R.drawable.fourstar_bg,"Herta",R.drawable.path_the_erudition),
    Characters(R.drawable.sushang, R.drawable.physical_sm, R.drawable.fourstar_bg,"Sushang",R.drawable.path_the_hunt),
    Characters(R.drawable.himeko, R.drawable.fire_sm, R.drawable.fivestar_bg,"Himeko",R.drawable.path_the_erudition),
    Characters(R.drawable.welt, R.drawable.imaginary_sm, R.drawable.fivestar_bg,"Welt",R.drawable.path_the_nihility),
    Characters(R.drawable.arlan, R.drawable.lightning_sm, R.drawable.fourstar_bg,"Arlan",R.drawable.path_the_destruction),
)