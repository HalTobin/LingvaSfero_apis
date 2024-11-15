package com.moineaufactory.lingvasferoapi.repository

import com.moineaufactory.lingvasferoapi.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CategoryRepository : JpaRepository<Category, Int> {
    override fun findAll(): List<Category>
    override fun findById(id: Int): Optional<Category?>
    fun save(category: Category)
}