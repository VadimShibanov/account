package com.bank.account.controller;

import com.bank.account.dto.AuditDto;
import com.bank.account.mapper.AuditMapper;
import com.bank.account.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер аудита, реализующий REST API
 */
@Tag(name = "Account", description = "Accounts API")
@RestController
@RequestMapping("/audit")
public class AuditRestController {

    private final AuditService auditService;

    private final AuditMapper auditMapper;

    public AuditRestController(AuditService auditService, AuditMapper auditMapper) {
        this.auditService = auditService;
        this.auditMapper = auditMapper;
    }

    @Operation(summary = "Get audits list")
    @GetMapping
    public List<AuditDto> getAll() {
        return auditMapper.toAuditDto(auditService.getAll());
    }
}
