package dev.josed20.pc02moviles2220015022200171.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dev.josed20.pc02moviles2220015022200171.data.model.Team
import kotlinx.coroutines.tasks.await

class TeamRepository {
    private val db = FirebaseFirestore.getInstance()
    // Asegúrate de que este nombre sea igual al que Lucero usará
    private val collection = db.collection("equipos_liga1")

    // TU PARTE: Obtener la lista de equipos
    suspend fun getTeams(): Result<List<Team>> {
        return try {
            // Ordenamos por títulos descendente (opcional, pero se ve mejor)
            val snapshot = collection
                .orderBy("titulos", Query.Direction.DESCENDING)
                .get()
                .await()
            val teams = snapshot.toObjects(Team::class.java)
            Result.success(teams)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // PARTE DE LUCERO (La dejamos lista para ella): Guardar equipo
    suspend fun saveTeam(team: Team): Result<Boolean> {
        return try {
            val docRef = collection.document()
            val teamWithId = team.copy(id = docRef.id)
            docRef.set(teamWithId).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Método adicional para agregar equipo
    fun addTeam(team: Team): Result<Unit> {
        return try {
            val docRef = collection.document()
            val teamWithId = team.copy(id = docRef.id)
            docRef.set(teamWithId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}