package fr.jorisfavier.a2048datavis.model.dto

data class SaleDTO (
    val date : String,
    val country : String,
    val units : UnitDTO,
    val revenue : RevenueDTO
)