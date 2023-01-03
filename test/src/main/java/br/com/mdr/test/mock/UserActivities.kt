package br.com.mdr.test.mock

import br.com.mdr.base.domain.UserActivityResponse
import br.com.mdr.base.extension.SerializationExtension.jsonToObject
import br.com.mdr.test.extension.getJsonFromAssetsOrResources

val userActivityResponse: UserActivityResponse? =
    getJsonFromAssetsOrResources("user-activity.json").jsonToObject<UserActivityResponse>()