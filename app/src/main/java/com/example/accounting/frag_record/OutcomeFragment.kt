package com.example.accounting.frag_record

import android.annotation.SuppressLint
import com.example.accounting.R
import com.example.accounting.db.DBManager

class OutcomeFragment : BaseRecordFragment() {
    @SuppressLint("SetTextI18n")
    override fun loadDataGV() {
        super.loadDataGV()
        val outlist = DBManager.getTypeList(0)
        typeList.addAll(outlist)
        adapter.notifyDataSetChanged()
        typeTv.text = "Other"
        typeIv.setImageResource(R.mipmap.ic_qita_fs)
    }

    override fun saveAccountToDB() {
        accountBean.kind = 0
        DBManager.insertItemToAccounttb(accountBean)
    }
}