package com.example.iadiproject.api.EvaluationPanel

import com.example.iadiproject.api.EvaluationPanelDTO
import com.example.iadiproject.api.GrantCallDTO
import com.example.iadiproject.api.ReviewerDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(value="evaluationpanels", description = "'Evaluation Panels' management operations")
@RequestMapping("/evaluationpanels")
interface EvaluationPanelAPI {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation("Get the list of all evaluation panels")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of evaluation panels"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<EvaluationPanelDTO>

    @PreAuthorize("@securityService.doesReviewerBelongToPanel(principal, #id) or hasAuthority('ROLE_ADMIN')")
    @ApiOperation("Get a evaluation panel by id")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of evaluation panels"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): EvaluationPanelDTO

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation("Create a new evaluation panel")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created a new evaluation panel"),
        ApiResponse(code = 400, message = "BAD_REQUEST"),
        ApiResponse(code = 409, message = "CONFLICT")
    ])
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody ePanel: EvaluationPanelDTO){

    }

    @ApiOperation("Add a reviewer to a evaluation panel")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully added a reviewer to a panel"),
        ApiResponse(code = 400, message = "BAD_REQUEST"),
        ApiResponse(code = 409, message = "CONFLICT")
    ])
    @PostMapping("{id}/reviewers")
    @ResponseStatus(HttpStatus.CREATED)
    fun addReviewerToPanel(@PathVariable id:Long,@RequestBody reviewerId: Long){

    }

    @ApiOperation("Add a panel chair to a evaluation panel")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully added a panel chair to a panel"),
        ApiResponse(code = 400, message = "BAD_REQUEST"),
        ApiResponse(code = 409, message = "CONFLICT")
    ])
    @PostMapping("{id}/panelchair")
    @ResponseStatus(HttpStatus.OK)
    fun addPanelChairToPanel(@PathVariable id:Long,@RequestBody reviewerId: Long){

    }

}