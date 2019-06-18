package fr.jorisfavier.a2048datavis.model.dto

import com.google.gson.annotations.SerializedName

class SharingDTO(val products: List<ProductDTO>,
                 @SerializedName("owner_account_id")
                 val ownerAccountId: Long,
                 nextPage: String?,
                 code: Int,
                 prevPage: String?,
                 pageNum: Int,
                 pageIndex: Int
): AppAnnieResponseDTO(nextPage, code, prevPage, pageNum, pageIndex)