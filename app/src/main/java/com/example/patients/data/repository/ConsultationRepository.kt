package com.example.patients.data.repository

import com.example.patients.data.model.Consultation
import com.example.patients.data.remote.ConsultationApi
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

class ConsultationRepository(private val api: ConsultationApi) {

    suspend fun getConsultations(patientId: String): Result<List<Consultation>> =
        try {
            Result.success(api.getConsultations(patientId))
        } catch (e: HttpException) {
            Result.failure(Exception("Erreur ${e.code()}"))
        } catch (e: IOException) {
            Result.failure(Exception("Serveur injoignable. Vérifiez l'adresse et le réseau."))
        } catch (e: SerializationException) {
            Result.failure(Exception("Réponse inattendue du serveur."))
        }
}