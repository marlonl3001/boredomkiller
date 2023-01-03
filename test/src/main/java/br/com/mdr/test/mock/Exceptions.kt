package br.com.mdr.test.mock

import br.com.mdr.base.ApiError
import br.com.mdr.test.base.REQUEST_ERROR
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

fun createHttpException(
    statusCode: Int,
    message: String = REQUEST_ERROR
) = HttpException(
    Response.error<ApiError>(
        statusCode,
        (
            "{" +
            "    \"error\": " +
            "      {  \"message\": \"$message }" +
            "}"
        ).toResponseBody("application/json".toMediaTypeOrNull())
    )
)
