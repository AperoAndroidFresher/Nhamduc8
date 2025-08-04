package com.example.nhamngocduc.util

interface EntityMapper<Domain, Entity> {
    fun mapFromEntity(entity: Entity): Domain
    fun mapToEntity(domain: Domain): Entity
}