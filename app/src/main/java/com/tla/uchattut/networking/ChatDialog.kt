package com.tla.uchattut.networking


//Not final version, far from it
data class ChatDialog(val id:String,
                      val userName:String,
                      val lastMessage:String,
                      val imageUrl:String,
                      val messageTime:String,
                      val unreadMsgNumber: Int)