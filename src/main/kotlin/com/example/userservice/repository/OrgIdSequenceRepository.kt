package com.example.userservice.repository

import com.example.userservice.entity.OrgIdSequence
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrgIdSequenceRepository : JpaRepository<OrgIdSequence, Long>
