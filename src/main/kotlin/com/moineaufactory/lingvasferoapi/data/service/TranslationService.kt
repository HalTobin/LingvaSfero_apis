package com.moineaufactory.lingvasferoapi.data.service

import com.moineaufactory.lingvasferoapi.data.entity.Translation
import com.moineaufactory.lingvasferoapi.data.entity.TranslationId
import com.moineaufactory.lingvasferoapi.data.repository.TranslationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TranslationService @Autowired constructor(
    private val translationRepository: TranslationRepository
) {

    // Get all translations
    fun getAll(): List<Translation> = translationRepository.findAll()

    // Get a list of translations by the textId from Category
    fun getByTextId(textId: String): List<Translation> = translationRepository.findByTextId(textId)

    // Save or update a translation
    @Transactional
    fun save(translation: Translation): Translation = translationRepository.save(translation)

    // Save or update a list of translations
    @Transactional
    fun saveAll(translations: List<Translation>): List<Translation> = translationRepository.saveAll(translations)

    // Optional: Retrieve a specific translation by textId and iso code (composite key)
    fun getByTextIdAndIso(textId: String, iso: String): Translation? {
        val translationId = TranslationId(textId, iso)
        return translationRepository.findById(translationId).orElse(null)
    }

    // Optional: Delete a specific translation by textId and iso code
    @Transactional
    fun deleteByTextIdAndIso(textId: String, iso: String) {
        val translationId = TranslationId(textId, iso)
        if (translationRepository.existsById(translationId)) {
            translationRepository.deleteById(translationId)
        }
    }
}