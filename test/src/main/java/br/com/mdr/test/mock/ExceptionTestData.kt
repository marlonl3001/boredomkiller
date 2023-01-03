package br.com.mdr.test.mock

import br.com.mdr.base.ApiError
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

private const val MESSAGE_ERROR = "No activity found with the specified parameters"
private const val CONTENT_ERROR = "{\"error\":\"No activity found with the specified parameters\"}"
private const val TEXT_PLAIN = "text/plain"
private const val UNPROCESSABLE_ENTITY_ERROR_CODE = 422

object ExceptionTestData {

    val HTTP_EXCEPTION = HttpException(
        Response.error<Any>(
            UNPROCESSABLE_ENTITY_ERROR_CODE,
            CONTENT_ERROR.toResponseBody(TEXT_PLAIN.toMediaTypeOrNull())
        )
    )

    val ERROR_WRAPPER = ApiError(MESSAGE_ERROR)
}
