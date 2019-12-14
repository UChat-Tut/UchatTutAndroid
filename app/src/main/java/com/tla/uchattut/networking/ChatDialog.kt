package com.tla.uchattut.networking

data class ChatDialog(val id:String,
                      val userName:String,
                      val lastMessage:String,
                      val imageUrl:String,
                      val messageTime:String,
                      val unreadMsgNumber: Int)