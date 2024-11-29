package com.moineaufactory.lingvasferoapi.repository

import com.moineaufactory.lingvasferoapi.entity.Channel
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ChannelRepository : JpaRepository<Channel?, Long?> {
    override fun findAll(): List<Channel>
    override fun findById(id: Long): Optional<Channel?>
    fun findByContentCreatorId(id: Long): List<Channel>
    fun save(channel: Channel): Channel

    @Transactional
    @Modifying
    @Query("UPDATE Channel c SET c.contentDate = :newTimestamp WHERE c.id = :channelId")
    fun updateTimestamp(@Param("channelId") channelId: Long, @Param("newTimestamp") newTimestamp: Long): Int
}