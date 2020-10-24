package com.example.iadiproject.api.EvaluationPanel

import com.example.iadiproject.api.EvaluationPanelDTO
import com.example.iadiproject.api.GrantCallDTO
import com.example.iadiproject.api.ReviewerDTO
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Api(value="evaluationpanels", description = "'Evaluation Panels' management operations")
@RequestMapping("/evaluationpanels")
interface EvaluationPanelAPI {

    @GetMapping("")
    fun getAll(): List<EvaluationPanelDTO>

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): EvaluationPanelDTO

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody ePanel: EvaluationPanelDTO){

    }

    @PostMapping("{id}/reviewers")
    @ResponseStatus(HttpStatus.OK)
    fun addReviewerToPanel(@PathVariable id:Long,@RequestBody reviewerId: Long){

    }

    @PostMapping("{id}/panelchair")
    @ResponseStatus(HttpStatus.OK)
    fun addPanelChairToPanel(@PathVariable id:Long,@RequestBody reviewerId: Long){

    }

}