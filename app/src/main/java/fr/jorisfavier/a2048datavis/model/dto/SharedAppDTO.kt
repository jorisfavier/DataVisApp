package fr.jorisfavier.a2048datavis.model.dto

class SharedAppDTO(val sharings: List<SharingDTO>,
                 nextPage: String?,
                 code: Int,
                 prevPage: String?,
                 pageNum: Int,
                 pageIndex: Int
): AppAnnieResponseDTO(nextPage, code, prevPage, pageNum, pageIndex)