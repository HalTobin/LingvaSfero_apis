package data.service

import data.entity.Region
import data.repository.RegionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RegionService @Autowired constructor(
    private val regionRepository: RegionRepository
) {
    fun getAll(): List<Region> = regionRepository.findAll()
    fun getById(id: Int): Region? = regionRepository.findById(id).orElse(null)
    fun getByIso(iso: String): Region? = regionRepository.findByIso(iso).orElse(null)
    fun getByLanguageId(languageId: Int): List<Region> = regionRepository.findByLanguageId(languageId)
    fun save(region: Region): Region = regionRepository.save(region)
}