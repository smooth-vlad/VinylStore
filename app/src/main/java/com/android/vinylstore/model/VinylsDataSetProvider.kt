package com.android.vinylstore.model

import com.android.vinylstore.R

class VinylsDataSetProvider {
    fun get(): Array<Vinyl> {
        return arrayOf(
            Vinyl(R.drawable.am, "AM", "Arctic Monkeys"),
            Vinyl(R.drawable.tbhc, "Tranquility Base Hotel & Casino", "Arctic Monkeys"),
            Vinyl(R.drawable.smoke_mirrors, "Smoke + Mirrors", "Imagine Dragons"),
            Vinyl(R.drawable.evolve, "Evolve", "Imagine Dragons"),
            Vinyl(R.drawable.night_visions, "Night Visions", "Imagine Dragons"),
            Vinyl(R.drawable.hp, "Дом с нормальными явлениями", "Скриптонит"),
            Vinyl(R.drawable.p36, "Праздник на улице 36", "Скриптонит"),
            Vinyl(R.drawable._2004, "2004", "Скриптонит"),
            Vinyl(R.drawable.dragonborn, "Dragonburn", "Big Baby Tape"),
            Vinyl(R.drawable.bandana, "Bandana I", "Big Baby Tape")
        )
    }
}