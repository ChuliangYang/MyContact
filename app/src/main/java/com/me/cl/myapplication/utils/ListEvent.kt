package com.me.cl.myapplication.utils

/**
 * Created by CL on 10/23/18.
 */
data class  ListEvent<T>(val modifyType: ModifyType,val dataList:List<T>?=null,val position:Int?=null)

enum class ModifyType{
    Insert,Delete,Init
}