package fr.jorisfavier.a2048datavis.model.dto

import com.google.gson.annotations.SerializedName

class SalesResponseDTO(@SerializedName("sales_list")
                       val salesList: List<SaleDTO>,
                       nextPage: String?,
                       code: Int,
                       prevPage: String?,
                       pageNum: Int,
                       pageIndex: Int
): AppAnnieResponseDTO(nextPage, code, prevPage, pageNum, pageIndex)