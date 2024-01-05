package com.example.capstone.List
class User {
    var uid : String? = null
    var name : String? = null
    var email : String? = null
    var phoneNum: String? = null

    constructor(){}

    constructor(name: String?, email: String?, phoneNum: String?, uid: String?){
        this.uid = uid
        this.name = name
        this.email = email
        this.phoneNum = phoneNum
    }
}