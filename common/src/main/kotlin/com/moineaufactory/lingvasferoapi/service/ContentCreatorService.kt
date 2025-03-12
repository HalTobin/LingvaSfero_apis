package com.moineaufactory.lingvasferoapi.service

import com.moineaufactory.lingvasferoapi.dto.*
import com.moineaufactory.lingvasferoapi.entity.ContentCreator
import com.moineaufactory.lingvasferoapi.mapper.toChannelDto
import com.moineaufactory.lingvasferoapi.mapper.toContentCreatorDto
import com.moineaufactory.lingvasferoapi.repository.ChannelRepository
import com.moineaufactory.lingvasferoapi.repository.ContentCreatorCategoryRepository
import com.moineaufactory.lingvasferoapi.repository.ContentCreatorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class ContentCreatorService @Autowired constructor(
    private val contentCreatorRepository: ContentCreatorRepository,
    private val contentCreatorCategoryRepository: ContentCreatorCategoryRepository,
    private val channelRepository: ChannelRepository
) {
    fun getAll(): List<ContentCreatorDto> = contentCreatorRepository.findAll().mapNotNull { it.withCategories() }
    fun getById(id: Long): ContentCreatorDto? = contentCreatorRepository.findById(id).orElse(null)?.withCategories()
    fun getByLanguageId(languageId: Int): List<ContentCreatorDto> = contentCreatorRepository.findByLanguageId(languageId).mapNotNull { it.withCategories()}

    fun getFullContentCreator(id: Long): FullContentCreatorDto? = try {
        val rawData = contentCreatorRepository.findFullContentCreatorById(id)

        val language = rawData.first().let {
            LanguageDto(
                id = it["languageId"] as Int,
                iso = it["languageIso"] as String,
                name = it["languageName"] as String,
                imageUrl = "api/language/image?iso=${it["languageIso"] as String}",
                imageHash = it["languageImageHash"] as Int,
                color = it["languageColor"] as Long,
                lightColor = it["languageLightColor"] as Long,
                darkColor = it["languageDarkColor"] as Long,
                supportLevel = (it["languageSupportLevel"] as Number).toInt()
            )
        }

        val region = rawData.first()["regionId"]?.let {
            RegionDto(
                id = it as Int,
                languageId = rawData.first()["regionLanguageId"] as Int,
                iso = rawData.first()["regionIso"] as String,
                name = rawData.first()["regionName"] as String,
                imageUrl = "api/region/image?iso=${rawData.first()["regionIso"] as String}",
                imageHash = rawData.first()["regionImageHash"] as Int,
                color = rawData.first()["regionColor"] as Long,
                lightColor = rawData.first()["regionLightColor"] as Long,
                darkColor = rawData.first()["regionDarkColor"] as Long
            )
        }

        val categories = if (rawData.first()["categoryId"] == null) emptyList() else rawData.groupBy { it["categoryId"] as? Int }
            .mapNotNull { (categoryId, group) ->
            categoryId?.let {
                val translations = group.map { row ->
                    TranslationDto(
                        textId = row["categoryTextId"] as String,
                        iso = row["translationIso"] as String,
                        text = row["translationText"] as String
                    )
                }
                CategoryDto(
                    id = categoryId,
                    textId = group.first()["categoryTextId"] as String,
                    translations = translations
                )
            }
        }

        FullContentCreatorDto(
            id = id,
            language = language,
            region = region,
            thumbnail = rawData.first()["thumbnail"] as String,
            thumbnailSmall = rawData.first()["thumbnailSmall"] as String?,
            name = rawData.first()["creatorName"] as String,
            description = rawData.first()["creatorDescription"] as String,
            levelMin = rawData.first()["levelMin"] as Int,
            levelMax = rawData.first()["levelMax"] as Int,
            categories = categories,
            channels = channelRepository.findByContentCreatorId(id).map { it.toChannelDto() }
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null }

    fun filterContentCreators(filterDto: ContentCreatorFilterDto): List<ContentCreatorDto> {
        return contentCreatorRepository.filterContentCreator(
            languageId = filterDto.languageId,
            regionId = filterDto.regionId,
            arrayLength = filterDto.categoriesId.size,
            categoriesId = filterDto.categoriesId.toTypedArray(),
            levelId = filterDto.levelId,
            channelSourceId = filterDto.channelSourceId
        ).mapNotNull { it.withCategories() }
    }

    fun filterContentCreatorsPage(filterDto: ContentCreatorFilterDto, p: Pageable): Page<ContentCreatorDto> {
        return contentCreatorRepository.filterContentCreatorPage(
            languageId = filterDto.languageId,
            regionId = filterDto.regionId,
            arrayLength = filterDto.categoriesId.size,
            categoriesId = filterDto.categoriesId.toTypedArray(),
            levelId = filterDto.levelId,
            channelSourceId = filterDto.channelSourceId,
            pageable = p
        ).map { it.withCategories() }
    }

    fun countContentCreatorsByFilter(filterDto: ContentCreatorFilterDto): Int =
        contentCreatorRepository.countContentCreatorByFilter(
            languageId = filterDto.languageId,
            regionId = filterDto.regionId,
            arrayLength = filterDto.categoriesId.size,
            categoriesId = filterDto.categoriesId.toTypedArray(),
            levelId = filterDto.levelId,
            channelSourceId = filterDto.channelSourceId
        )

    fun countContentCreatorsByLanguage(languageId: Int): Int =
        contentCreatorRepository.countContentCreatorByLanguageId(languageId)

    fun searchContentCreators(search: String): List<ContentCreatorDto> = contentCreatorRepository.findContentCreatorBySearchText(search).mapNotNull { it.withCategories() }

    fun ContentCreator.withCategories(): ContentCreatorDto? {
        return this.id?.let { contentCreatorId ->
            val categories = contentCreatorCategoryRepository.findByContentCreatorId(contentCreatorId)
            this.toContentCreatorDto(categories.map { it.id.categoryId })
        }
    }
}