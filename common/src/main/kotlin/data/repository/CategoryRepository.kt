package com.moineaufactory.lingvasferoapi.data.repository

import com.moineaufactory.lingvasferoapi.data.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CategoryRepository : JpaRepository<Category, Int> {
    override fun findAll(): List<Category>
    override fun findById(id: Int): Optional<Category?>
    fun save(category: Category)
}