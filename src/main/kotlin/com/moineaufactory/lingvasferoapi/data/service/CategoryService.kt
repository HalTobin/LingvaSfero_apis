package com.moineaufactory.lingvasferoapi.data.service

import com.moineaufactory.lingvasferoapi.data.entity.Category
import com.moineaufactory.lingvasferoapi.data.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryService @Autowired constructor(
    private val categoryRepository: CategoryRepository
) {
    fun getAll(): List<Category> = categoryRepository.findAll()
    fun getById(id: Int): Category? = categoryRepository.findById(id).orElse(null)
    fun save(category: Category) = categoryRepository.save(category)
}