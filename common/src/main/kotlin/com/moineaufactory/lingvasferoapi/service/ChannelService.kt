package com.moineaufactory.lingvasferoapi.service

import com.moineaufactory.lingvasferoapi.entity.Channel
import com.moineaufactory.lingvasferoapi.repository.ChannelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChannelService @Autowired constructor(
    private val channelRepository: ChannelRepository
) {
    fun getAll(): List<Channel> = channelRepository.findAll()
    fun getById(id: Long): Channel? = channelRepository.findById(id).orElse(null)
    fun getByContentCreatorId(id: Long): List<Channel> = channelRepository.findByContentCreatorId(id)
    fun save(channel: Channel): Channel = channelRepository.save(channel)
    fun updateTimestamp(channelId: Long, newTimestamp: Long) = channelRepository.updateTimestamp(channelId, newTimestamp)
}