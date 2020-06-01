package com.app.rxretrofit.apiservices.responsebean

class UserResponseBean {
    var data: List<DataEntity>? = null
    var page = 0

    class DataEntity {
        var avatar: String = ""
        var last_name: String = ""
        var first_name: String = ""
        var email: String = ""
        var id = 0

    }
}