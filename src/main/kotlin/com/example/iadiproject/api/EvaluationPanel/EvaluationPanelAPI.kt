package com.example.iadiproject.api.EvaluationPanel

import com.example.iadiproject.api.EvaluationPanelDTO
import com.example.iadiproject.api.GrantCallDTO
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Api(value="evaluationpanels", description = "'Evaluation Panels' management operations")
@RequestMapping("/evaluationpanels")
interface EvaluationPanelAPI {

    @GetMapping("")
    fun getAll(): List<EvaluationPanelDTO>

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): String

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOne(@RequestBody ePanel: EvaluationPanelDTO){

    }
}