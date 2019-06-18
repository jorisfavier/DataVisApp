package fr.jorisfavier.a2048datavis.model.dto

open class AppAnnieResponseDTO(val nextPage: String?,
                               val code: Int,
                               val prevPage: String?,
                               val pageNum: Int,
                               val pageIndex: Int)