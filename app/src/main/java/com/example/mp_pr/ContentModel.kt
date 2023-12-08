package com.example.mp_pr

data class ContentModel (var com_title : String? = null,
                         var com_content : String? = null,
                         var uid : String? = null,
                         var timeStamp : Long? = null,
                         var str_timeStamp : String? = null,
                         var favoriteCount : Int = 0,
                         var favorites : MutableMap<String, Boolean> = HashMap()) {
    data class Comment(var uid : String? = null, var userId : String? = null, var comment : String? = null, var timeStamp: Long? = null, var str_timeStamp: String? = null)

}

