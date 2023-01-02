package br.com.mdr.base.domain

enum class UserActivityStatus(val status: String) {
    ADDED("Adicionada"),
    FINISHED("Realizada"),
    IN_PROGRESS("Em andamento"),
    GIVE_UP("Desistência")
}